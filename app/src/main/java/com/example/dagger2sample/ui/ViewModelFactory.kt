package com.example.dagger2sample.ui

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dagger2sample.R
import com.example.dagger2sample.ui.posts.PostsViewModel
import com.example.dagger2sample.ui.user.UserViewModel

class ViewModelFactory(private val activity: FragmentActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel() as T
        } else if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostsViewModel() as T
        } else {
            Toast.makeText(activity.applicationContext, R.string.error, Toast.LENGTH_SHORT).show()
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}