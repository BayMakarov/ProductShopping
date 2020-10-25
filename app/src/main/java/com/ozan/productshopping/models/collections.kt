package com.ozan.productshopping.models

import com.google.gson.annotations.SerializedName

class collections {


    @SerializedName("title")
    var title : String = ""

    @SerializedName("definition")
    var definition : String = ""

    @SerializedName("share_url")
    var share_url : String = ""

    @SerializedName("logo")
    var logo : images = images()

    @SerializedName("cover")
    var cover : cover = cover()
}