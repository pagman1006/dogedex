package com.inad.dogedex.dogdetail

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inad.dogedex.api.ApiResponseStatus
import com.inad.dogedex.dogdetail.DogDetailComposeActivity.Companion.DOG_KEY
import com.inad.dogedex.dogdetail.DogDetailComposeActivity.Companion.IS_RECOGNITION_KEY
import com.inad.dogedex.dogdetail.DogDetailComposeActivity.Companion.MOST_PROBABLE_DOGS_IDS
import com.inad.dogedex.doglist.IDogRepository
import com.inad.dogedex.model.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val dogRepository: IDogRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var dog = mutableStateOf(
        savedStateHandle.get<Dog>(DOG_KEY)
    )
        private set

    private var probableDogsIds = mutableStateListOf(
        savedStateHandle.get<ArrayList<String>>(MOST_PROBABLE_DOGS_IDS) ?: arrayListOf()
    )

    var isRecognition = mutableStateOf(
        savedStateHandle.get<Boolean>(IS_RECOGNITION_KEY) ?: false
    )
        private set

    var status = mutableStateOf<ApiResponseStatus<Any>?>(null)
        private set

    private var _probableDogList = MutableStateFlow<MutableList<Dog>>(mutableListOf())
    val probableDogList: StateFlow<MutableList<Dog>>
        get() = _probableDogList

    fun getProbableDogs() {
        _probableDogList.value.clear()
        viewModelScope.launch {
            dogRepository.getProbableDogs(probableDogsIds[0]).collect { apiResponseStatus ->
                if (apiResponseStatus is ApiResponseStatus.Success) {
                    val probableDogMutableList = _probableDogList.value.toMutableList()
                    probableDogMutableList.add(apiResponseStatus.data)
                    _probableDogList.value = probableDogMutableList
                }
            }
        }
    }

    fun addDogToUser(dogId: Long) {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleAddDogToUserResponseStatus(dogRepository.addDogToUser(dogId))
        }
    }

    private fun handleAddDogToUserResponseStatus(apiResponseStatus: ApiResponseStatus<Any>) {
        status.value = apiResponseStatus
    }

    fun resetApiResponseStatus() {
        status.value = null
    }

    fun updateDog(newDog: Dog) {
        dog.value = newDog
    }
}