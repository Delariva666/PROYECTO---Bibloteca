package com.example.proyecto_bibloteca

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_bibloteca.data.SQLiteHelper
import com.example.proyecto_bibloteca.Libro

class HomeEstudianteActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var autorInput: EditText
    private lateinit var tituloInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_estudiante)

        dbHelper = SQLiteHelper(this)
        recyclerView = findViewById(R.id.librosRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        autorInput = findViewById(R.id.autorInput)
        tituloInput = findViewById(R.id.tituloInput)

        findViewById<Button>(R.id.buscarAutor).setOnClickListener {
            mostrarLibros(autorInput.text.toString(), "")
        }

        findViewById<Button>(R.id.buscarTitulo).setOnClickListener {
            mostrarLibros("", tituloInput.text.toString())
        }

        mostrarLibros("", "")
    }

    private fun mostrarLibros(autor: String, titulo: String) {
        val libros = dbHelper.buscarLibros(autor, titulo)

        val adapter = LibroAdapter(libros,
            reservarCallback = {
                dbHelper.reservarLibro(it.id)
                mostrarLibros(autor, titulo)
            },
            detallesCallback = {
                Toast.makeText(this, "Autor: ${it.autor}", Toast.LENGTH_SHORT).show()
            })

        recyclerView.adapter = adapter
    }
}
