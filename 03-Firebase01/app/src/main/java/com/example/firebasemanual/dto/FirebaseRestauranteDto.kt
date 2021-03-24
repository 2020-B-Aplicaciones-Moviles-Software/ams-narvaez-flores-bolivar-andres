package com.example.firebasemanual.dto

class FirebaseRestauranteDto(
    var uid: String = "",
    var nombre: String = ""
) {
    override fun toString(): String {
        return nombre
    }
}