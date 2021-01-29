package com.ejcho.maskservice.di.module

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    fun provideLocationService(@ApplicationContext context: Context): FusedLocationProviderClient {
        // 위치정보
        // 위치 서비스 클라이언트 만들기
        return LocationServices.getFusedLocationProviderClient(context)
    }
}