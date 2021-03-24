package com.example.firebasemanual

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.firebasemanual.dto.FirestoreUsuarioDto

class MainActivity : AppCompatActivity() {
    val CODIGO_INGRESO = 102
    val mensajeNoLogeado = "Dale click al boton Ingresar"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val botonIngresar = findViewById<Button>(R.id.btn_ingresar)
        botonIngresar
            .setOnClickListener {
                pedidoIngresar()
            }

        val botonSalir = findViewById<Button>(R.id.btn_salir)
        botonSalir
            .setOnClickListener {
                pedidoSalir()
            }
        val texto = findViewById<TextView>(R.id.textView)

        val instanciaAuth = FirebaseAuth.getInstance()
        if(instanciaAuth.currentUser != null){
            texto.text = "Bienvenido ${instanciaAuth.currentUser?.email}"
            setearUsuarioFirebase()
        }else{
            texto.text = mensajeNoLogeado
        }
        mostrarBotonesOcultos()
        val botonFirestone = findViewById<Button>(R.id.btn_ir_firestone)
        botonFirestone
                .setOnClickListener {
                    irActividad(BFirebase::class.java)
                }

        val botonMapa = findViewById<Button>(R.id.btn_ir_mapa)
        botonMapa
            .setOnClickListener {
                irActividad(MapsActivity::class.java)
            }


        val botonFragmento = findViewById<Button>(R.id.btn_ir_fragmento)
        botonFragmento
            .setOnClickListener {
                irActividad(EFragmentos::class.java)
            }

    }
    fun pedidoIngresar(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.poli)
                .setTosAndPrivacyPolicyUrls(
                    "http://example.com/terms.html",
                    "http://example.com/privacy.html"
                )
                .build(),
            CODIGO_INGRESO

        )
    }

    fun pedidoSalir(){
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                Log.i("firebase-firestone", "Salio")
                val texto = findViewById<TextView>(R.id.textView)
                texto.text = mensajeNoLogeado
                BAuthUsuario.usuario = null
                mostrarBotonesOcultos()
            }
            .addOnFailureListener {
                Log.i("firebase-firestone", "Hubo problemas en salir")
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            CODIGO_INGRESO -> {
                if(resultCode == Activity.RESULT_OK){
                    val usuario = IdpResponse.fromResultIntent(data)
                    if(usuario?.isNewUser == true){
                        //logica para crear el usuario en nuestra colección
                        if(usuario.email != null){
                            val rolesUsuario = arrayListOf("usuario")
                            val nuevoUsuario = hashMapOf<String, Any>(
                                    "roles" to rolesUsuario
                            )
                            val db = Firebase.firestore
                            val referencia = db.collection("usuario")
                                    .document(usuario?.email.toString())
                            referencia
                                    .set(nuevoUsuario)
                                    .addOnSuccessListener {
                                        setearUsuarioFirebase()
                                        mostrarBotonesOcultos()
                                        Log.i("firebase-firestone", "Se creo")
                                    }
                                    .addOnFailureListener {
                                        Log.i("firebase-firestone", "Fallo")
                                    }
                        }
                    }
                    val texto = findViewById<TextView>(R.id.textView)
                    texto.text = "Bienvenido ${usuario?.email}"
                    setearUsuarioFirebase()
                    mostrarBotonesOcultos()
                }else{
                    Log.i("firebase-login", "El usuario cancelo")
                }
            }
        }
    }

    fun setearUsuarioFirebase(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuariolocal = instanciaAuth.currentUser

        if (usuariolocal != null){
            if (usuariolocal.email != null){
                val usuarioFirebase = BUsuarioFirebase(
                        usuariolocal.uid,
                        usuariolocal.email!!,
                        null
                )
                BAuthUsuario.usuario = usuarioFirebase
                cargarRolesUsuario(usuarioFirebase.email)
            }
        }
    }

    fun mostrarBotonesOcultos(){
        val botonEscondidoFirestone = findViewById<Button>(R.id.btn_ir_firestone)
        if(BAuthUsuario.usuario != null){
            botonEscondidoFirestone.visibility = View.VISIBLE
        }else{
            botonEscondidoFirestone.visibility = View.INVISIBLE
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

    fun cargarRolesUsuario (uid: String){
        val db = Firebase.firestore

        val referencia = db
            .collection("usuario")
            .document(uid)
        Log.i("firebase-firestore","${uid}")
        referencia
            .get()
            .addOnSuccessListener {
                Log.i("firebase-firestore", "Datos ${it.data}")
                val firestoreUsuario = it.toObject(FirestoreUsuarioDto::class.java)
                BAuthUsuario.usuario?.roles = firestoreUsuario?.roles
                mostrarRolesEnPantalla()
            }
            .addOnFailureListener {
                Log.i("firebase-firestore", "Fallo cargar usuario")
            }
    }

    fun mostrarRolesEnPantalla(){
        var cadenaTextRoles = ""
        BAuthUsuario.usuario?.roles?.forEach {
            cadenaTextRoles = cadenaTextRoles + " " + it
        }
        val textoRoles = findViewById<TextView>(R.id.tv_roles)
        textoRoles.text = cadenaTextRoles
    }
}