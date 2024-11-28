package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {
            HomeScreen()
        }
    }

    @Composable
    fun HomeScreen() {
        val user = auth.currentUser

        // Colores personalizados
        val backgroundColor = Color(0xFFF1F1F1) // Color de fondo suave
        val primaryColor = Color(0xFF6200EE) // Color principal
        val secondaryColor = Color(0xFF03DAC5) // Color secundario para contrastar

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Bienvenida con nombre de usuario
            Text(
                text = "Bienvenido ${user?.email ?: "Usuario"}",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = primaryColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Icono de usuario
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Icono de usuario",
                tint = primaryColor,
                modifier = Modifier.size(120.dp).padding(bottom = 24.dp)
            )

            // Botón de cerrar sesión
            OutlinedButton(
                onClick = {
                    auth.signOut()
                    val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish() // Finaliza HomeActivity para que no se pueda volver atrás
                    Toast.makeText(this@HomeActivity, "Has cerrado sesión", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 32.dp), // Mayor espacio en los márgenes
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = secondaryColor,
                    contentColor = Color.White
                ),
                border = BorderStroke(2.dp, secondaryColor)
            ) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Cerrar sesión",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color.White
                )
                Text(text = "Cerrar Sesión", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold))
            }
        }
    }
}
