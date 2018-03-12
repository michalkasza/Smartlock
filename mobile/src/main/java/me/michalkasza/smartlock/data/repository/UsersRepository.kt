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
    val currentLockAccessedUsers = MutableLiveData<ArrayList<User>>()

    fun getUser(userId: String) {
        interactor.getUser(userId).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { user ->
                    user.id = userId
                    currentUser.value = user
                },
                onError = { Log.e(TAG, "Error") }
        )
    }

    fun getUsers(usersId: ArrayList<String>) {
        currentLockAccessedUsers.value = ArrayList()
        usersId.forEach { userId -> interactor.getUser(userId).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { user ->
                    val tempList = currentLockAccessedUsers.value
                    user.id = userId
                    tempList?.add(user)
                    currentLockAccessedUsers.value = tempList
                },
                onError = { Log.e(TAG, "Error") }
        ) }
    }
}