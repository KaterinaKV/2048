package com.app.android2048.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.android2048.R
import com.app.android2048.model.Record

class RecordsAdapter(var items: List<Record>) : RecyclerView.Adapter<RecordsAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false))

    override fun getItemCount(): Int {
        return if (items.size > 5)
            5
        else
            items.size
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val score = itemView.findViewById<TextView>(R.id.score)
        private val name = itemView.findViewById<TextView>(R.id.name)

        fun bind(item: Record) {
            name.text = item.name
            score.text = item.score.toString()
        }
    }

}