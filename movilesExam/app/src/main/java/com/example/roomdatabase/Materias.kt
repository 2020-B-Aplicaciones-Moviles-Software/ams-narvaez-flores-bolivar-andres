package com.example.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "materias")
class Materias {
    @PrimaryKey(autoGenerate = true)
    var idM = 0

    @ColumnInfo(name = "matname")
    var matname: String? = null

    @ColumnInfo(name = "codigo")
    var codigo: String? = null

    @ColumnInfo(name = "horas")
    var horas = 0

    @ColumnInfo(name = "profesor")
    var profesor: String? = null

}