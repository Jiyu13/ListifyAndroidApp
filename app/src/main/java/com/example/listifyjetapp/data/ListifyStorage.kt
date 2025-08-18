package com.example.listifyjetapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.listifyjetapp.model.UserDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create a Preferences DataStore
val USER_DATASTORE = "user_data"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

class ListifyStorageManager(val context: Context) {

     private companion object {
        val USER_ID = intPreferencesKey("userId")
        val EMAIL = stringPreferencesKey("email")
        val ACCESS_TOKEN = stringPreferencesKey("accessToken")
        val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
        val IS_LOGIN = booleanPreferencesKey("isLogin")
    }

    // write a preference dataStorage - after login
    suspend fun saveToDataStore(userDataStore: UserDataStore) {
        context.dataStore.edit {
            it[USER_ID] = userDataStore.userId
            it[EMAIL] = userDataStore.email
            it[ACCESS_TOKEN] = userDataStore.accessToken
            it[REFRESH_TOKEN] = userDataStore.refreshToken
            it[IS_LOGIN] = userDataStore.isLogin
        }
    }

    // retrieve data from dataStore
    fun getUser(): Flow<UserDataStore> = context.dataStore.data.map {
        UserDataStore(
            userId= it[USER_ID] ?: 0,
            email = it[EMAIL] ?: "",
            accessToken =it[ACCESS_TOKEN] ?: "",
            refreshToken =it[REFRESH_TOKEN] ?: "",
            isLogin = it[IS_LOGIN] ?: false,
        )
    }

    val userIdFlow: Flow<Int> = context.dataStore.data.map { it[USER_ID] ?: 0 }
    val emailFlow: Flow<String> = context.dataStore.data.map { it[EMAIL] ?: "" }
    val isLoggedInFlow: Flow<Boolean> = context.dataStore.data.map { it[IS_LOGIN] ?: false }

    val accessTokenFlow: Flow<String> = context.dataStore.data.map { it[ACCESS_TOKEN] ?: "" }
    val refreshTokenFlow: Flow<String> = context.dataStore.data.map { it[REFRESH_TOKEN] ?: "" }

    suspend fun updateIsLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit { it[IS_LOGIN] = isLoggedIn }
    }

    suspend fun updateEmail(email: String) {
        context.dataStore.edit { it[EMAIL] = email }
    }

    suspend fun updateTokens(accessToken: String, refreshToken: String) {
        context.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
            it[REFRESH_TOKEN] = refreshToken
        }
    }

    // clear data from dataStore - logout
    suspend fun clearDataStore() = context.dataStore.edit {
        it.clear()
    }
}