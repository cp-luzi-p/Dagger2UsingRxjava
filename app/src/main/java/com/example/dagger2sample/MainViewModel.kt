package com.example.dagger2sample

import androidx.lifecycle.ViewModel
import com.example.dagger2sample.di.component.DaggerViewModelComponent
import com.example.dagger2sample.di.component.ViewModelComponent
import com.example.dagger2sample.di.module.RetrofitModule
import com.example.dagger2sample.ui.posts.PostsViewModel
import com.example.dagger2sample.ui.user.UserViewModel

abstract class MainViewModel : ViewModel(){
    private val injector: ViewModelComponent = DaggerViewModelComponent
        .builder()
        .retrofitModule(RetrofitModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is UserViewModel -> injector.inject(this)
            is PostsViewModel -> injector.inject(this)
        }
    }
}