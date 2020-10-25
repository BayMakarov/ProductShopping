package com.ozan.productshopping.models

import android.provider.MediaStore
import com.google.gson.annotations.SerializedName

class category {

    @SerializedName("parent_category")
    var parent_category : parent_category? = null

    @SerializedName("parent_id")
    var parent_id : Int = 0

    @SerializedName("name")
    var name : String = ""

    @SerializedName("id")
    var id : Int = 0

    @SerializedName("logo")
    var logo : images = images()

    @SerializedName("cover")
    var cover : cover = cover()

    @SerializedName("children")
    var children : List<children_category> = ArrayList()

}