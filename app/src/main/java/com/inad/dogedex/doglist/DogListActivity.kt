package com.inad.dogedex.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.inad.dogedex.dogdetail.DogDetailComposeActivity
import com.inad.dogedex.dogdetail.DogDetailComposeActivity.Companion.DOG_KEY
import com.inad.dogedex.dogdetail.ui.theme.DogedexTheme
import com.inad.dogedex.model.Dog

class DogListActivity : ComponentActivity() {

    private val viewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogedexTheme {
                val dogList = viewModel.dogList
                val status = viewModel.status
                DogListScreen(
                    dogList = dogList.value,
                    status = status.value,
                    onDogClicked = ::openDogDetailActivity,
                    onNavigationIconClick = ::onNavigationIconClick,
                    onErrorDialogDismiss = ::resetApiResponseStatus
                )
            }
        }
    }

    private fun openDogDetailActivity(dog: Dog) {
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DOG_KEY, dog)
        startActivity(intent)
    }

    private fun onNavigationIconClick() {
        finish()
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

}