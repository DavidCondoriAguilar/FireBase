package com.example.firebase

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseInstanceIDService :  FirebaseMessagingService(){
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d("TAG", "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("TAG", "Message data payload: ${remoteMessage.data}")

        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d("TAG", "Message Notification Body: ${it.body}")
            val bundle = Bundle()
            bundle.putString("titulo", it.title)
            bundle.putString("contenido", it.body)
            bundle.putString("imagen", it.imageUrl.toString())

            val intent = Intent("datosNotify")
            intent.putExtras(bundle)
            sendBroadcast(intent)
        }
    }
    }
