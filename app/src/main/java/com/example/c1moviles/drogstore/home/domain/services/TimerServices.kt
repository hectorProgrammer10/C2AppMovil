package com.example.c1moviles.drogstore.home.domain.services

import android.app.*
import android.content.*
import android.os.*
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class TimerServices : Service() {

    private val binder = LocalBinder()
    private var timerJob: Job? = null
    private var onTickCallback: ((Long) -> Unit)? = null
    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "timer_service_channel"
    private val ACTION_YA_LLEGO = "com.example.c1moviles.YA_LLEGO"

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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_YA_LLEGO) {
            handleYaLlego()
        }
        return START_STICKY
    }

    fun startTimer(duration: Long, onTick: (Long) -> Unit) {
        onTickCallback = onTick
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            var remainingTime = duration
            while (remainingTime > 0) {
                delay(1000)
                remainingTime -= 1000
                onTickCallback?.invoke(remainingTime)
                updateNotification(remainingTime)
            }
            onTickCallback?.invoke(0)
            stopSelf()
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        onTickCallback = null
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun handleYaLlego() {
        onTickCallback?.invoke(0) // Notificar que llegó
        stopTimer() // Detener el servicio
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
        val notification = createNotification(30 * 60 * 1000) // 30 minutos iniciales
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun updateNotification(remainingTime: Long) {
        val notification = createNotification(remainingTime)
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotification(remainingTime: Long): Notification {
        val minutes = (remainingTime / 1000) / 60
        val seconds = (remainingTime / 1000) % 60

        // Intent para el botón "¡Ya llegó!"
        val yaLlegoIntent = Intent(this, TimerServices::class.java).apply {
            action = ACTION_YA_LLEGO
        }
        val yaLlegoPendingIntent = PendingIntent.getService(
            this, 0, yaLlegoIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Fast Delivery Temporizador")
            .setContentText("Tiempo restante: ${minutes}:${seconds} minutos.") // Texto corto para cuando la notificación no esté expandida
            .setStyle(NotificationCompat.BigTextStyle() // Estilo expandible
                .bigText("Tiempo restante: ${minutes}:${seconds} minutos.\n¡Yupi! Tu pedido ya va en camino. Si no llega en 30 minutos... ¡te lo regalamos!\n" +
                        "\uD83D\uDEFA \uD83D\uDEFA \uD83D\uDEFA \uD83D\uDEFA \uD83D\uDEFA \uD83D\uDEFA _-_-__"))
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_media_pause, "¿Ya llegó?", yaLlegoPendingIntent)
            .build()
    }
}
