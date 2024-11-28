package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class LoginActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {
            AuthContent()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AuthContent() {
        var correo by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }
        var isLoginMode by remember { mutableStateOf(true) }

        // Fondo de pantalla suave
        val backgroundColor = Color(0xFFF1F1F1)

        // Pantalla de Login / Registro
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isLoginMode) "Iniciar Sesión" else "Registrar Usuario",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF6200EE),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Correo
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo Electrónico") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email Icon"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { /* Mover al siguiente campo */ }),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF6200EE),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF6200EE)
                ),
                singleLine = true
            )

            // Contraseña
            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { /* Submit form */ }),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF6200EE),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color(0xFF6200EE)
                ),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            // Botón de Iniciar sesión o Registrar
            OutlinedButton(
                onClick = {
                    if (isLoginMode) {
                        iniciarSesion(correo, pass)
                    } else {
                        registrar(correo, pass)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFF6200EE),
                    contentColor = Color.White
                ),
                border = BorderStroke(2.dp, Color(0xFF6200EE))
            ) {
                Text(text = if (isLoginMode) "Iniciar Sesión" else "Registrar", style = MaterialTheme.typography.bodyLarge)
            }

            // Enlace para cambiar entre modo Login y Registro
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(
                onClick = {
                    isLoginMode = !isLoginMode
                }
            ) {
                Text(text = if (isLoginMode) "¿No tienes cuenta? Regístrate" else "¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }

    private fun registrar(correo: String, pass: String) {
        auth.createUserWithEmailAndPassword(correo, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
                } else {
                    val exception = task.exception as FirebaseAuthException
                    Toast.makeText(baseContext, "Error: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun iniciarSesion(correo: String, pass: String) {
        auth.signInWithEmailAndPassword(correo, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Si el login fue exitoso, redirigir a HomeActivity
                    Toast.makeText(baseContext, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish() // Finaliza LoginActivity para no poder regresar
                } else {
                    // Manejar el error
                    val exception = task.exception as FirebaseAuthException
                    Toast.makeText(baseContext, "Error: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
