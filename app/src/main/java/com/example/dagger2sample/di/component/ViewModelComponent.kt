package com.example.dagger2sample.di.component

import com.example.dagger2sample.di.module.RetrofitModule
import com.example.dagger2sample.ui.posts.PostsViewModel
import com.example.dagger2sample.ui.user.UserViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface ViewModelComponent {

    fun inject(userViewModel: UserViewModel)
    fun inject(postsViewModel: PostsViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelComponent

        fun retrofitModule(retrofitModule: RetrofitModule): Builder
    }
}