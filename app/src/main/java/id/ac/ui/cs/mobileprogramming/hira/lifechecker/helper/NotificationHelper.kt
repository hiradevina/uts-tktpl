package id.ac.ui.cs.mobileprogramming.hira.lifechecker.helper

import android.R
import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.core.app.NotificationCompat


class NotificationHelper(base: Context?) : ContextWrapper(base) {
    var notificationManager: NotificationManager? = null
        get() {
            if (field == null) {
                field =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return field
        }
        private set

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannels() {
        val notificationChannel = NotificationChannel(
            channelID,
            channelname,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        notificationChannel.lightColor = R.color.background_light
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        notificationManager!!.createNotificationChannel(notificationChannel)
    }

    fun getChannelNotification(
        title: String?,
        message: String?
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(
            applicationContext,
            channelID
        )
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.alert_dark_frame)
    }

    companion object {
        const val channelID = "NotificationChannelID"
        const val channelname = "NotificationChannelName"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels()
        }
    }
}
