package com.example.roomdatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ViewModel(application: Application) : AndroidViewModel(application) {
    var estudiantesRepository: EstudiantesRepository
    var userList: LiveData<List<Estudiantes?>?>?
    val allUsers: LiveData<List<Estudiantes?>?>?
        get() = estudiantesRepository.allUsers

    fun insertUsers(estudiantes: Estudiantes?) {
        estudiantesRepository.insertUsers(estudiantes)
    }

    fun updateUser(estudiantes: Estudiantes?) {
        estudiantesRepository.updateUser(estudiantes)
    }

    fun deleteUser(estudiantes: Estudiantes?) {
        estudiantesRepository.deleteUser(estudiantes)
    }

    init {
        estudiantesRepository = EstudiantesRepository(application)
        userList = estudiantesRepository.allUsers
    }
}