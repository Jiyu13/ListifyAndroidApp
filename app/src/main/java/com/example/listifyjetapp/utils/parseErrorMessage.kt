package com.example.listifyjetapp.utils

import org.json.JSONObject

// Helper function to parse error message from JSON response
fun parseErrorMessage(errorBody: String?): String? {
    return try {
        errorBody?.let {
            // Parse the JSON to extract the "error" field
            val jsonObject = JSONObject(it)
            when {
                jsonObject.has("message") -> jsonObject.getString("message")
                jsonObject.has("error") -> jsonObject.getString("error")
                jsonObject.has("details") -> jsonObject.getString("details")
                jsonObject.has("errors") -> jsonObject.getJSONArray("errors").let { arr ->
                    (0 until arr.length()).joinToString(", ") { idx -> arr.getString(idx) }
                }
                else -> errorBody // fallback: show raw body
            }
        }
    } catch (parseError: Exception) {
        errorBody // Return errorBody if parsing fails
    }
}