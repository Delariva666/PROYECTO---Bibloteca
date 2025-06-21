package com.example.proyecto_bibloteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.proyecto_bibloteca.data.SQLiteHelper

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper
    private val CONTRASENA_ADMIN = "admin123"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = SQLiteHelper(this)

        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        val etContrasena2 = findViewById<EditText>(R.id.etContrasena2)
        val btnLogin2 = findViewById<Button>(R.id.btnLogin2)

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

        btnLogin2.setOnClickListener {
            val contrasenaAdmin = etContrasena2.text.toString().trim()

            if (contrasenaAdmin == CONTRASENA_ADMIN) {
                Toast.makeText(this, "Bienvenido administrador", Toast.LENGTH_SHORT).show()

                // Redirige a pantalla del administrador
                val intent = Intent(this, AdminHomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
            }
        }
        etCorreo.text.clear()
        etContrasena.text.clear()
        etContrasena2.text.clear()

    }
}