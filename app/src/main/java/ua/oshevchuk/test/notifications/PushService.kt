package ua.oshevchuk.test.notifications


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.provider.ContactsContract.Intents.Insert.ACTION
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ua.oshevchuk.test.R
import ua.oshevchuk.test.core.activity.MainActivity
import ua.oshevchuk.test.utils.channel
import kotlin.random.Random


/**
 * @author shevsan on 30.07.2022
 */

class PushService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }
    override fun onMessageReceived(message: RemoteMessage) {
        val data: Map<*, *> = message.data
        Log.e("Message", "Could not parse malformed JSON: \"$data\"")
        if (message.data.isNotEmpty()) {
            val userId = message.data["userId"].toString()
            val changesCount = message.data["changesCount"]!!.toInt()
            val intent = Intent(ACTION)
            intent.putExtra("userId", userId)
            intent.putExtra("changesCount", changesCount)
            sendBroadcast(intent)
        }
        showNotifi(message)

    }
    private fun showNotifi(message: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = Random.nextInt()

        createNotificationChannel(notificationManager)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        val notification = NotificationCompat.Builder(this, channel)
            .setContentTitle(message.notification!!.title)
            .setContentText(message.notification!!.body)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(id, notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "simpleName"
        val channel = NotificationChannel(channel, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
            description = "SimpleDesc"
            enableLights(true)
        }
        notificationManager.createNotificationChannel(channel)
    }

}