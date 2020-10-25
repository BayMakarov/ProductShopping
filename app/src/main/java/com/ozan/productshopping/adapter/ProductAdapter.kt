package com.ozan.productshopping.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.ozan.productshopping.R
import com.ozan.productshopping.models.products
import com.squareup.picasso.Picasso

class ProductAdapter (private var productList: List<products>) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var desc: TextView = view.findViewById(R.id.txt_des)
        var image: ImageView = view.findViewById(R.id.image)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = productList[position]

        if(product.images.isNotEmpty()) {
            Picasso.get().load(product.images[0].url).into(holder.image);
        }


        holder.title.text = product.title
        holder.desc.text = product.shop?.name

    }
    override fun getItemCount(): Int {
        return productList.size
    }
}