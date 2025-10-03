package com.example.listifyjetapp.components.copyToClip

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE

@SuppressLint("ServiceCast")
fun copyToClipboard(context: Context, text: String) {
    val clipboardManager =
        context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("code", text)
    clipboardManager.setPrimaryClip(clip)
}