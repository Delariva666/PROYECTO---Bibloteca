package com.example.proyecto_bibloteca.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import com.example.proyecto_bibloteca.Libro // Asegúrate de que la ruta a Libro sea correcta
import com.example.proyecto_bibloteca.R
import com.example.proyecto_bibloteca.Reservacion


class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, "BibliotecaDB", null, 6) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableEstudiantes = """
            CREATE TABLE Estudiantes (
                id INTEGER PRIMARY KEY,
                nombre TEXT NOT NULL,
                semestre INTEGER NOT NULL,
                correo TEXT UNIQUE NOT NULL,
                contraseña TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableEstudiantes)

        val createTableLibros = """
            CREATE TABLE Libros (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                autor TEXT NOT NULL,
                estado TEXT NOT NULL,
                imagen INTEGER NOT NULL,
                paginas INTEGER,
                idioma TEXT,
                anio INTEGER,
                sinopsis TEXT
            )
        """.trimIndent()
        db.execSQL(createTableLibros)

        // Libros de prueba
        val libros = listOf(
            Triple("El Principito", "Antoine de Saint-Exupéry", R.drawable.el_principito),
            Triple("Álgebra de Baldor", "Baldor", R.drawable.baldor),
            Triple("Harry Potter", "J.K. Rowling", R.drawable.harry_potter),
            Triple("Harry Potter y la piedra filosofal", "J.K. Rowling", R.drawable.harry_potter),
            Triple("Cien años de soledad", "Gabriel García Márquez", R.drawable.sol),
            Triple("Don Quijote de la Mancha", "Miguel de Cervantes", R.drawable.quijote),
            Triple("La sombra del viento", "Carlos Ruiz Zafón", R.drawable.sombra),
            Triple("El código Da Vinci", "Dan Brown", R.drawable.codigo),
            Triple("Orgullo y prejuicio", "Jane Austen", R.drawable.orgullo),
            Triple("Matar a un ruiseñor", "Harper Lee", R.drawable.matar),
            Triple("El alquimista", "Paulo Coelho", R.drawable.el),
            Triple("Crónica de una muerte anunciada", "Gabriel García Márquez", R.drawable.a),
            Triple("El nombre de la rosa", "Umberto Eco", R.drawable.b),
            Triple("El retrato de Dorian Gray", "Oscar Wilde", R.drawable.c),
            Triple("La casa de los espíritus", "Isabel Allende", R.drawable.d),
            Triple("Los juegos del hambre", "Suzanne Collins", R.drawable.e),
            Triple("Sapiens: De animales a dioses", "Yuval Noah Harari", R.drawable.f),
            Triple("El señor de los anillos", "J.R.R. Tolkien", R.drawable.g),
            Triple("El diario de Ana Frank", "Ana Frank", R.drawable.h),
            Triple("La ciudad de las bestias", "Isabel Allende", R.drawable.bestias),
            Triple("Ficciones", "Jorge Luis Borges", R.drawable.ficc),
            Triple("La tregua", "Mario Benedetti", R.drawable.treagua),
            Triple("La metamorfosis", "Franz Kafka", R.drawable.meta),
            Triple("Rayuela", "Julio Cortázar", R.drawable.rayuela),
            Triple("El viejo y el mar", "Ernest Hemingway", R.drawable.viejo),
            Triple("Ensayo sobre la ceguera", "José Saramago", R.drawable.ceguera),
            Triple("La insoportable levedad del ser", "Milan Kundera", R.drawable.ser),
            Triple("Meditaciones", "Marco Aurelio", R.drawable.meditaciones),
            Triple("El hombre en busca de sentido", "Viktor Frankl", R.drawable.sentido),
            Triple("Cumbres borrascosas", "Emily Brontë", R.drawable.cumbres),
            Triple("Crimen y castigo", "Fiódor Dostoyevski", R.drawable.crimen),
            Triple("Guerra y paz", "León Tolstói", R.drawable.paz),
            Triple("Lolita", "Vladimir Nabokov", R.drawable.lolita),
            Triple("El gran Gatsby", "F. Scott Fitzgerald", R.drawable.gran),
            Triple("Moby Dick", "Herman Melville", R.drawable.moby),
            Triple("El perfume", "Patrick Süskind", R.drawable.perfume),
            Triple("La carretera", "Cormac McCarthy", R.drawable.carretera),
            Triple("El jardín secreto", "Frances Hodgson Burnett", R.drawable.secreto),
            Triple("La ladrona de libros", "Markus Zusak", R.drawable.ladrona),
        )

        val createTableReservaciones = """
    CREATE TABLE Reservaciones (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        nombre TEXT NOT NULL,
        dias TEXT NOT NULL,
        ubicacion TEXT NOT NULL,
        hora TEXT NOT NULL
    )
