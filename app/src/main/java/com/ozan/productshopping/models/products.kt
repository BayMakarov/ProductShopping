package com.ozan.productshopping.models

import com.google.gson.annotations.SerializedName

class products {

    @SerializedName("slug")
    var slug : String = ""

    @SerializedName("title")
    var title : String = ""

    @SerializedName("definition")
    var definition : String = ""

    @SerializedName("share_url")
    var share_url : String = ""

    @SerializedName("price")
    var price : Int = 0

    @SerializedName("is_editor_choice")
    var is_editor_choice : Boolean = false

    @SerializedName("shop")
    val shop : shop? = null

    @SerializedName("category")
    val category : shop? = null

    @SerializedName("images")
    val images : List<images> = ArrayList()

}