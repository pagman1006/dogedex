package com.inad.dogedex.di

import com.inad.dogedex.doglist.DogRepository
import com.inad.dogedex.doglist.IDogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DogTaskModule {

    @Binds
    abstract fun bindDogRepository(
        dogRepository: DogRepository
    ): IDogRepository
}