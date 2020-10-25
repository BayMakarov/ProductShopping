package com.ozan.productshopping.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.ozan.productshopping.R
import com.ozan.productshopping.models.shop
import com.squareup.picasso.Picasso

class EditorSelectedShopsAdapter (private var editorList: List<shop>) :
    RecyclerView.Adapter<EditorSelectedShopsAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var definition: TextView = view.findViewById(R.id.definition)
        var image: ImageView = view.findViewById(R.id.image_main)
        var imageDef: ImageView = view.findViewById(R.id.image_def)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_editor_selected_shops, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val collection = editorList[position]

        Picasso.get().load(collection.logo?.url).into(holder.image)
        Picasso.get().load(collection.cover?.url).into(holder.imageDef)

        holder.title.text = collection.name
        holder.definition.text = collection.definition

    }
    override fun getItemCount(): Int {
        return editorList.size
    }
}