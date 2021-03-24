package com.example.firebasemanual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.firebasemanual.dto.FirestoreOrdenesDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DMostrarOrdenes : AppCompatActivity() {

    //var arregloOrdenes:ArrayList<String> = arrayListOf()

    val db = Firebase.firestore
    val referencia = db.collection("orden")
    var arregloOrdenes:ArrayList<FirestoreOrdenesDto> = arrayListOf()
    var fechaCreacion: com.google.firebase.Timestamp? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_mostrar_ordenes)

        val botonMostrarOrden = findViewById<Button>(R.id.btn_mostrarOrdenes)
        botonMostrarOrden
            .setOnClickListener {
                mostrarOrden()
            }

    }



    fun mostrarOrden(){

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloOrdenes
        )

        referencia
            .orderBy("fechaCreacion")
            .limit(2)
            .startAfter(fechaCreacion)
            .get()
            .addOnSuccessListener {
                for (orden in it) {
                    val ordenes = orden.toObject(FirestoreOrdenesDto::class.java)
                    ordenes.uid = orden.id
                    arregloOrdenes.add(ordenes)
                    Log.i("firebase-consultas" ,"boton ${orden.data}")
                    fechaCreacion = ordenes.fechaCreacion
                }
            }
            .addOnFailureListener{
                Log.i("firebase-consultas" ,"Error")
            }

        val listViewOredenes= findViewById<ListView>(R.id.lv_mostrarOrdenes)
        listViewOredenes.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listViewOredenes)
    }
}