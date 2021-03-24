package com.example.deberimagenes
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage

class FirebaseStorageManager {
    private val TAG = "FirebaseStoreManager"
    private val mStorageRef = FirebaseStorage.getInstance().reference
    private lateinit var mProgressDialog: ProgressDialog

    fun uploadImage(mContext:Context, imageUri: Uri){
        mProgressDialog = ProgressDialog(mContext)
        mProgressDialog.setMessage("Imagen cargando")
        val uploadTask = mStorageRef.child("users/profilePic.png").putFile(imageUri)
        uploadTask
            .addOnSuccessListener {
            Log.e("firebase-upload", "Imagen subida correctamente")
                mProgressDialog.dismiss()
        }
            .addOnFailureListener{
                Log.e(TAG, "No se subió la imagen")
                mProgressDialog.dismiss()
            }
    }
}