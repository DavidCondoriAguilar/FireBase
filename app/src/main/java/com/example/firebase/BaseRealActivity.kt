package com.example.firebase

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BaseRealActivity : ComponentActivity() {

    val database = Firebase.database
    val myRef = database.getReference("date")

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Establecer un valor inicial en Firebase
        myRef.setValue("Hello WorldXD")

        setContent {
            // Variables de estado para el texto introducido por el usuario y el valor recibido de Firebase
            var textoMensaje by remember { mutableStateOf("") }
            var textoRecibido by remember { mutableStateOf("") }

            // Listener para obtener datos de Firebase en tiempo real
            LaunchedEffect(Unit) {
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        // Actualiza el estado con el valor recibido
                        val value = snapshot.getValue<String>()
                        if (value != null) {
                            // Asignar el valor recibido a la variable textoRecibido
                            textoRecibido = value
                            Log.d("Firebase", "Value is: $value")
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w("Firebase", "Failed to read value.", error.toException())
                    }
                })
            }

            // UI
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Campo de texto para ingresar un mensaje
                OutlinedTextField(
                    value = textoMensaje,
                    onValueChange = { textoMensaje = it },  // Actualiza el estado de textoMensaje
                    label = { Text("Mensaje") }
                )

                // Botón para enviar el mensaje a Firebase
                Button(onClick = {
                    // Actualiza el valor en Firebase con el texto ingresado
                    myRef.setValue(textoMensaje)
                }) {
                    Text(text = "Enviar")
                }

                // Mostrar el texto recibido desde Firebase
                Text(text = "Recibido: $textoRecibido")
            }
        }
    }
}
