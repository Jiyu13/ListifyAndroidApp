package com.example.listifyjetapp.utils

import org.json.JSONObject

// Helper function to parse error message from JSON response
fun parseErrorMessage(errorBody: String?): String? {
    return try {
        errorBody?.let {
            // Parse the JSON to extract the "error" field
            val jsonObject = JSONObject(it)
            jsonObject.getString("error")
        }
    } catch (parseError: Exception) {
        null // Return null if parsing fails
    }
}