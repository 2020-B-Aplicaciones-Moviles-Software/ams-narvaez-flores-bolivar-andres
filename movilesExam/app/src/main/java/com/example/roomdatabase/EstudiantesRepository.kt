package com.example.roomdatabase

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class EstudiantesRepository(application: Application?) {
    private val estudiantesDao: EstudiantesDao
    private val database: Database
    private val usersList: LiveData<List<Estudiantes?>?>?
    val allUsers: LiveData<List<Estudiantes?>?>?
        get() = estudiantesDao.allUsers

    fun insertUsers(estudiantes: Estudiantes?) {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                database.usersDao()?.insertUsers(estudiantes)
                return null
            }
        }.execute()
    }

    fun deleteUser(estudiantes: Estudiantes?) {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                database.usersDao()?.deleteUser(estudiantes)
                return null
            }
        }.execute()
    }

    fun updateUser(estudiantes: Estudiantes?) {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                database.usersDao()?.updateUser(estudiantes)
                return null
            }
        }.execute()
    }

    init {
        database = Database.getDatabase(application)!!
        estudiantesDao = database.usersDao()!!
        usersList = estudiantesDao.allUsers

    }
}