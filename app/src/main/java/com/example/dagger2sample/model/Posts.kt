package com.example.dagger2sample.model

import com.google.gson.annotations.SerializedName

class Posts {

    @SerializedName("id")
    var postId: Int = 0

    @SerializedName("title")
    var postTitle: String = ""

    @SerializedName("body")
    var postBody: String = ""
}