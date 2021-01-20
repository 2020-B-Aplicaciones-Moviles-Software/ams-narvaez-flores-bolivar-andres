package com.example.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MateriasDao {
    @Insert
    fun insertMats(materias: Materias?)

    @Delete
    fun deleteMats(materias: Materias?)

    @Update
    fun updateMats(materias: Materias?)

    @get:Query("SELECT * FROM materias")
    val allMats: LiveData<List<Materias?>?>?
}