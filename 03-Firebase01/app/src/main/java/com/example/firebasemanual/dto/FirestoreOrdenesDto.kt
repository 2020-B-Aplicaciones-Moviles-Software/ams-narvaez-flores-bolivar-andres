package com.example.firebasemanual.dto

data class FirestoreOrdenesDto(
    var uid:String = "",
    var fechaCreacion: com.google.firebase.Timestamp? = null,
    var restaurante:FirebaseRestauranteDto? = null,
    var review: Int = 0,
    var tiposComida:ArrayList<String> = arrayListOf(),
    var usuario:FirestoreUsuarioDto? = null
){

    override fun toString(): String {
        return "${restaurante}, ${tiposComida}, ${review}"
    }
}