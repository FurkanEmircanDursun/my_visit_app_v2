package com.example.my_visit_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_visit_app.adapter.MyRecyclerViewAdapter
import com.example.my_visit_app.model.Visit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class VisitActivity : AppCompatActivity() {
    lateinit var signOutTextView: TextView
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    lateinit var addDataButton: Button
    lateinit var descEditText: EditText
    lateinit var titleEditText: EditText
    lateinit var cityEditText: EditText
    private lateinit var myRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit)
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        signOutTextView = findViewById(R.id.signOutTextVİew)
        addDataButton = findViewById(R.id.addDataButton)
        descEditText = findViewById(R.id.descEditText)
        titleEditText = findViewById(R.id.titleEditText)
        cityEditText = findViewById(R.id.cityEditText)
        myRecyclerView = findViewById(R.id.recyclerView)
        myRecyclerView.layoutManager = LinearLayoutManager(this)
        signOutTextView.setOnClickListener {


            firebaseAuth.signOut()

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
        if (titleEditText.text.toString().isEmpty() || cityEditText.text.toString()
                .isEmpty() || descEditText.text.toString().isEmpty()
        ) {

            addDataButton.setOnClickListener {

                val data = hashMapOf(
                    "title" to titleEditText.text.toString(),
                    "city" to cityEditText.text.toString(),
                    "description" to descEditText.text.toString(),

                    )
                firestore.collection("Users").document(firebaseAuth.currentUser!!.uid)
                    .collection("My_Visit_List").document().set(data).addOnSuccessListener {

                        Log.d("firestore data sending", "is successfully")

                    }.addOnFailureListener {

                        Toast.makeText(applicationContext, "${it.message}", Toast.LENGTH_SHORT)
                            .show()
                        Log.d(
                            "firestore data sending", "is not successful ${it.message} "
                        )
                    }
            }
        }


        val currentUserUid = firebaseAuth.currentUser!!.uid

        val query = firestore.collection("Users").document(currentUserUid)
            .collection("My_Visit_List")

        query.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                // Hata durumunda işlemleri yönetin
                return@addSnapshotListener
            }


            val visitList =
                ArrayList<Visit>() // Verileri depolamak için Visit sınıfı türünde bir liste oluşturun

            for (document in snapshot?.documents ?: emptyList()) {
                val title = document.getString("title") ?: ""
                val city = document.getString("city") ?: ""
                val desc = document.getString("description") ?: ""

                val visit = Visit(title, city, desc)
                visitList.add(visit)
            }

            // RecyclerView ile dataList'i kullanarak verileri gösterin
            val adapter = MyRecyclerViewAdapter(visitList)
            myRecyclerView.adapter = adapter
        }
    }
}