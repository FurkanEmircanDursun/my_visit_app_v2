package com.example.my_visit_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    lateinit var mailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var forgetPasswordTextView: TextView
    lateinit var loginButton: Button
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        mailEditText = findViewById(R.id.mailEditText)
        loginButton = findViewById(R.id.loginButton_2)
        passwordEditText = findViewById(R.id.passwordEditText)
        forgetPasswordTextView = findViewById(R.id.forgetPasswordTextView)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()



        loginButton.setOnClickListener {
            val mail = mailEditText.text
            val password = passwordEditText.text
            firebaseAuth.signInWithEmailAndPassword(mail.toString(), password.toString())
                .addOnSuccessListener {

                    Log.d("firebase auth state", "sign In state is successful ")
                    startActivity(Intent(this@LoginActivity, VisitActivity::class.java))
                    finish()
                }.addOnFailureListener {

                    Toast.makeText(applicationContext, "${it.message}", Toast.LENGTH_LONG).show()
                    Log.d("firebase auth state", "sign In state is not successful ${it.message} ")
                }

        }


    }


}
