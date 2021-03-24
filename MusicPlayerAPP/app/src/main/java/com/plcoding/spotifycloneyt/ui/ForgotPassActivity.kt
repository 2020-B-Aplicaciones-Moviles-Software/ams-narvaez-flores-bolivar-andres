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
import com.plcoding.spotifycloneyt.R

class ForgotPassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)

        val emailRegistro = findViewById<EditText>(R.id.et_emailRec)
        val btnEnviarContraseña = findViewById<Button>(R.id.btn_enviar)
        val progressBar = findViewById<ProgressBar>(R.id.pb_forgotPass)

        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        btnEnviarContraseña
            .setOnClickListener {
                if(!TextUtils.isEmpty(emailRegistro.text.toString())){
                    auth.sendPasswordResetEmail(emailRegistro.text.toString())
                        .addOnCompleteListener {
                                task ->
                            if (task.isSuccessful){
                                progressBar.visibility = View.VISIBLE
                                irActividad(LoginActivity::class.java)
                            }else{
                                Toast.makeText(this, "Error al Enviar el Email", Toast.LENGTH_SHORT).show()
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
}