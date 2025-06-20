package com.example.proyecto_bibloteca

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_bibloteca.data.SQLiteHelper

class RegistroActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        dbHelper = SQLiteHelper(this)

        val etId = findViewById<EditText>(R.id.etIdAlumno)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etSemestre = findViewById<EditText>(R.id.etSemestre)
        val etCorreo = findViewById<EditText>(R.id.etCorreoRegistro)
        val etContrasena = findViewById<EditText>(R.id.etContrasenaRegistro)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarRegistro)

        btnGuardar.setOnClickListener {
            val id = etId.text.toString().trim()
            val nombre = etNombre.text.toString().trim()
            val semestre = etSemestre.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val contrasena = etContrasena.text.toString().trim()

            if (id.isEmpty() || nombre.isEmpty() || semestre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val exito = dbHelper.insertarEstudianteCompleto(id, nombre, semestre, correo, contrasena)
                if (exito) {
                    Toast.makeText(this, "Alumno registrado correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error: ya existe ese alumno o correo", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

