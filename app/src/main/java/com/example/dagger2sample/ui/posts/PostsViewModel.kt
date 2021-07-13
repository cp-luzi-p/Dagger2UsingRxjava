package com.example.dagger2sample.ui.posts

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.dagger2sample.MainViewModel
import com.example.dagger2sample.di.APIInterface
import com.example.dagger2sample.model.Posts
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsViewModel: MainViewModel() {

    @Inject
    lateinit var userApi: APIInterface

    val posts = MutableLiveData<List<Posts>>()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    private val post: List<Posts> = ArrayList()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val onErrorClick = View.OnClickListener {
        loadPosts(post[0].postId)
    }

    private lateinit var subscription: Disposable

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

     fun loadPosts(id:Int) {
        subscription = userApi.getPostList(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {  loadingVisibility.value = View.VISIBLE ; errorMessage.value = null }
            .doOnTerminate {  loadingVisibility.value = View.GONE }
            .subscribeWith(object : DisposableObserver<List<Posts>>() {
                override fun onNext(value: List<Posts>) {
                    posts.postValue(value)
                }

                override fun onError(e: Throwable) {
                    e.message?.toInt().also { errorMessage.value = it }
                }

                override fun onComplete() {
                    loadingVisibility.value = View.GONE
                }
            })
    }
}