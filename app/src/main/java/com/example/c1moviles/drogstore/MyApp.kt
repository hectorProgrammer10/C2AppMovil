package com.example.c1moviles.drogstore

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging

class MyApp : Application() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "notification_fcm"
    }

    override fun onCreate() {
        super.onCreate()

        // Obtener el token de FCM
        Firebase.messaging.token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("MyApp", "Error al obtener el token de FCM", task.exception)
                return@addOnCompleteListener
            }

            // Obtener el token
            val token = task.result
            saveTokenToPreferences(token)

            // Imprimir el token en el Logcat
            Log.d("MyApp", "El token de FCM es: $token")
        }

        // Crear el canal de notificaciones
        createNotificationChannel()
    }

    private fun saveTokenToPreferences(token: String) {
        val sharedPreferences = getSharedPreferences("FCM_PREFS", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("fcm_token", token)
            apply()
        }
    }

    private fun createNotificationChannel() {
        // Verificar si la versión de Android es Oreo (API 26) o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Notification de FCM",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Notificación FCM"
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}