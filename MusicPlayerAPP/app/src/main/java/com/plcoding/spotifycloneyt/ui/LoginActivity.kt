package com.plcoding.spotifycloneyt.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.plcoding.spotifycloneyt.MapActivity
import com.plcoding.spotifycloneyt.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val usuarioLog = findViewById<EditText>(R.id.et_correoLog)
        val contraseñaLog = findViewById<EditText>(R.id.et_contraseñaLog)
        val progressBar = findViewById<ProgressBar>(R.id.pb_registrarLog)
        val forgotPass = findViewById<TextView>(R.id.tv_info)
        val singIn = findViewById<TextView>(R.id.tv_registrarse)
        val btnIniciarSesion = findViewById<Button>(R.id.btn_Logearse)
        val ubicacion = findViewById<TextView>(R.id.tv_seleccionar_ubicacion)
        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        forgotPass.setOnClickListener {
            irActividad(ForgotPassActivity::class.java)
        }
        singIn.setOnClickListener{
            irActividad(RegisterActivity::class.java)
        }
        btnIniciarSesion.setOnClickListener {
            if (!TextUtils.isEmpty(usuarioLog.text.toString()) &&
                !TextUtils.isEmpty(contraseñaLog.text.toString())
            ){
                progressBar.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(usuarioLog.text.toString(), contraseñaLog.text.toString())
                    .addOnCompleteListener {
                            task ->
                        if (task.isSuccessful){
                            irActividad(MainActivity::class.java)
                        }else{
                            Toast.makeText(this, "Error al Iniciar Sesión", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }
        ubicacion.setOnClickListener {
            irActividad(MapActivity::class.java)
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