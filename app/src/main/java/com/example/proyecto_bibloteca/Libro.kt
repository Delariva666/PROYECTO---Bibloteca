package com.example.proyecto_bibloteca

data class Libro(
    val id: Int,
    val titulo: String,
    val autor: String,
    var estado: String,
    val imagen: Int
)

