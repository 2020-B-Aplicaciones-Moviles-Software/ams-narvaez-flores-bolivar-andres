package com.example.roomdatabase

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class MateriasRepository(application: Application?) {
    private val materiasDao: MateriasDao
    private val database: Database
    private val matsList: LiveData<List<Materias?>?>?
    val allMats: LiveData<List<Materias?>?>?
        get() = materiasDao.allMats

    fun insertMats(materias: Materias?) {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                database.matsDao()?.insertMats(materias)
                return null
            }
        }.execute()
    }

    fun deleteMats(materias: Materias?) {
        object : AsyncTask<Void?, Void?, Void?>() {
             override fun doInBackground(vararg p0: Void?): Void? {
                database.matsDao()?.deleteMats(materias)
                return null
            }
        }.execute()
    }

    fun updateMats(materias: Materias?) {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                database.matsDao()?.updateMats(materias)
                return null
            }
        }.execute()
    }

    init {
        database = Database.getDatabase(application)!!
        materiasDao = database.matsDao()!!
        matsList = materiasDao.allMats
    }
}