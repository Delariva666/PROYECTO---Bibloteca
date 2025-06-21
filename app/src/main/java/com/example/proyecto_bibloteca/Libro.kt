package com.example.proyecto_bibloteca

data class Libro(
    val id: Int,
    val titulo: String,
    val autor: String,
    var estado: String,
    val imagen: Int,
    val paginas: Int? = null,
    val idioma: String? = null,
    val anio: Int? = null,
    val sinopsis: String? = null
)


