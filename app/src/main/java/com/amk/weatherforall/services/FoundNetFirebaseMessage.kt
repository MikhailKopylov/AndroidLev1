package com.amk.weatherforall.services

import android.R
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FoundNetFirebaseMessage : FirebaseMessagingService() {

    private var messageId:Int = 0

    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d("Push Message", p0.notification?.body)
        val title:String = p0.notification?.title ?: "Push message"
        val text:String = p0.notification?.body ?: "Body message"

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "2")
            .setSmallIcon(R.drawable.ic_menu_day)
            .setContentTitle(title)
            .setContentText(text)
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(messageId++, builder.build())
//        Toast.makeText(this, "$title  $text", Toast.LENGTH_LONG ).show()
    }

    override fun onNewToken(p0: String) {
        sendRegistrationToServer(p0)
    }

    private fun sendRegistrationToServer(token:String){

    }
}
