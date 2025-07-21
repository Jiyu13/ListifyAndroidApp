package com.example.listifyjetapp.di

import com.example.listifyjetapp.network.ListifyAPI
import com.example.listifyjetapp.utils.constants.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // TODO: create a provider to build a Retrofit instance
    @Provides
    @Singleton
    fun provideListifyAPI():ListifyAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ListifyAPI::class.java)
    }
}