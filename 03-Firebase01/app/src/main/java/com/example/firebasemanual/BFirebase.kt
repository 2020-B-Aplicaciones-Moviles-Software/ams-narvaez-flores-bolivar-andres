package com.example.firebasemanual

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button

class BFirebase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_firebase)

        val botonProducto = findViewById<Button>(R.id.btn_producto)
        botonProducto
            .setOnClickListener {
                irActividad(CProducto::class.java)
            }

        val botonRestaurante = findViewById<Button>(R.id.btn_restaurante)
        botonRestaurante
            .setOnClickListener {
                irActividad(CRestaurante::class.java)
            }

        val botonOrdenes = findViewById<Button>(R.id.btn_ir_ordenes)
        botonOrdenes
            .setOnClickListener {
                irActividad(COrdenes::class.java)
            }
        val botonMostrar = findViewById<Button>(R.id.btn_mostrar)
        botonMostrar
            .setOnClickListener {
                irActividad(DMostrarOrdenes::class.java)
            }
    }

    fun irActividad( clase: Class<*>, parametros: ArrayList<Pair<String, *>>? = null, codigo: Int? = null ) {
        val intentExplicito = Intent( this, clase )
        if (parametros != null) {
            parametros.forEach {
                val nombreVariable = it.first
                val valorVariable = it.second
                var tipoDato = false
                tipoDato = it.second is String // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as String)
                }
                tipoDato = it.second is Int // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as Int)
                }
                tipoDato = it.second is Parcelable // instanceOf()
                if (tipoDato == true) {
                    intentExplicito.putExtra(nombreVariable, valorVariable as Parcelable)
                }
            }
        }
        if (codigo != null) {
            startActivityForResult(intentExplicito, codigo)
        } else {
            startActivity(intentExplicito)
        }
    }

}