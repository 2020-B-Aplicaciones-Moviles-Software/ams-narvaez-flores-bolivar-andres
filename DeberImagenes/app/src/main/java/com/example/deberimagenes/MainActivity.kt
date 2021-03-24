package com.example.deberimagenes

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

abstract class MainActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 2
    val SELECT_FILE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonSubir = findViewById<Button>(R.id.btnSeleccionarFoto)

        botonSubir
            .setOnClickListener {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.type = "image/*"
                startActivityForResult(Intent.createChooser(intent,"Seleccione una imagen"), SELECT_FILE)
            }

        val botonTomar = findViewById<Button>(R.id.btnTomarFoto)

        botonTomar
            .setOnClickListener {
                intentTomarFoto()
            }

        val botonSubirFirestore = findViewById<Button>(R.id.btn_firestore)

        botonSubirFirestore
            .setOnClickListener {
                subirFirestore()
            }

        val botonDescargar = findViewById<Button>(R.id.btn_descargar)

        botonDescargar
            .setOnClickListener {
                descargar()
            }
    }

    private fun intentTomarFoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    fun subirFirestore(){
        val imagen = findViewById<ImageView>(R.id.iv_foto)
        val storage = Firebase.storage
        val storageRef = storage.reference
        val pruebaRef = storageRef.child("Imagenes/test.jpg")

        imagen.isDrawingCacheEnabled = true
        imagen.buildDrawingCache()
        val bitmap = (imagen.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,baos)
        val data = baos.toByteArray()

        val uploadTask = pruebaRef.putBytes(data)
        uploadTask
            .addOnFailureListener{
                Log.i("firebase-firestore","Error ${it}")
            }
            .addOnSuccessListener {
                Log.i("firebase-firestore","Subido ${it}")
            }

    }

    fun descargar(){
        val imagen = findViewById<ImageView>(R.id.iv_foto)
        val texto = findViewById<EditText>(R.id.ev_descargar)
        val storage = Firebase.storage
        val storageRef = storage.reference
        val pruebaRef = storageRef.child("Imagenes/${texto.text}.jpg")

        val ONE_MEGABYTE: Long = 1024 * 1024
        pruebaRef.getBytes(ONE_MEGABYTE)
            .addOnFailureListener{
                Log.i("firebase-firestore","Error ${it}")
            }
            .addOnSuccessListener {
                val bmp = BitmapFactory.decodeByteArray(it,0,it.size)
                imagen.setImageBitmap(bmp)
                Log.i("firebase-firestore","Subido ${it}")
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imagen = findViewById<ImageView>(R.id.iv_foto)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val img = data?.extras?.get("data") as Bitmap
            imagen.setImageBitmap(img)
        }else if (requestCode == SELECT_FILE && resultCode == RESULT_OK){
            imagen.setImageURI(data?.data)
        }

    }
}