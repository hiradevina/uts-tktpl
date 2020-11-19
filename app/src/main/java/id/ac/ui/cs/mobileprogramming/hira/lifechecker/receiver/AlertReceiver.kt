package id.ac.ui.cs.mobileprogramming.hira.lifechecker.receiver

import android.annotation.SuppressLint
import android.app.IntentService
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.Handler
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.constants.AppConstants
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.helper.NotificationHelper
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.service.LifecheckerService
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.add_emergency.AddEmergencyActivity
import id.ac.ui.cs.mobileprogramming.hira.lifechecker.ui.countdown.CountDownActivity


class AlertReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        val nama = intent.getStringExtra(AppConstants.notificationTitleIntentKey)
        val desc = intent.getStringExtra(AppConstants.notificationDescriptionIntentKey)
        val notificationHelper = NotificationHelper(context)
        val intent = Intent(context, CountDownActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val builder: NotificationCompat.Builder =
            notificationHelper.getChannelNotification(nama, desc).setContentIntent(pendingIntent)
        notificationHelper.notificationManager?.notify(1, builder.build())

        Handler().postDelayed({
            Intent(context, LifecheckerService::class.java).also { intent ->
                context.startService(intent)
            }
        }, 5000)


    }
}