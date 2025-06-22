package com.example.proyecto_bibloteca

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_bibloteca.data.SQLiteHelper

class ReservacionInfoActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservacion_info)

        dbHelper = SQLiteHelper(this)

        val tvDias = findViewById<TextView>(R.id.tvDias)
        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val tvUbicacion = findViewById<TextView>(R.id.tvUbicacion)
        val tvHora = findViewById<TextView>(R.id.tvHora)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        val btnRegresar = findViewById<Button>(R.id.btnRegresar2)

        // Recibir info desde el intent
        val nombre = intent.getStringExtra("nombre") ?: ""
        val dias = intent.getStringExtra("dias") ?: ""
        val ubicacion = intent.getStringExtra("ubicacion") ?: ""
        val hora = intent.getStringExtra("hora") ?: ""

        tvNombre.text = "Nombre del estudiante: $nombre"
        tvDias.text = "Días: $dias"
        tvUbicacion.text = "Ubicación: $ubicacion"
        tvHora.text = "Hora: $hora"

        btnEliminar.setOnClickListener {
            val eliminado = dbHelper.eliminarReservacionPorNombre(nombre)
            if (eliminado) {
                Toast.makeText(this, "Reservación eliminada", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "No se pudo eliminar", Toast.LENGTH_SHORT).show()
            }
        }


        btnRegresar.setOnClickListener {
            val intent = Intent(this, HomeEstudianteActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // opcional, por si no quieres que regrese con "Back"
        }
    }
}
