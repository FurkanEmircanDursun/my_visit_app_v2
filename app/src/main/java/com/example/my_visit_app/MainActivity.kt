package com.example.my_visit_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    lateinit var registerButton: Button
    lateinit var mailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton: Button
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mailEditText = findViewById(R.id.resetPasswordMailEditText)
        loginButton = findViewById(R.id.loginButton)
        passwordEditText = findViewById(R.id.passwordEditText)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this@MainActivity, VisitActivity::class.java))

        }

        registerButton = findViewById(R.id.registerButton)

      if (mailEditText.text.isNotEmpty()&&passwordEditText.text.isNotEmpty()){

          registerButton.setOnClickListener {

              val mail = mailEditText.text
              val password = passwordEditText.text


              firebaseAuth.createUserWithEmailAndPassword(mail.toString(), password.toString())
                  .addOnSuccessListener {

                      Log.d("firebase auth state", "create User state is successful ")

                      val user = hashMapOf(
                          "E-mail" to mail.toString(),
                          "Password" to password.toString(),

                          )


                      firestore.collection("Users").document(firebaseAuth.currentUser!!.uid).set(user)
                          .addOnSuccessListener {
                              Log.d("Firestore state", "firestore state is successful")

                              startActivity(Intent(this@MainActivity, VisitActivity::class.java))
                          }.addOnFailureListener {

                              Log.d(
                                  "Firestore state", "firestore state is not successful because : $it"
                              )

                          }

                  }.addOnFailureListener {

                      Toast.makeText(applicationContext, "${it.message}", Toast.LENGTH_LONG).show()
                      Log.d(
                          "firebase auth state", "create User state is not successful ${it.message} "
                      )
                  }

          }
      }

        loginButton.setOnClickListener {


            startActivity(Intent(this@MainActivity, LoginActivity::class.java))


        }
    }
}