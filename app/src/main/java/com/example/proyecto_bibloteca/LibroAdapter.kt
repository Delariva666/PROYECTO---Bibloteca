package com.example.proyecto_bibloteca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.util.Log
import com.example.proyecto_bibloteca.Libro
import com.example.proyecto_bibloteca.data.SQLiteHelper

class LibroAdapter(
    private val libros: List<Libro>,
    private val reservarCallback: (Libro) -> Unit,
    private val detallesCallback: (Libro) -> Unit
) : RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    inner class LibroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagen: ImageView = view.findViewById(R.id.libroImagen)
        val titulo: TextView = view.findViewById(R.id.libroTitulo)
        val estado: TextView = view.findViewById(R.id.libroEstado)
        val reservar: Button = view.findViewById(R.id.btnReservar)
        val detalles: Button = view.findViewById(R.id.btnDetalles)
        val info: Button = view.findViewById(R.id.btnInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_libro, parent, false)
        return LibroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libros[position]
        holder.imagen.setImageResource(libro.imagen)
        holder.titulo.text = libro.titulo
        holder.estado.text = "Estado: ${libro.estado}"

        holder.reservar.isEnabled = libro.estado == "Disponible"
        holder.reservar.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ReservacionActivity::class.java)
            intent.putExtra("titulo", libro.titulo)
            intent.putExtra("autor", libro.autor)
            intent.putExtra("idLibro", libro.id)
            context.startActivity(intent)
        }

        holder.info.setOnClickListener {
            val context = holder.itemView.context
            val dbHelper = SQLiteHelper(context)
            val reservacion = dbHelper.obtenerReservacionPorLibro(libro.titulo)

            if (reservacion != null) {
                val intent = Intent(context, ReservacionInfoActivity::class.java).apply {
                    putExtra("nombre", reservacion.nombre)
                    putExtra("dias", reservacion.dias)
                    putExtra("ubicacion", reservacion.ubicacion)
                    putExtra("hora", reservacion.hora)
                    putExtra("idLibro", libro.id)
                }
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "No hay reservación para este libro", Toast.LENGTH_SHORT).show()
            }
        }

        holder.detalles.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, LibroDetalleActivity::class.java).apply {
                putExtra("titulo", libro.titulo)
                putExtra("idLibro", libro.id)
                putExtra("autor", libro.autor)
                putExtra("estado", libro.estado)
                putExtra("imagen", libro.imagen)
                putExtra("paginas", libro.paginas ?: 0)
                putExtra("idioma", libro.idioma ?: "No disponible")
                putExtra("anio", libro.anio ?: 0)
                putExtra("sinopsis", libro.sinopsis ?: "Sin sinopsis")
            }
            context.startActivity(intent)
            Log.d("LibroAdapter", "Reservando libro: ${libro.titulo} con ID: ${libro.id}")
        }


    }

    override fun getItemCount(): Int = libros.size
}
