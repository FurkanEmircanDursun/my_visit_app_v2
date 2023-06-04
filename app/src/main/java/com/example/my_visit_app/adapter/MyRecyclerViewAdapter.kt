package com.example.my_visit_app.adapter

import android.app.AlertDialog
import android.graphics.Color
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.my_visit_app.R
import com.example.my_visit_app.model.Visit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Random

class MyRecyclerViewAdapter(private val dataList: ArrayList<Visit>) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
        holder.currentItem = data
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.itemTitleTextView)
        private val city: TextView = itemView.findViewById(R.id.itemCityTextView)
        private val desc: TextView = itemView.findViewById(R.id.itemDescTextView)
        private val layout: LinearLayout = itemView.findViewById(R.id.linearLayout)

        var currentItem: Visit? = null

        init {
            firestore = FirebaseFirestore.getInstance()
            firebaseAuth = FirebaseAuth.getInstance()
            itemView.setOnLongClickListener {
                val alertDialogBuilder = AlertDialog.Builder(itemView.context)
                alertDialogBuilder.setTitle("Uyarı")
                alertDialogBuilder.setMessage("Bu öğeyi silmek istediğinize emin misiniz?")
                alertDialogBuilder.setPositiveButton("Evet") { dialog, which ->
                    currentItem?.let { visit ->
                        val documentId = visit.uid
                        // Silme işlemini gerçekleştirmek için documentId'yi kullanabilirsiniz
                        firestore.collection("Users")
                            .document(firebaseAuth.currentUser?.uid!!)
                            .collection("My_Visit_List")
                            .document(documentId)
                            .delete()
                            .addOnSuccessListener {
                                // Silme işlemi başarılı olduğunda yapılacak işlemler
                            }
                            .addOnFailureListener { e ->
                                // Silme işlemi başarısız olduğunda yapılacak işlemler
                            }
                    }
                }
                alertDialogBuilder.setNegativeButton("Hayır") { dialog, which ->
                    // Silme işleminden vazgeç veya herhangi başka bir işlem yap
                }

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()

                true
            }
        }

        fun bind(data: Visit) {
            title.text = data.title.capitalize()
            city.text = data.city.capitalize()
            desc.text = data.desc.capitalize()

            val random = Random()
            val color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
            layout.setBackgroundColor(color)
        }
    }
}
