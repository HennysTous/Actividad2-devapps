package com.example.actividad2crud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorContactos (
    val listaContactos:MutableList<Contacto>,
    val listener: AdaptadorListener
): RecyclerView.Adapter<AdaptadorContactos.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_contacto, parent, false)
    return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return listaContactos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var contacto = listaContactos[position]

        holder.tvNombre.text = contacto.nombre
        holder.tvNumero.text = contacto.numero

        holder.cvContactos.setOnClickListener{
            listener.onEditItemClick(contacto)
        }

        holder.btnBorrar.setOnClickListener{
            listener.onDeleteItemClick(contacto)
        }
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val cvContactos = itemView.findViewById<CardView>(R.id.cvContacto)
        val tvNombre = itemView.findViewById<TextView>(R.id.tvNombre)
        val tvNumero = itemView.findViewById<TextView>(R.id.tvNumero)
        val btnBorrar = itemView.findViewById<Button>(R.id.btnBorrar)
    }
}