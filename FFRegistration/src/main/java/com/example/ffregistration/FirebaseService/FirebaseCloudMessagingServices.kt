package com.example.ffregistration.FirebaseService

import android.R
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
//import ff.steel.procurement.LogIn.Login
import com.example.ffregistration.General.Storage


class FirebaseCloudMessagingServices(cntxt: Context) : FirebaseMessagingService() {
    var context:Context
    init {
        context=cntxt
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FirebaseMessAx","FirebaseMessagingService"+"Orkeya washwal" +remoteMessage +" # "+remoteMessage.notification)

        if (remoteMessage.data != null){
//            val title = remoteMessage.notification!!.title
//            val message = remoteMessage.notification!!.body
            Log.d("N_Notification","#555 "+remoteMessage.data.size)
            Log.d("N_Notification","#555 "+remoteMessage.data.get("message"))
            //remoteMessage.data.get("title")
            sendNotification(Storage(context).GetName()+""+"", remoteMessage.data.get("body")+"")

        }

        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification!!.title // will hold FCM Title
            Log.d("N_Notification","#0 "+remoteMessage.notification?.toString())
            Log.d("N_Notification","#1 "+remoteMessage.notification?.title)
            Log.d("N_Notification","#2 "+remoteMessage.notification?.body)
            Log.d("N_Notification","#5 "+remoteMessage.to)
            Log.d("N_Notification","#6 "+remoteMessage.from)
            Log.d("N_Notification","#7 "+remoteMessage.data.size)

            val message = remoteMessage.notification!!.body //will hold FCM message body
            sendNotification(title, message)
            Log.d("FirebaseMessAx2","FirebaseMessagingService"+"Orkeya washwal")
        }
    }

    private fun sendNotification(title: String?, message: String?) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val NOTIFICATION_CHANNEL_ID = "admin_channel_1"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX)
            // Configure the notification channel.
            notificationChannel.description = "Sample Channel description"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val intent = Intent(this, Storage(applicationContext).GetPackageName()::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        notificationBuilder.setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.sym_def_app_icon) //.setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(title)
                .setContentText(message)
        notificationManager.notify(1, notificationBuilder.build())
    }
}