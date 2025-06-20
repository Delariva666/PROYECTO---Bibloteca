package com.example.proyecto_bibloteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.proyecto_bibloteca.data.SQLiteHelper

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = SQLiteHelper(this)

        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        btnRegistrar.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val contraseña = etContrasena.text.toString().trim()

            if (correo.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(this, "Por favor, completa los campos", Toast.LENGTH_SHORT).show()
            } else {
                val esValido = dbHelper.validarEstudiante(correo, contraseña)
                if (esValido) {
                    Toast.makeText(this, "Bienvenido estudiante", Toast.LENGTH_SHORT).show()

                    // Ir a la nueva pantalla
                    val intent = Intent(this, HomeEstudianteActivity::class.java)
                    startActivity(intent)
                    finish() // opcional: cierra la pantalla de login
                }

            }

        }
    }
}