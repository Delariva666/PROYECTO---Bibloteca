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

        val etCorreo = findViewById<EditText>(R.id.etCorreoRegistro)
        val etContrasena = findViewById<EditText>(R.id.etContrasenaRegistro)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarRegistro)

        btnGuardar.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val contraseña = etContrasena.text.toString().trim()

            if (correo.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(this, "Campos vacíos", Toast.LENGTH_SHORT).show()
            } else {
                val exito = dbHelper.insertarEstudiante(correo, contraseña)
                if (exito) {
                    Toast.makeText(this, "Alumno registrado correctamente", Toast.LENGTH_SHORT).show()
                    finish() // cerrar pantalla
                } else {
                    Toast.makeText(this, "Error: ya existe ese alumno", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
