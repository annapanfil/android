package com.example.bike_app

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CaptionedImagesAdapter(val captions: Array<String?>, val imageIds: IntArray): RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>() {
    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cv: CardView = LayoutInflater.from(parent.context).inflate(R.layout.card_captioned_image, parent, false) as CardView
        return ViewHolder(cv)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView: CardView = holder.cardView;
        val imageView: ImageView = cardView.findViewById(R.id.iv_image) as (ImageView)
        val drawable: Drawable? = ContextCompat.getDrawable(cardView.context, imageIds[position])
        imageView.setImageDrawable(drawable)
        imageView.contentDescription = captions[position]
        val textView = cardView.findViewById(R.id.tv_name) as TextView
        textView.text = captions[position]
    }

    override fun getItemCount(): Int {
        return captions.size
    }
}