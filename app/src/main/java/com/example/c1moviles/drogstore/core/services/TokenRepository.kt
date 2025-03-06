package com.example.c1moviles.drogstore.core.services

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getToken(): String? {
        val sharedPreferences = context.getSharedPreferences("FCM_PREFS", Context.MODE_PRIVATE)
        return sharedPreferences.getString("fcm_token", null)
    }
}