package com.example.todo_api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val register: MutableList<User>
) : RecyclerView.Adapter<UserAdapter.RankingViewHolder>() {

    var onItemClick: ((User)-> Unit)? = null

    inner class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        val tvMail = itemView.findViewById<TextView>(R.id.tv_email)
        val tvTodos = itemView.findViewById<TextView>(R.id.tv_todos)
        val tvPosts = itemView.findViewById<TextView>(R.id.tv_posts)

        init{
            itemView.setOnClickListener{
                onItemClick?.invoke(register[adapterPosition])
            }
        }
        fun bind(user: User){
            tvName.text = user.name
            tvMail.text = user.email
            tvTodos.text = user.taskCtr.toString()
            tvPosts.text = user.postCtr.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        return RankingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    fun addUser(user: User) {
        register.add(user)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val user = register[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return register.size
    }
}
