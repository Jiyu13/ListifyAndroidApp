package com.example.listifyjetapp.ui.theme

import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.example.listifyjetapp.R


// Initialize the GoogleFont.Provider with the credentials for Google Fonts
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Define a FontFamily
val barriecitoFont = FontFamily(
    Font(
        googleFont = GoogleFont("Barriecito"),
        fontProvider = provider,
        //weight = FontWeight.Bold
        //style =
    )
)