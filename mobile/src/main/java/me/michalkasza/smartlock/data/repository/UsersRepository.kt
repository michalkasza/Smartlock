package me.michalkasza.smartlock.data.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User

object UsersRepository {
    val TAG = UsersRepository::class.java.simpleName
    val interactor = UsersInteractor()

    val currentUser = MutableLiveData<User>()
    val currentLockAccessedUsers = MutableLiveData<ArrayList<User>>()

    fun getCurrentUser() {
        FirebaseAuth.getInstance().currentUser?.let { firebaseUser ->
            interactor.getUser(firebaseUser.uid).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                    onNext = { user ->
                        currentUser.value = user
                    },
                    onError = { Log.e(TAG, "Error") }
            )
        }
    }

    fun getUsers(usersId: ArrayList<String>) {
        currentLockAccessedUsers.value = ArrayList()
            usersId.forEach { userId -> interactor.getUser(userId).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                    onNext = { user ->
                        val tempList = currentLockAccessedUsers.value
                        tempList?.add(user)
                        currentLockAccessedUsers.value = tempList
                    },
                    onError = { Log.e(TAG, "Error") }
            )
        }
    }

    fun grantLockToUser(lock: Lock, user: User) {
        interactor.grantLockToUser(lock.id, user).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { grantedUser ->
                    Log.e(TAG, "User " + user.name + " has been added to " + lock.name + " lock ACL")
                    // TODO: finalise granted lock implementation
                },
                onError = { Log.e(TAG, "Error") }
        )
    }
}