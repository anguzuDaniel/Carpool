package com.danoTech.carpool.model.service.module

import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.model.service.ProfileService
import com.danoTech.carpool.model.service.StorageService
import com.danoTech.carpool.model.service.impl.AccountServiceImpl
import com.danoTech.carpool.model.service.impl.ProfileServiceImpl
import com.danoTech.carpool.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun provideProfileService(impl: ProfileServiceImpl): ProfileService

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
}