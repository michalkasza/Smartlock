package me.michalkasza.smartlock.data.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User

object LocksRepository {
    val TAG = LocksRepository::class.java.simpleName
    val interactor = LocksInteractor()

    val currentLock = MutableLiveData<Lock>()
    val userLocks = MutableLiveData<ArrayList<Lock>>()


    fun getLocks(user: User) {
        userLocks.value = ArrayList()
        user.locksOwned.forEach { ownedLockId -> getLock(ownedLockId) }
        user.locksGranted.forEach { grantedLockId -> getLock(grantedLockId) }
    }

    private fun getLock(lockId: String) {
        interactor.getLock(lockId).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { lock ->
                    var tempLocks = userLocks.value
                    tempLocks?.add(lock)
                    userLocks.value = tempLocks
                    tempLocks = null
                },
                onError = { Log.e(TAG, "Error") }
        )
    }
}