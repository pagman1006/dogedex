package com.inad.dogedex.di

import com.inad.dogedex.machinelearning.ClassifierRepository
import com.inad.dogedex.machinelearning.IClassifierRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ClassifierModule {

    @Binds
    abstract fun bindClassifierRepository(
        classifierRepository: ClassifierRepository
    ): IClassifierRepository

}