package com.example.proyecto_bibloteca

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.content.Intent

class LibroDetalleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro_detalle)

        // Obtener datos desde el intent
        val titulo = intent.getStringExtra("titulo")
        val autor = intent.getStringExtra("autor")
        val imagen = intent.getIntExtra("imagen", R.drawable.h)
        val paginas = intent.getIntExtra("paginas", 0)
        val idioma = intent.getStringExtra("idioma")
        val anio = intent.getIntExtra("anio", 0)
        val sinopsis = intent.getStringExtra("sinopsis")

        // Mostrar en la UI
        findViewById<TextView>(R.id.detalleTitulo).text = titulo
        findViewById<TextView>(R.id.detalleAutor).text = "Autor: $autor"
        findViewById<ImageView>(R.id.detalleImagen).setImageResource(imagen)
        findViewById<TextView>(R.id.detallePaginas).text = "Páginas: $paginas"
        findViewById<TextView>(R.id.detalleIdioma).text = "Idioma: $idioma"
        findViewById<TextView>(R.id.detalleAnio).text = "Año: $anio"
        findViewById<TextView>(R.id.detalleSinopsis).text = "Sinopsis:\n$sinopsis"

        val btnRegresar = findViewById<Button>(R.id.btnRegresar)
        btnRegresar.setOnClickListener {
            val intent = Intent(this, HomeEstudianteActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // opcional, por si no quieres que regrese con "Back"
        }

    }

}
