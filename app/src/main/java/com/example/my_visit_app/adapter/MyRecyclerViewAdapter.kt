package com.example.my_visit_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.my_visit_app.R
import com.example.my_visit_app.model.Visit

class MyRecyclerViewAdapter(private val dataList: ArrayList<Visit>) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.itemTitleTextView)
        private val city: TextView = itemView.findViewById(R.id.itemCityTextView)
        private val desc: TextView = itemView.findViewById(R.id.itemDescTextView)

        fun bind(data: Visit) {
            title.text = data.title
            city.text = data.city
            desc.text = data.desc
        }
    }
}
