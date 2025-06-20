package com.example.proyecto_bibloteca.data
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, "BibliotecaDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE Estudiantes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                correo TEXT NOT NULL,
                contraseña TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createTable)

        // Insertar un estudiante de prueba
        val insert = ContentValues().apply {
            put("correo", "alumno@alumnos.uaa.mx")
            put("contraseña", "1234")
        }
        db.insert("Estudiantes", null, insert)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Estudiantes")
        onCreate(db)
    }

    fun insertarEstudiante(correo: String, contraseña: String): Boolean {
        val db = writableDatabase

        // Verificar si ya existe
        val cursor = db.rawQuery("SELECT * FROM Estudiantes WHERE correo = ?", arrayOf(correo))
        if (cursor.count > 0) {
            cursor.close()
            return false // ya existe
        }

        cursor.close()
        val values = ContentValues().apply {
            put("correo", correo)
            put("contraseña", contraseña)
        }
        val resultado = db.insert("Estudiantes", null, values)
        return resultado != -1L
    }


    // Validar credenciales
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
}