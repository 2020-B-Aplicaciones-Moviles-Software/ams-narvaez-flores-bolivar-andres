package com.example.a03_firebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class MainActivity : AppCompatActivity() {
    private val mAuth: FirebaseAuth? = FirebaseAuth.getInstance()

    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("message")
    var db = FirebaseFirestore.getInstance()

    var user: Map<String, Any> = HashMap()
    private val mStorageRef: StorageReference? = FirebaseStorage.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentUser = mAuth!!.currentUser
        //updateUI(currentUser)
        myRef.setValue("Hello, World!");

    }
    fun onTokenRefresh(){
        val refreshedToken = FirebaseInstanceId.getInstance().token
        //Log.d(FragmentActivity.TAG, "Refreshed token: $refreshedToken")

    }
}