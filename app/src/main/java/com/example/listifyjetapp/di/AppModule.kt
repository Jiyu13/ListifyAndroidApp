package com.example.listifyjetapp.di

import android.content.Context
import com.example.listifyjetapp.data.ListifyStorageManager
import com.example.listifyjetapp.network.ListifyAPI
import com.example.listifyjetapp.utils.constants.Constants
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // TODO: OkHttp with:
    // - Interceptor: adds Authorization: Bearer <access_token>
    // - Authenticator: on 401 -> POST /auth/refresh with x-Refresh-Token -> save new access -> retry
    @Provides
    @Singleton
    fun provideOkHttpClient(
        storageManager: ListifyStorageManager,
        apiLazy: Lazy<ListifyAPI>
    ): OkHttpClient {
        val authInterceptor = Interceptor {chain ->
            // Interceptor is not suspend; grab the latest value synchronously:
            val accessToken = runBlocking { storageManager.accessTokenFlow.first() }
            val request = chain.request().newBuilder().apply {
                if (accessToken.isNotBlank()) {
                    addHeader("Authorization", "Bearer $accessToken")
                }
            }.build()
            chain.proceed(request)
        }

        val authenticator = Authenticator { route, response ->
            // Prevent infinite loops by checking prior responses
            var prior = response.priorResponse
            var attemptCount = 0
            while (prior != null) {
                if (prior.code == 401) {
                    attemptCount++
                }
                prior = prior.priorResponse
            }

            if (attemptCount >= 1) {
                return@Authenticator null // Already tried once
            }
            // Do not try to refresh while calling refresh
            if (response.request.url.encodedPath.endsWith("/refresh")) return@Authenticator null

            try {
                val refreshToken = runBlocking { storageManager.refreshTokenFlow.first() }
                if (refreshToken.isBlank()) return@Authenticator null

                //call refresh synchronously
                val refreshResponse = runBlocking { apiLazy.get().refresh(refreshToken) }

                // save new token
                runBlocking {
                    storageManager.updateTokens(refreshResponse.accessToken, refreshResponse.refreshToken)
                }

                // rebuild the original request with new access token
                val newAccessToken = refreshResponse.accessToken
                return@Authenticator response.request.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()

            } catch (_: Exception) {
                // refresh failed -> no retry
                runBlocking { storageManager.clearDataStore() }
                null
            }
        }

        return OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
            .authenticator(authenticator)
            .build()
    }


    // TODO: Retrofit  with OkHttpClient
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // TODO: dataStore to read / write tokens
    @Provides
    @Singleton
    fun provideDataStorageAPI(@ApplicationContext context: Context
    ): ListifyStorageManager = ListifyStorageManager(context)

    @Provides
    @Singleton
    fun provideListifyAPI(retrofit: Retrofit): ListifyAPI =
        retrofit.create(ListifyAPI::class.java)

//    // TODO: create a provider to build a Retrofit instance
//    @Provides
//    @Singleton
//    fun provideListifyAPI():ListifyAPI {
//        return Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ListifyAPI::class.java)
//    }
}