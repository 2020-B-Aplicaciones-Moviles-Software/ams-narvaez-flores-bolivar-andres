package com.example.firebasemanual


import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.firebasemanual.dto.FirebaseRestauranteDto
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class COrdenes : AppCompatActivity() {
    val arregloRestaurantes = arrayListOf<FirebaseRestauranteDto>()
    var adaptadorRestaurantes: ArrayAdapter<FirebaseRestauranteDto>? = null
    var restauranteSeleccionado: FirebaseRestauranteDto? = null
    val arregloTiposComida = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_ordenes)

        if (adaptadorRestaurantes == null){
            adaptadorRestaurantes = ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                arregloRestaurantes
            )
            adaptadorRestaurantes?.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )
            cargarRestaurantes()
        }
        val textViewTipoComida = findViewById<TextView>(R.id.tv_tipos_comida)
        textViewTipoComida.setText("")

        val botonAñadirTipoComida = findViewById<Button>(R.id.btn_añadir_tipo_comida)
        botonAñadirTipoComida
            .setOnClickListener {
                agregarTipoComida()
            }

        val botonCrearOrden = findViewById<Button>(R.id.btn_crear_orden)
        botonCrearOrden
            .setOnClickListener {
                crearOrden()
            }
        buscarOrdenes()
        //crearDatosParaBusquedasPorGrupoColleccion()
        //buscarXd()
        leer()
        val etEliminarId = findViewById<EditText>(R.id.et_eliminar_id)

        val botonEliminar = findViewById<Button>(R.id.btn_eliminar)
        botonEliminar
                .setOnClickListener {
                    var delId = etEliminarId.getText().toString()
                    eliminarDocumentoMedianteConsulta(delId)
                }
    }

    fun cargarRestaurantes(){
        //codigo para llamar a los nombres de restaurante el un spinner
        val spinnerRestaurantes = findViewById<Spinner>(R.id.sp_restaurantes)
        spinnerRestaurantes.adapter = adaptadorRestaurantes
        spinnerRestaurantes
            .onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("firebase-firestore", "No selecciono nada")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                restauranteSeleccionado = arregloRestaurantes[position]
            }
        }


        val db = Firebase.firestore
        val referencia = db.collection("restaurante")
        referencia.get()
            .addOnSuccessListener {
                for (restaurante in it){
                    val restauranteCasteado = restaurante.toObject(
                        FirebaseRestauranteDto::class.java
                    )
                    restauranteCasteado.uid = restaurante.id
                    arregloRestaurantes.add(restauranteCasteado)
                }
                adaptadorRestaurantes?.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Log.i("firebase-firestore", "Error ${it}")
            }
    }

    fun agregarTipoComida(){
        val etTipoComida = findViewById<EditText>(R.id.et_tipo_comida)
        val textoTipoComida = etTipoComida.text.toString()

        arregloTiposComida.add(textoTipoComida)

        val textViewTipoComida = findViewById<TextView>(R.id.tv_tipos_comida)
        val textoAnterior = textViewTipoComida.text.toString()

        textViewTipoComida.setText("${textoAnterior}, ${textoTipoComida}")
        etTipoComida.setText("")
    }

    fun crearOrden(){
        if(restauranteSeleccionado != null && FirebaseAuth.getInstance() != null){
            val restaurante = restauranteSeleccionado
            val usuario = BAuthUsuario.usuario
            val editTextReview = findViewById<EditText>(R.id.et_review)

            val nuevaOrden = hashMapOf<String, Any?>(
                "restaurante" to restaurante,
                "usuario" to usuario,
                "review" to editTextReview.text.toString().toInt(),
                "tiposComida" to arregloTiposComida,
                "fechaCreacion" to Timestamp(Date())
            )

            val db = Firebase.firestore
            val referencia = db.collection("orden")
                .document()
            referencia
                .set(nuevaOrden)
                .addOnSuccessListener {  }
                .addOnFailureListener {  }
        }
    }

    fun buscarOrdenes() {
        val db = Firebase.firestore
        val referencia = db.collection("orden")
        //buscar por un solo campo ==
        /*referencia
                .whereEqualTo("review", 2)
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "Error")
                }
        //Buscar por dos campos ==
        referencia
                .whereEqualTo("review", 3)
                .whereEqualTo("restaurante.nombre", "Onde Pepe")
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "Error")
                }
        //Buscar por dos o mas elementos campo '==' 'array-contains'
        referencia
                .whereEqualTo("restaurante.nombre", "Onde Pepe")
                .whereArrayContains("tiposComida", "Vegetariana")
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "Error")
                }
        //buscar por dos o mas elementos campo '==' '>='
        referencia
                .whereEqualTo("restaurante.nombre", "Onde Pepe")
                .whereGreaterThanOrEqualTo("review", 3)
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "Error")
                }
        referencia
                .whereEqualTo("restaurante.nombre", "Onde Pepe")
                .whereGreaterThanOrEqualTo("review", 3)
                .orderBy("review", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "Error")
                }
        referencia
                .whereEqualTo("restaurante.nombre", "Onde Pepe")
                .whereArrayContainsAny("tiposComida", arrayListOf("Vegetariana", "Fast Food"))
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "Error")
                }
        //WHERE IN
        referencia
                .whereIn("restaurante.nombre", arrayListOf("Onde Pepe", "McDonalds"))
                .whereGreaterThanOrEqualTo("review", 3)
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "Error")
                }*/
        referencia
                .limit(2)
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                    val ultimoRegistro: QueryDocumentSnapshot = it.last()
                    //siguientes dos ordenes
                    referencia
                            .limit(2)
                            .startAfter(ultimoRegistro)
                            .get()
                            .addOnSuccessListener {
                                for (orden in it) {
                                    Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                                }
                            }
                            .addOnFailureListener {
                                Log.i("firebase-firestore", "Error")
                            }
                }
    }

    fun crearDatosParaBusquedasPorGrupoColleccion() {

        val db = Firebase.firestore
        val cities = db.collection("cities")
        val ggbData = mapOf(
                "name" to "Golden Gate Bridge",
                "type" to "bridge"
        )
        cities.document("SF").collection("landmarks").add(ggbData)

        val lohData = mapOf(
                "name" to "Legion of Honor",
                "type" to "museum"
        )
        cities.document("SF").collection("landmarks").add(lohData)

        val gpData = mapOf(
                "name" to "Griffth Park",
                "type" to "park"
        )
        cities.document("LA").collection("landmarks").add(gpData)

        val tgData = mapOf(
                "name" to "The Getty",
                "type" to "museum"
        )
        cities.document("LA").collection("landmarks").add(tgData)

        val lmData = mapOf(
                "name" to "Lincoln Memorial",
                "type" to "memorial"
        )
        cities.document("DC").collection("landmarks").add(lmData)

        val nasaData = mapOf(
                "name" to "National Air and Space Museum",
                "type" to "museum"
        )
        cities.document("DC").collection("landmarks").add(nasaData)

        val upData = mapOf(
                "name" to "Ueno Park",
                "type" to "park"
        )
        cities.document("TOK").collection("landmarks").add(upData)

        val nmData = mapOf(
                "name" to "National Musuem of Nature and Science",
                "type" to "museum"
        )
        cities.document("TOK").collection("landmarks").add(nmData)

        val jpData = mapOf(
                "name" to "Jingshan Park",
                "type" to "park"
        )
        cities.document("BJ").collection("landmarks").add(jpData)

        val baoData = mapOf(
                "name" to "Beijing Ancient Observatory",
                "type" to "musuem"
        )
        cities.document("BJ").collection("landmarks").add(baoData)
        val data1 = hashMapOf("name" to "San Francisco", "state" to "CA", "country" to "USA", "capital" to false, "population" to 860000, "regions" to listOf("west_coast", "norcal"))
        cities.document("SF").set(data1)
        val data2 = hashMapOf("name" to "Los Angeles", "state" to "CA", "country" to "USA", "capital" to false, "population" to 3900000, "regions" to listOf("west_coast", "socal"))
        cities.document("LA").set(data2)
        val data3 = hashMapOf("name" to "Washington D.C.", "state" to null, "country" to "USA", "capital" to true, "population" to 680000, "regions" to listOf("east_coast"))
        cities.document("DC").set(data3)
        val data4 = hashMapOf("name" to "Tokyo", "state" to null, "country" to "Japan", "capital" to true, "population" to 9000000, "regions" to listOf("kanto", "honshu"))
        cities.document("TOK").set(data4)
        val data5 = hashMapOf("name" to "Beijing", "state" to null, "country" to "China", "capital" to true, "population" to 21500000, "regions" to listOf("jingjinji", "hebei"))
        cities.document("BJ").set(data5)
    }

    fun buscarXd(){
        val db = Firebase.firestore
        //val referencia = db.collectionGroup("landmarks")

        val referencia = db.collection("cities").document("BJ")

        referencia.collection("landmarks").whereEqualTo("type", "park").get()
                .addOnSuccessListener {
                    for (landmark in it){
                        Log.i("firebase-consultas","${landmark.id} ${landmark.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore","Error")
                }

        /*referencia
                .whereEqualTo("type", "park")
                .get()
                .addOnSuccessListener {
                    for (landmark in it){
                        Log.i("firebase-consultas", "${landmark.id} ${landmark.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "Error")
                }*/
    }
    fun eliminacion(){
        val db = Firebase.firestore
        val docRef = db
                .collection("cities")
                .document("BJ")
                .collection("landmarks")
                .document("9V1nMpN797ltUdwdH5Tv")

//        val eliminarCampo = hashMapOf<String, Any>(
//                "name" to FieldValue.delete()
//        )
//        docRef
//                .update(eliminarCampo)
//                .addOnSuccessListener {
//                    Log.i("firebase-delete","${it}")
//                }
//                .addOnFailureListener {
//                    Log.i("firebase-delete","Error eliminando campo ${it}")
//                }

        docRef
                .delete()
                .addOnSuccessListener {
                    Log.i("firebase-delete","${it}")
                }
                .addOnFailureListener {
                    Log.i("firebase-delete","Error eliminando documento ${it}")
                }
    }

    fun leer(){
        val db = Firebase.firestore
        val referencia = db.collectionGroup("landmarks")
        val arregloDocs: ArrayList<DocumentReference>? = null
        referencia
                .whereEqualTo("type","museum")
                .get()
                .addOnSuccessListener {
                    for (landmarks in it){
                        Log.i("firebase-taller","${landmarks.id} ${landmarks.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firebase","Error ${it}")
                }
    }

    fun eliminarDocumentoMedianteConsulta(delId: String) {
        val db = Firebase.firestore
        val referencia = db.collectionGroup("landmarks")
        val arregloDocs = ArrayList<DocumentReference>()
        referencia
                .whereEqualTo("type","museum")
                .get()
                .addOnSuccessListener {
                    for (landmarks in it){
                        arregloDocs.add(landmarks.reference)
                    }
                    if (!delId.isEmpty()) {
                        for (arregloDocs in it) {
                            if (delId.equals(arregloDocs.id)){
                                arregloDocs.reference.delete()
                                Log.i("firebase-taller", "${arregloDocs.id} eliminado")
                                Toast.makeText(applicationContext, "${arregloDocs.id} eliminado", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(applicationContext, "Ingrese un id", Toast.LENGTH_SHORT).show()
                        Log.i("firebase-taller", "Ingrese un id")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firebase","Error ${it}")
                }
    }
}