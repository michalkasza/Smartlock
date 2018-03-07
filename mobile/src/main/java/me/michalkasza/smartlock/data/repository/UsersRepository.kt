package me.michalkasza.smartlock.data.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import me.michalkasza.smartlock.data.model.User

object UsersRepository {
    val TAG = UsersRepository::class.java.simpleName
    val interactor = UsersInteractor()

    val currentUser = MutableLiveData<User>()

    fun getUser(userId: String) {
        interactor.getUser(userId).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { user ->  currentUser.value = user },
                onError = { Log.e(TAG, "Error") }
        )
    }
}