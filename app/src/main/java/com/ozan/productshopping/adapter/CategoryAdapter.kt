package com.ozan.productshopping.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.ozan.productshopping.R
import com.ozan.productshopping.models.category
import com.squareup.picasso.Picasso

class CategoryAdapter (private var collectionsList: List<category>) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var image: ImageView = view.findViewById(R.id.image)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val collection = collectionsList[position]

        Picasso.get().load(collection.logo.url).into(holder.image);

        holder.title.text = collection.name

    }
    override fun getItemCount(): Int {
        return collectionsList.size
    }
}