package com.example.roomdatabase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var btnListEstudiantes: Button
    lateinit var btnListMaterias: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnListEstudiantes = findViewById(R.id.btn_list_Est)
        btnListMaterias = findViewById(R.id.btn_list_Mat)
        btnListEstudiantes.setOnClickListener(View.OnClickListener { v ->
            val intent = Intent(v.context, HomeActivity::class.java)
            startActivityForResult(intent, 0)
        })
        btnListMaterias.setOnClickListener(View.OnClickListener { v ->
            val intent = Intent(v.context, HomeActivityM::class.java)
            startActivityForResult(intent, 0)
        })
    }
}