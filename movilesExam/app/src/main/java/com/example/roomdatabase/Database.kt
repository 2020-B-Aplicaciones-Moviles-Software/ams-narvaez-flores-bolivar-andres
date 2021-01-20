package com.example.roomdatabase

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(entities = [Estudiantes::class, Materias::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun usersDao(): EstudiantesDao?
    abstract fun matsDao(): MateriasDao?

    companion object {
        @Volatile
        var INSTANCE: Database? = null
        fun getDatabase(context: Context?): Database? {
            if (INSTANCE == null) {
                synchronized(Database::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context!!, Database::class.java, "database.db").build()
                    }
                }
            }
            return INSTANCE
        }
    }
}