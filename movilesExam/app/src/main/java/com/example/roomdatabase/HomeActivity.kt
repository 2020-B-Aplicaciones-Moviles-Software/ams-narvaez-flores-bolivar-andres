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

class HomeActivity : AppCompatActivity(), EstudiantesAdapter.ItemClicked {
    lateinit var userViewModel: ViewModel
    lateinit var estudiantesAdapter: EstudiantesAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var btnNewUser: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        userViewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        estudiantesAdapter = EstudiantesAdapter(this)
        recyclerView = findViewById(R.id.recyclerView)
        btnNewUser = findViewById(R.id.btnNewEsts)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        userViewModel!!.allUsers.observe(this, Observer<List<Estudiantes?>> { users ->
            if (users.size > 0) {
                estudiantesAdapter!!.setData(users as List<Estudiantes>?)
                recyclerView.setAdapter(estudiantesAdapter)
            }
        })
        btnNewUser.setOnClickListener(View.OnClickListener { addUsers(this@HomeActivity) })
    }

    fun addUsers(context: Context?) {
        val builder = AlertDialog.Builder(this)
        val view1 = layoutInflater.inflate(R.layout.row_add_ests, null)
        val addUser = view1.findViewById<Button>(R.id.btnAddEst)
        addUser.setOnClickListener {
            val edUsers = view1.findViewById<EditText>(R.id.edEst)
            val edFecha = view1.findViewById<EditText>(R.id.edFechaN)
            val edCalf = view1.findViewById<EditText>(R.id.edNota)
            val edIdMat = view1.findViewById<EditText>(R.id.edIdMat)
            val estudiantes = Estudiantes()
            estudiantes.username = edUsers.text.toString()
            estudiantes.fecha_nac = edFecha.text.toString()
            estudiantes.calf = edCalf.text.toString()
            estudiantes.idMat = edIdMat.text.toString().toInt()
            userViewModel!!.insertUsers(estudiantes)
        }
        builder.setView(view1)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun detailsUsers(estudiantes: Estudiantes) {
        val builder = AlertDialog.Builder(this)
        val view1 = layoutInflater.inflate(R.layout.detalles_estudiante, null)
        val users = view1.findViewById<TextView>(R.id.nomEstV)
        val fechaN = view1.findViewById<TextView>(R.id.fechaEstV)
        val calf = view1.findViewById<TextView>(R.id.calfV)
        val idMat = view1.findViewById<TextView>(R.id.idMatV)
        users.text = estudiantes.username
        fechaN.text = estudiantes.fecha_nac
        calf.text = estudiantes.calf
        idMat.text = estudiantes.idMat.toString()
        builder.setView(view1)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun updateUsers(estudiantes: Estudiantes) {
        val builder = AlertDialog.Builder(this)
        val view1 = layoutInflater.inflate(R.layout.row_add_ests, null)
        val edUsers = view1.findViewById<EditText>(R.id.edEst)
        val edFechaN = view1.findViewById<EditText>(R.id.edFechaN)
        val edCalf = view1.findViewById<EditText>(R.id.edNota)
        val edIdMat = view1.findViewById<EditText>(R.id.edIdMat)
        edUsers.setText(estudiantes.username)
        edFechaN.setText(estudiantes.fecha_nac)
        edCalf.setText(estudiantes.calf)
        edIdMat.setText(estudiantes.idMat.toString())
        val addUser = view1.findViewById<Button>(R.id.btnAddEst)
        addUser.text = "Actualizar"
        addUser.setOnClickListener {
            estudiantes.username = edUsers.text.toString()
            estudiantes.fecha_nac = edFechaN.text.toString()
            estudiantes.calf = edCalf.text.toString()
            estudiantes.idMat = edIdMat.text.toString().toInt()
            userViewModel!!.updateUser(estudiantes)
        }
        builder.setView(view1)
        val alertDialog = builder.create()
        alertDialog.show()
    }


    override fun updateClicked(estudiantes: Estudiantes?) {
        if (estudiantes != null) {
            updateUsers(estudiantes)
        }
    }

    override fun deleteClicked(estudiantes: Estudiantes?) {
        userViewModel!!.deleteUser(estudiantes)
    }

    override fun detailsClicked(estudiantes: Estudiantes?) {
        if (estudiantes != null) {
            detailsUsers(estudiantes)
        }
    }
}

private fun <T> LiveData<T>?.observe(homeActivity: HomeActivity, observer: Observer<List<Estudiantes?>>) {

}
