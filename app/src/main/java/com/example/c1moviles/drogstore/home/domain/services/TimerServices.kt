package com.example.c1moviles.drogstore.home.domain.services

import android.app.*
import android.content.Intent
import android.os.*
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class TimerServices : Service() {

    private val binder = LocalBinder()
    private var timerJob: Job? = null
    private var onTickCallback: ((Long) -> Unit)? = null
    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "timer_service_channel"

    inner class LocalBinder : Binder() {
        fun getService(): TimerServices = this@TimerServices
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForegroundService()
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun startTimer(duration: Long, onTick: (Long) -> Unit) {
        onTickCallback = onTick
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            var remainingTime = duration
            while (remainingTime > 0) {
                delay(1000)
                remainingTime -= 1000
                onTickCallback?.invoke(remainingTime)
                updateNotification(remainingTime) // 🔹 ACTUALIZAR NOTIFICACIÓN
            }
            onTickCallback?.invoke(0)
            stopSelf() // 🔹 Detener el servicio cuando termine
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        onTickCallback = null
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Timer Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun startForegroundService() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Fast Delivery Temporizador")
            .setContentText("Tiempo restante: 30 minutos")
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setOngoing(true)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun updateNotification(remainingTime: Long) {
        val newRemainigTime = remainingTime/1000
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Fast Delivery Temporizador")
            .setContentText("Tiempo restante: ${newRemainigTime / 60}:${newRemainigTime % 60} segundos.") // Texto corto para cuando la notificación no esté expandida
            .setStyle(NotificationCompat.BigTextStyle() // Estilo expandible
                .bigText("Tiempo restante: ${newRemainigTime / 60}:${newRemainigTime % 60} segundos.\n¡Yupi! Tu pedido ya va en camino. Si no llega en 30 minutos... ¡te lo regalamos!\n" +
                        "\uD83D\uDEFA \uD83D\uDEFA \uD83D\uDEFA \uD83D\uDEFA \uD83D\uDEFA \uD83D\uDEFA _-_-__"))
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setOngoing(true)
            .build()

        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, notification)
    }
}
