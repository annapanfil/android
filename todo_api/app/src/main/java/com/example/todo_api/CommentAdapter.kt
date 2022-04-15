package com.example.todo_api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommentAdapter(
    private val register: MutableList<Comment>
) : RecyclerView.Adapter<CommentAdapter.RankingViewHolder>() {

    inner class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvMail = itemView.findViewById<TextView>(R.id.tv_email)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        private val tvBody = itemView.findViewById<TextView>(R.id.tv_body)

        fun bind(comment: Comment){
            tvMail.text = comment.mail
            tvTitle.text = comment.title
            tvBody.text = comment.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        return RankingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        )
    }

    fun addComment(comment: Comment) {
        register.add(comment)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val comment = register[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int {
        return register.size
    }
}
