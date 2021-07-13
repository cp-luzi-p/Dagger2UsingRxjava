package com.example.dagger2sample.di

import com.example.dagger2sample.model.Posts
import com.example.dagger2sample.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("/users")
    fun getUsers(): Observable<List<User>>

    @GET("/posts")
    fun getPostList(@Query("userId") userId: Int): Observable<List<Posts>>
}