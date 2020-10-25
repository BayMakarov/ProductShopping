package com.ozan.productshopping.models

import com.google.gson.annotations.SerializedName

class DownloadModel {

    @SerializedName("type")
    var type : String = ""

    @SerializedName("title")
    var title : String = ""

    @SerializedName("featured")
    var featured : List<featured> = ArrayList()

    @SerializedName("products")
    var products : List<products> = ArrayList()

    @SerializedName("categories")
    var categories : List<category> = ArrayList()

    @SerializedName("collections")
    var collections : List<collections> = ArrayList()

    @SerializedName("shops")
    var shops : List<shop> = ArrayList()


}