package com.amk.weatherforall.services

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.app.NotificationCompat
import com.amk.weatherforall.R

class NetworkChangeReceiver : BroadcastReceiver() {

    private var messageId:Int = 10000
    override fun onReceive(context: Context, intent: Intent) {

        if(intent.extras !=null) {
            val info:NetworkInfo? = (intent.extras?.get(ConnectivityManager.EXTRA_NETWORK_INFO) as NetworkInfo)
            if(info?.state?.equals(NetworkInfo.State.CONNECTED) == false){
                val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, "2")
                    .setSmallIcon(R.drawable.cloudy)
                    .setContentTitle("Нет сети")
                    .setContentText("Not Network")
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(messageId++, builder.build())
            }
        }
    }


}
