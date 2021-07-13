package com.example.dagger2sample.model

import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("id")
    private var id: Int = 0

    @SerializedName("name")
    private var name: String = ""

    @SerializedName("email")
    private var email: String = ""

    fun getEmail(): String {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String {
        return name
    }

}