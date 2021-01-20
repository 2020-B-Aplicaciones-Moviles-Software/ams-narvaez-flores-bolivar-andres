package com.example.roomdatabase

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivityM : AppCompatActivity(), MateriasAdapter.ItemClicked {
    var estudiantes = Estudiantes()
    lateinit var viewModelM: ViewModelM
    lateinit var materiasAdapter: MateriasAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var btnNewMat: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_m)
        viewModelM = ViewModelProviders.of(this).get(ViewModelM::class.java)
        materiasAdapter = MateriasAdapter(this)
        recyclerView = findViewById(R.id.recyclerView1)
        btnNewMat = findViewById(R.id.btnNewMats)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        viewModelM!!.allMats?.observe(this, Observer<List<Materias?>> { mats ->
            if (mats.size > 0) {
                materiasAdapter!!.setData(mats as List<Materias>?)
                recyclerView.setAdapter(materiasAdapter)
            }
        })
        btnNewMat.setOnClickListener(View.OnClickListener { addMats(this@HomeActivityM) })
    }

    fun addMats(context: Context?) {
        val builder = AlertDialog.Builder(this)
        val view1 = layoutInflater.inflate(R.layout.row_add_mats, null)
        val addMat = view1.findViewById<Button>(R.id.btnAddMat)
        addMat.setOnClickListener {
            val edMats = view1.findViewById<EditText>(R.id.edMat)
            val edCodigo = view1.findViewById<EditText>(R.id.edCodigo)
            val edHoras = view1.findViewById<EditText>(R.id.edHoras)
            val edProfesor = view1.findViewById<EditText>(R.id.edProfesor)
            val materias = Materias()
            materias.matname = edMats.text.toString()
            materias.codigo = edCodigo.text.toString()
            materias.horas = edHoras.text.toString().toInt()
            materias.profesor = edProfesor.text.toString()
            viewModelM!!.insertMats(materias)
        }
        builder.setView(view1)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun detailsMats(materias: Materias?) {
        val builder = AlertDialog.Builder(this)
        val view1 = layoutInflater.inflate(R.layout.detalles_materias, null)
        val nomMat = view1.findViewById<TextView>(R.id.nomMatV)
        val codigo = view1.findViewById<TextView>(R.id.codigoV)
        val horas = view1.findViewById<TextView>(R.id.horasV)
        val profesor = view1.findViewById<TextView>(R.id.profeV)
        val alumnos = view1.findViewById<TextView>(R.id.alumnosV)
        nomMat.text = materias!!.matname
        codigo.text = materias.codigo
        horas.setText(materias.horas.toString())
        profesor.text = materias.profesor
        if (materias.idM == estudiantes.idMat) {
            alumnos.text = estudiantes.username
        }
        builder.setView(view1)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun updateMats(materias: Materias?) {
        val builder = AlertDialog.Builder(this)
        val view1 = layoutInflater.inflate(R.layout.row_add_mats, null)
        val edMats = view1.findViewById<EditText>(R.id.edMat)
        val edCodigo = view1.findViewById<EditText>(R.id.edCodigo)
        val edHoras = view1.findViewById<EditText>(R.id.edHoras)
        val edProfesor = view1.findViewById<EditText>(R.id.edProfesor)
        edMats.setText(materias!!.matname)
        edCodigo.setText(materias.codigo)
        edHoras.setText(materias.horas.toString())
        edProfesor.setText(materias.profesor)
        val addMat = view1.findViewById<Button>(R.id.btnAddMat)
        addMat.text = "Actualizar"
        addMat.setOnClickListener {
            materias.matname = edMats.text.toString()
            materias.codigo = edCodigo.text.toString()
            materias.horas = edHoras.text.toString().toInt()
            materias.profesor = edProfesor.text.toString()
            viewModelM!!.updateMats(materias)
        }
        builder.setView(view1)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun updateClicked(materias: Materias?) {
        updateMats(materias)
    }

    override fun deleteClicked(materias: Materias?) {
        viewModelM!!.deleteMats(materias)
    }

    override fun detailsClicked(materias: Materias?) {
        val estudiantes: Estudiantes? = null
        detailsMats(materias)
    }
}

private fun <T> LiveData<T>?.observe(homeActivityM: HomeActivityM, observer: Observer<List<Materias?>>) {

}


