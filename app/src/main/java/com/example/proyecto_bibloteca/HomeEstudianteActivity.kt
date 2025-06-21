package com.example.proyecto_bibloteca

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_bibloteca.data.SQLiteHelper

class HomeEstudianteActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var autorInput: EditText
    private lateinit var tituloInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_estudiante)

        // Inicializar base de datos y componentes
        dbHelper = SQLiteHelper(this)
        recyclerView = findViewById(R.id.librosRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        autorInput = findViewById(R.id.autorInput)
        tituloInput = findViewById(R.id.tituloInput)

        // Buscar por autor
        findViewById<Button>(R.id.buscarAutor).setOnClickListener {
            mostrarLibros(autorInput.text.toString(), "")
        }

        // Buscar por título
        findViewById<Button>(R.id.buscarTitulo).setOnClickListener {
            mostrarLibros("", tituloInput.text.toString())
        }

        // Mostrar todos los libros al iniciar
        mostrarLibros("", "")

        // Botón cerrar sesión
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)
        btnCerrarSesion.setOnClickListener {
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Cierra esta actividad para no volver con "atrás"
        }
    }

    // Mostrar libros según filtros (autor, título)
    private fun mostrarLibros(autor: String, titulo: String) {
        val libros = dbHelper.buscarLibros(autor, titulo)

        val adapter = LibroAdapter(
            libros,
            reservarCallback = {
                dbHelper.reservarLibro(it.id)
                mostrarLibros(autor, titulo) // Recarga lista
            },
            detallesCallback = {
                Toast.makeText(this, "Autor: ${it.autor}", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.adapter = adapter
    }
}
