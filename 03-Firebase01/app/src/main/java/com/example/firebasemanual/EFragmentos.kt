package com.example.firebasemanual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment

class EFragmentos : AppCompatActivity() {

    lateinit var fragmentoActual: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_e_fragmentos)

        val botonPrimer = findViewById<Button>(R.id.btn_primer_fragmento)
        botonPrimer
            .setOnClickListener {
                crearFragmentoUno()
            }

        val botonSegundo = findViewById<Button>(R.id.btn_segundo_fragmento)
        botonSegundo
            .setOnClickListener {
                crearFragmentoDos()
            }

    }

    fun crearFragmentoUno(){
        //Manger
        val fragmentManager = supportFragmentManager
        //Transacciones
        val fragmentTransaction = fragmentManager.beginTransaction()
        //Crear instancia de fragmento
        val primerFragmento = PrimerFragment()
        //ARGUMENTOS
        val argumentos = Bundle()
        argumentos.putString("nombre", "Andres Narvaez")
        argumentos.putInt("edad", 23)
        primerFragmento.arguments = argumentos

        //Añadir Fragmento
        fragmentTransaction.replace(R.id.rl_fragmento, primerFragmento)
        fragmentoActual = primerFragmento
        fragmentTransaction.commit()
    }

    fun crearFragmentoDos(){
        //Manger
        val fragmentManager = supportFragmentManager
        //Transacciones
        val fragmentTransaction = fragmentManager.beginTransaction()
        //Crear instancia de fragmento
        val segundoFragmento = SegundoFragment()
        //ARGUMENTOS
        val argumentos = Bundle()
        argumentos.putString("nombre", "Andres Narvaez")
        argumentos.putInt("edad", 23)
        segundoFragmento.arguments = argumentos

        //Añadir Fragmento
        fragmentTransaction.replace(R.id.rl_fragmento, segundoFragmento)
        fragmentoActual = segundoFragmento
        fragmentTransaction.commit()
    }
}