package com.example.dagger2sample.ui.user

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.dagger2sample.MainViewModel
import com.example.dagger2sample.R
import com.example.dagger2sample.di.APIInterface
import com.example.dagger2sample.model.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserViewModel: MainViewModel() {

    @Inject
    lateinit var userApi: APIInterface

    val users = MutableLiveData<List<User>>()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val onErrorClick = View.OnClickListener {
        loadUsers()
    }

    private lateinit var subscription: Disposable

    init {
        loadUsers()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadUsers() {
        subscription = userApi.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {  loadingVisibility.value = View.VISIBLE ; errorMessage.value = null }
            .doOnTerminate {  loadingVisibility.value = View.GONE }
            .subscribeWith(object : DisposableObserver<List<User>>() {
                override fun onNext(value: List<User>) {
                    users.postValue(value)
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