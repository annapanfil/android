package com.example.todo_api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val register: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.RankingViewHolder>() {

    inner class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val chbCompleted = itemView.findViewById<CheckBox>(R.id.chb_completed)

        fun bind(todo: Todo){
            tvTitle.text = todo.title
            chbCompleted.isChecked = todo.completed
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        return RankingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        )
    }

    fun addTodo(todo: Todo) {
        register.add(todo)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val todo = register[position]
        holder.bind(todo)
    }

    override fun getItemCount(): Int {
        return register.size
    }
}
