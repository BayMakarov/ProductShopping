package com.ozan.productshopping.models

import com.google.gson.annotations.SerializedName

class shop {

    @SerializedName("slug")
    var slug : String = ""

    @SerializedName("title")
    var title : String = ""

    @SerializedName("definition")
    var definition : String = ""

    @SerializedName("share_url")
    var share_url : String = ""

    @SerializedName("name")
    var name : String = ""

    @SerializedName("is_editor_choice")
    var is_editor_choice : Boolean = false

    @SerializedName("cover")
    var cover : cover? = null

    @SerializedName("logo")
    var logo : cover? = null

    @SerializedName("popular_products")
    var popular_products : List<products> = ArrayList()

}