package com.ozan.productshopping.models

import com.google.gson.annotations.SerializedName

class parent_category {

    @SerializedName("parent_category")
    var parent_category : parent_category? = null

    @SerializedName("parent_id")
    var parent_id : Int = 0

    @SerializedName("name")
    var name : String = ""

    @SerializedName("id")
    var id : Int = 0

}