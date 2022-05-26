package com.example.bike_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecordAdapter (
    private val records: MutableList<Record>
): RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {
        class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val tvTime = itemView.findViewById<TextView>(R.id.tv_time)
            val tvDate = itemView.findViewById<TextView>(R.id.tv_date)

            fun bind(record: Record){
                tvTime.text = record.time.toString()
                tvDate.text = record.date
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
            return RecordViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
            )
        }

        fun addRecord(record: Record) {
            records.add(record)
        }

        override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
            val record = records[position]
            holder.bind(record)
        }

        override fun getItemCount(): Int {
            return records.size
        }
}