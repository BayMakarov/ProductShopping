package com.ozan.productshopping.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.ozan.productshopping.MainActivity
import com.ozan.productshopping.R
import com.ozan.productshopping.models.featured
import com.squareup.picasso.Picasso


class FeatureAdapter(
    featuredOlanlarList: List<featured>,
    mainActivity: MainActivity
) : PagerAdapter() {


    private var models: List<featured> = featuredOlanlarList
    private var layoutInflater: LayoutInflater? = null
    private var context: Context = mainActivity


    override fun getCount(): Int {
        return models.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater!!.inflate(R.layout.item_feature, container, false)

        val imageView: ImageView
        val title: TextView
        val desc: TextView

        imageView = view.findViewById(R.id.image)
        title = view.findViewById(R.id.title)
        desc = view.findViewById(R.id.txt_des)

        Picasso.get().load(models[position].cover?.url).into(imageView);

        title.text = models[position].title
        desc.text = models[position].sub_title
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }


}