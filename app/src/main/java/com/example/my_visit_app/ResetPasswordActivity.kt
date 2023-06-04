package com.example.my_visit_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {
    lateinit var resetPasswordMailEditText: EditText
    lateinit var resetPasswordButton: Button
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        resetPasswordMailEditText = findViewById(R.id.resetPasswordMailEditText)
        resetPasswordButton = findViewById(R.id.resetPasswordButton)
        firebaseAuth = FirebaseAuth.getInstance()





            resetPasswordButton.setOnClickListener() {

                if (resetPasswordMailEditText.text.toString().isNotEmpty()) {
                firebaseAuth.sendPasswordResetEmail(resetPasswordMailEditText.text.toString())
                    .addOnSuccessListener {

                        Log.d("Reset password state ", "is successful")

                        Toast.makeText(
                            this@ResetPasswordActivity,
                            "The Password reset mail has been sent",
                            Toast.LENGTH_LONG
                        ).show()

                    }.addOnFailureListener {

                        Toast.makeText(
                            this@ResetPasswordActivity,
                            "Error: ${it.message.toString()}",
                            Toast.LENGTH_LONG
                        ).show()

                        Log.d("Reset password state", " is not successful" + it.message.toString())
                    }

            }
        }
    }
}