""".trimIndent()
        db.execSQL(createTableReservaciones)

        for ((titulo, autor, imagen) in libros) {
            val insertLibro = ContentValues().apply {
                put("titulo", titulo)
                put("autor", autor)
                put("estado", "Disponible")
                put("imagen", imagen)
                put("paginas", 200) // valor por defecto
                put("idioma", "Español")
                put("anio", 2000)
                put("sinopsis", "Sinopsis no disponible.")
            }
            db.insert("Libros", null, insertLibro)
        }



    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Estudiantes")
        db.execSQL("DROP TABLE IF EXISTS Libros")
        onCreate(db)
    }

    fun insertarEstudianteCompleto(id: String, nombre: String, semestre: String, correo: String, contraseña: String): Boolean {
        val db = writableDatabase

        val cursor = db.rawQuery("SELECT * FROM Estudiantes WHERE correo = ? OR id = ?", arrayOf(correo, id))
        if (cursor.count > 0) {
            cursor.close()
            return false // ya existe
        }

        cursor.close()
        val values = ContentValues().apply {
            put("id", id.toInt())
            put("nombre", nombre)
            put("semestre", semestre.toInt())
            put("correo", correo)
            put("contraseña", contraseña)
        }
        val resultado = db.insert("Estudiantes", null, values)
        return resultado != -1L
    }


    fun validarEstudiante(correo: String, contraseña: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM Estudiantes WHERE correo = ? AND contraseña = ?",
            arrayOf(correo, contraseña)
        )
        val valido = cursor.count > 0
        cursor.close()
        return valido
    }

    fun buscarLibros(autor: String, titulo: String): List<Libro> {
        val lista = mutableListOf<Libro>()
        val db = readableDatabase
        var query = "SELECT * FROM Libros WHERE 1=1"
        val args = mutableListOf<String>()

        if (autor.isNotEmpty()) {
            query += " AND autor LIKE ?"
            args.add("%$autor%")
        }
        if (titulo.isNotEmpty()) {
            query += " AND titulo LIKE ?"
            args.add("%$titulo%")
        }

        val cursor = db.rawQuery(query, args.toTypedArray())
        while (cursor.moveToNext()) {
            val libro = Libro(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                autor = cursor.getString(cursor.getColumnIndexOrThrow("autor")),
                estado = cursor.getString(cursor.getColumnIndexOrThrow("estado")),
                imagen = cursor.getInt(cursor.getColumnIndexOrThrow("imagen")),
                paginas = cursor.getColumnIndex("paginas").let { if (it >= 0) cursor.getInt(it) else null },
                idioma = cursor.getColumnIndex("idioma").let { if (it >= 0) cursor.getString(it) else null },
                anio = cursor.getColumnIndex("anio").let { if (it >= 0) cursor.getInt(it) else null },
                sinopsis = cursor.getColumnIndex("sinopsis").let { if (it >= 0) cursor.getString(it) else null }
            )
            lista.add(libro)
        }
        cursor.close()
        return lista
    }


    fun reservarLibro(id: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("estado", "No Disponible")
        }
        db.update("Libros", values, "id = ?", arrayOf(id.toString()))
    }

    fun insertarReservacion(nombre: String, dias: String, ubicacion: String, hora: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("dias", dias)
            put("ubicacion", ubicacion)
            put("hora", hora)
        }
        val resultado = db.insert("Reservaciones", null, values)
        return resultado != -1L
    }

    fun eliminarReservacionPorNombre(nombre: String): Boolean {
        val db = writableDatabase
        val result = db.delete("Reservaciones", "nombre=?", arrayOf(nombre))
        return result > 0
    }


    fun obtenerReservacionPorLibro(nombreLibro: String): Reservacion? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Reservaciones WHERE nombre LIKE ?", arrayOf("%$nombreLibro%"))

        return if (cursor.moveToFirst()) {
            val reservacion = Reservacion(
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                dias = cursor.getString(cursor.getColumnIndexOrThrow("dias")),
                ubicacion = cursor.getString(cursor.getColumnIndexOrThrow("ubicacion")),
                hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"))
            )
            cursor.close()
            reservacion
        } else {
            cursor.close()
            null
        }
    }
}
