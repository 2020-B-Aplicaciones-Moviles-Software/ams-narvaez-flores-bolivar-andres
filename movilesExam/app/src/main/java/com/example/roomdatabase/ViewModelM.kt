package com.example.roomdatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ViewModelM(application: Application) : AndroidViewModel(application) {
    var materiasRepository: MateriasRepository
    var matsList: LiveData<List<Materias?>?>?
    val allMats: LiveData<List<Materias?>?>?
        get() = materiasRepository.allMats

    fun insertMats(materias: Materias?) {
        materiasRepository.insertMats(materias)
    }

    fun updateMats(materias: Materias?) {
        materiasRepository.updateMats(materias)
    }

    fun deleteMats(materias: Materias?) {
        materiasRepository.deleteMats(materias)
    }

    init {
        materiasRepository = MateriasRepository(application)
        matsList = materiasRepository.allMats
    }
}