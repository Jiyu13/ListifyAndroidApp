package com.example.listifyjetapp.ui.screens.splash

import androidx.lifecycle.ViewModel
import com.example.listifyjetapp.data.ListifyStorageManager
import com.example.listifyjetapp.network.ListifyAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val storageManager: ListifyStorageManager,
    private val api: ListifyAPI
) : ViewModel() {

    suspend fun isLoggedIn(): Boolean = storageManager.isLoggedInFlow.first()
    suspend fun getUserId(): Int = storageManager.userIdFlow.first()

    // Optional: decode JWT exp; if you don't want to parse JWT, just try refresh and ignore errors.
    private fun isExpired(jwt: String): Boolean {
        return try {
            val token = jwt.split(".")
            if (token.size < 2) return true     // treat as expired/invalid
            val payload = android.util.Base64.decode(
                token[1],
                android.util.Base64.URL_SAFE or android.util.Base64.NO_WRAP
            )
            val json = org.json.JSONObject(String(payload))
            val exp = json.optLong("exp", 0L)
            val nowSec = System.currentTimeMillis() / 1000
            exp != 0L && exp <= nowSec
        } catch (_: Exception) { false }
    }

    suspend fun accessToken(): String {
        return storageManager.accessTokenFlow.first()
    }
    // Make sure access toke nis valid before navigating
    // returns true if we are good to proceed, false if we must show login
    suspend fun checkAccessToken(): Boolean {
        val accessToken = storageManager.accessTokenFlow.first()
        if (accessToken.isNotBlank() && !isExpired(accessToken)) return true

        // try refresh
        return try {
            val refreshed = api.refresh(storageManager.refreshTokenFlow.first())
            storageManager.updateTokens(
                refreshed.accessToken, refreshed.refreshToken
            )
            true
        } catch (_: Exception) {
            storageManager.clearDataStore()
            false
        }
    }
}