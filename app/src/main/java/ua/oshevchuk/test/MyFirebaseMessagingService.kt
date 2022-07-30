package ua.oshevchuk.test

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

/**
 * @author shevsan on 30.07.2022
 */

class MyFirebaseMessagingService: FirebaseMessagingService() {


//    override fun onMessageReceived(message: RemoteMessage) {
//        val data: Map<*, *> = message.data
//        Log.e("Message", "Could not parse malformed JSON: \"$data\"")
//        if (message.data.isNotEmpty()) {
//            val userId = message.data["userId"].toString()
//            val changesCount = message.data["changesCount"]!!.toInt()
//            val intent = Intent(ACTION)
//            intent.putExtra("userId", userId)
//            intent.putExtra("changesCount", changesCount)
//            sendBroadcast(intent)
//        }
//        showNotification(message)
//    }
//
//    private fun showNotification(message: RemoteMessage) {
//        val intent = Intent(this, MainActivity::class.java)
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val notificationId = Random.nextInt()
//
//        createNotificationChannel(notificationManager)
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
//        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_star)
//            .setContentTitle(message.notification!!.title)
//            .setContentText(message.notification!!.body)
//            .setAutoCancel(true)
//            .setContentIntent(pendingIntent)
//            .build()
//        notificationManager.notify(notificationId, notification)
//    }
//
//    private fun createNotificationChannel(notificationManager: NotificationManager) {
//        val channelName = "channelName"
//        val channel = NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
//            description = "My channel description"
//            enableLights(true)
//        }
//        notificationManager.createNotificationChannel(channel)
//    }
//    companion object{
//        private const val channel =
//    }
}