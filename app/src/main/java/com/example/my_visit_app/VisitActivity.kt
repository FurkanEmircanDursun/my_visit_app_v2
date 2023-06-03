package com.example.my_visit_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class VisitActivity : AppCompatActivity() {
    lateinit var signOutTextView: TextView
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit)
        firebaseAuth=FirebaseAuth.getInstance()
        signOutTextView=  findViewById(R.id.signOutTextVİew)
        signOutTextView.setOnClickListener {


            firebaseAuth.signOut()

            startActivity(Intent(this,MainActivity::class.java))
            finish()


        }
    }
}