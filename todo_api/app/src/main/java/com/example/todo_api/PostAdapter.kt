package com.example.todo_api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(
    private val register: MutableList<Post>
) : RecyclerView.Adapter<PostAdapter.RankingViewHolder>() {

    var onItemClick: ((Post)-> Unit)? = null

    inner class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)

        init{
            itemView.setOnClickListener{
                onItemClick?.invoke(register[adapterPosition])
            }
        }
        fun bind(post: Post){
            tvTitle.text = post.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        return RankingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        )
    }

    fun addPost(post: Post) {
        register.add(post)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val post = register[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return register.size
    }
}
