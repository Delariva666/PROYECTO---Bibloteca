package com.example.proyecto_bibloteca

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_bibloteca.data.SQLiteHelper

class ReservacionActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservacion)

        dbHelper = SQLiteHelper(this)

        val etNombreEstudiante = findViewById<EditText>(R.id.etNombreEstudiante)
        val etNombreLibro = findViewById<EditText>(R.id.etNombreLibro)
        val etDias = findViewById<EditText>(R.id.etDias)
        val etUbicacion = findViewById<EditText>(R.id.etUbicacion)
        val etHora = findViewById<EditText>(R.id.etHora)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarReservacion)

        // Título del libro recibido
        val tituloLibro = intent.getStringExtra("titulo") ?: ""
        etNombreLibro.setText(tituloLibro)

        btnGuardar.setOnClickListener {
            val estudiante = etNombreEstudiante.text.toString().trim()
            val libro = etNombreLibro.text.toString().trim()
            val dias = etDias.text.toString().trim()
            val ubicacion = etUbicacion.text.toString().trim()
            val hora = etHora.text.toString().trim()

            if (estudiante.isEmpty() || libro.isEmpty() || dias.isEmpty() || ubicacion.isEmpty() || hora.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val resultado = dbHelper.insertarReservacion("$estudiante - $libro", dias, ubicacion, hora)
                if (resultado) {
                    Toast.makeText(this, "Reservación guardada", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

