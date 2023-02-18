package com.inad.dogedex.doglist

import com.inad.dogedex.R
import com.inad.dogedex.api.ApiResponseStatus
import com.inad.dogedex.api.DogsApi.retrofitService
import com.inad.dogedex.api.dto.AddDogToUserDTO
import com.inad.dogedex.api.dto.DogDTOMapper
import com.inad.dogedex.api.makeNetworkCall
import com.inad.dogedex.model.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class DogRepository {

    private suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val doglistApiResponse = retrofitService.getAllDogs()
        val dogsDTOList = doglistApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogsDTOList)
    }

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> = makeNetworkCall {
        val addDogToUserDTO = AddDogToUserDTO(dogId)
        val defaultResponse = retrofitService.addDogToUser(addDogToUserDTO)

        if (!defaultResponse.isSuccess) {
            throw Exception(defaultResponse.message)
        }
    }

    private suspend fun getUserDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val doglistApiResponse = retrofitService.getUserDogs()
        val dogsDTOList = doglistApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogsDTOList)
    }

    suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
        return withContext(Dispatchers.IO) {
            val allDogsListDeferred = async { downloadDogs() }
            val userDogsListDeferred = async { getUserDogs() }

            val allDogsListResponse = allDogsListDeferred.await()
            val userDogsListResponse = userDogsListDeferred.await()

            if (allDogsListResponse is ApiResponseStatus.Error) {
                allDogsListResponse
            } else if (userDogsListResponse is ApiResponseStatus.Error) {
                userDogsListResponse
            } else if (allDogsListResponse is ApiResponseStatus.Success
                && userDogsListResponse is ApiResponseStatus.Success) {
                ApiResponseStatus.Success(
                    getCollectionList(
                        allDogsListResponse.data, userDogsListResponse.data
                    )
                )
            } else {
                ApiResponseStatus.Error(R.string.unknown_error)
            }
        }
    }

    private fun getCollectionList(allDogList: List<Dog>, userDogList: List<Dog>): List<Dog> =
        allDogList.map { dog ->
            if (userDogList.contains(dog)) {
                dog
            } else {
                Dog(
                    0, dog.index, "", "", "", "", "",
                    "", "", "", "", inCollection = false
                )
            }
        }.sorted()

    suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> = makeNetworkCall {
        val response = retrofitService.getDogByMlId(mlDogId)
        if (!response.isSuccess) {
            throw Exception(response.message)
        }
        val dogMapper = DogDTOMapper()
        dogMapper.fromDogDTOToDogDomain(response.data.dog)
    }

}