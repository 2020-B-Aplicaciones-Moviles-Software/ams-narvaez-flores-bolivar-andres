package com.example.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estudiantes")
class Estudiantes {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "username")
    var username: String? = null

    @ColumnInfo(name = "fecha_nac")
    var fecha_nac: String? = null

    @ColumnInfo(name = "calf")
    var calf: String? = null

    @ColumnInfo(name = "idMat")
    var idMat = 0

}