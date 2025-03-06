package com.example.c1moviles.drogstore.core.services

import android.app.Notification
import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.c1moviles.drogstore.MyApp
import com.example.c1moviles.drogstore.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Verificar si el mensaje contiene datos
        if (message.data.isNotEmpty()) {
            Log.d("FcmService", "Datos del mensaje: ${message.data}")
        }

        // Verificar si el mensaje contiene una notificación
        message.notification?.let { notification ->
            // Mostrar la notificación en la barra de estado
            showNotification(message)
        }
    }

    private fun showNotification(message: RemoteMessage) {
        // Obtener el NotificationManager
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Crear la notificación
        val notification: Notification = NotificationCompat.Builder(this, MyApp.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(message.notification?.title) // Título de la notificación
            .setContentText(message.notification?.body)  // Cuerpo de la notificación
            .setSmallIcon(R.drawable.logofast)           // Icono pequeño
            .setAutoCancel(true)                         // La notificación se cierra al hacer clic
            .build()

        // Mostrar la notificación
        notificationManager.notify(1, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FcmService", "Nuevo token de FCM: $token")
    }
}