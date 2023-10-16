package com.example.actividad2crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.actividad2crud.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), AdaptadorListener{

    lateinit var binding : ActivityMainBinding

    var listaContactos: MutableList<Contacto> = mutableListOf()

    lateinit var adaptador: AdaptadorContactos

    lateinit var room: Database

    lateinit var contacto: Contacto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvContactos.layoutManager = LinearLayoutManager(this)

        room = Room.databaseBuilder(this, Database::class.java, name="dbContactos").build()

        obtenerContactos(room)

        binding.btnAddUpdate.setOnClickListener{
            if(binding.etNumero.text.isNullOrEmpty() || binding.etNombre.text.isNullOrEmpty()){
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(binding.btnAddUpdate.text.equals("agregar")){
                contacto = Contacto(
                    binding.etNumero.text.toString().trim(),
                    binding.etNombre.text.toString().trim()
                )

                agregarContacto(room, contacto)

            }else if(binding.btnAddUpdate.text.equals("actualizar")){

                contacto.nombre = binding.etNombre.text.toString().trim()

                actualizarContacto(room, contacto)
            }

        }
    }

    fun obtenerContactos(room: Database){
        lifecycleScope.launch {

            listaContactos = room.daoContacto().obtenerContactos()
            adaptador = AdaptadorContactos(listaContactos, this@MainActivity)
            binding.rvContactos.adapter = adaptador
        }
    }

    fun agregarContacto(room: Database, contacto: Contacto){
        lifecycleScope.launch {
            room.daoContacto().agregarContacto(contacto)
            obtenerContactos(room)
            limpiarCampos()
        }
    }

    fun actualizarContacto(room: Database, contacto: Contacto){
        lifecycleScope.launch {
            room.daoContacto().actualizarContacto(contacto.nombre, contacto.numero)
            obtenerContactos(room)
            limpiarCampos()
        }
    }
    fun limpiarCampos(){
        contacto.nombre = ""
        contacto.numero = ""
        binding.etNombre.setText("")
        binding.etNumero.setText("")

        if(binding.btnAddUpdate.text.equals("actualizar")){
            binding.btnAddUpdate.setText("agregar")
            binding.etNumero.isEnabled = true
        }
    }

    override fun onEditItemClick(contacto: Contacto) {
        binding.btnAddUpdate.setText("actualizar")
        binding.etNumero.isEnabled = false
        this.contacto = contacto
        binding.etNumero.setText(this.contacto.numero)
        binding.etNombre.setText(this.contacto.nombre)

    }

    override fun onDeleteItemClick(contacto: Contacto) {
        lifecycleScope.launch {
            room.daoContacto().borrarContacto(contacto.numero)
            adaptador.notifyDataSetChanged()
            obtenerContactos(room)
        }
    }
}