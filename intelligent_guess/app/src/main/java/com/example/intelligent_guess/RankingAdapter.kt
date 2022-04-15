package com.example.intelligent_guess

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RankingAdapter(
    private val ranking: MutableList<User>
) : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {
    class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvPosition = itemView.findViewById<TextView>(R.id.tv_position)
        val tvUsername = itemView.findViewById<TextView>(R.id.tv_username)
        val tvScore = itemView.findViewById<TextView>(R.id.tv_score)

        fun bind(position: Int, user: User){
            tvPosition.text = position.toString()
            tvUsername.text = user.username
            tvScore.text = user.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        return RankingViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        )
    }

    fun addUser(user: User) {
        ranking.add(user)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val user = ranking[position]
        holder.bind(position+1, user)
    }

    override fun getItemCount(): Int {
        return ranking.size
    }
}
