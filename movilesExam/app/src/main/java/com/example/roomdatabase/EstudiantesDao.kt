package com.example.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EstudiantesDao {
    @Insert
    fun insertUsers(estudiantes: Estudiantes?)

    @Delete
    fun deleteUser(estudiantes: Estudiantes?)

    @Update
    fun updateUser(estudiantes: Estudiantes?)

    @get:Query("SELECT * from estudiantes")
    val allUsers: LiveData<List<Estudiantes?>?>?
}