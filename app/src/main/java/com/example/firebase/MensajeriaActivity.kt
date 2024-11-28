package com.example.firebase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MensajeriaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        var broadcastReceiver : BroadcastReceiver? = null
        if (broadcastReceiver!= null) {
            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    Toast.makeText(this@MensajeriaActivity, "Mensaje succes", Toast.LENGTH_SHORT)
                        .show()
                    mostrarMensaje(intent)
                }
            }
            registerReceiver(broadcastReceiver,
                IntentFilter("datosNotify"), RECEIVER_NOT_EXPORTED)
        }
    }

    private fun mostrarMensaje(intent: Intent?) {
        val bundle = intent?.extras

        setContent {
            var contendido by remember { mutableStateOf(bundle?.getString("titulo") ?:"")}

                var titulo by remember { mutableStateOf(bundle?.getString("contenido")?:"")
        }

            Column {
                Text(text = "Contenido")
                Text(text = "Tittle")

            }
        }
    }
}
