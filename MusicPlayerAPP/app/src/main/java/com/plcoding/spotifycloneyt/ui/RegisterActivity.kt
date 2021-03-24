package com.plcoding.spotifycloneyt.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.plcoding.spotifycloneyt.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val nombreRegistro = findViewById<EditText>(R.id.et_nombre)
        val apellidoRegistro = findViewById<EditText>(R.id.et_apellido)
        val emailRegistro = findViewById<EditText>(R.id.et_correo)
        val passRegistro = findViewById<EditText>(R.id.et_contraseña)
        val progressBar = findViewById<ProgressBar>(R.id.pb_registrar)
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val dbReference: DatabaseReference = database.reference.child("User")

        val btnRegistrar = findViewById<Button>(R.id.btn_registrarse)
        btnRegistrar.setOnClickListener {
            if(!TextUtils.isEmpty(nombreRegistro.text.toString()) &&
                !TextUtils.isEmpty(apellidoRegistro.text.toString()) &&
                !TextUtils.isEmpty(emailRegistro.text.toString()) &&
                !TextUtils.isEmpty(passRegistro.text.toString())
            ){
                progressBar.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(emailRegistro.text.toString(), passRegistro.text.toString())
                    .addOnCompleteListener (this){
                            task ->
                        if (task.isComplete){
                            val usuario: FirebaseUser? = auth.currentUser
                            verificarEmail(usuario)
                            val usuarioDB = dbReference.child(usuario?.uid!!)
                            usuarioDB.child("Nombre").setValue(nombreRegistro.text.toString())
                            usuarioDB.child("Apellido").setValue(apellidoRegistro.text.toString())
                            irActividad(LoginActivity::class.java)
                        }
                    }
            }

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

    private fun verificarEmail(usuario:FirebaseUser?){
        usuario?.sendEmailVerification()
            ?.addOnCompleteListener {
                    task ->
                if (task.isComplete){
                    Toast.makeText(this, "Email enviado", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Error al enviar el email", Toast.LENGTH_SHORT).show()
                }
            }
    }
}