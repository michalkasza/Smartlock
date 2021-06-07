package dev.michalkasza.smartlock.data.repository

import androidx.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import dev.michalkasza.smartlock.data.model.Lock
import dev.michalkasza.smartlock.data.model.User

object LocksRepository {
    private val TAG = LocksRepository::class.java.simpleName
    private val interactor = LocksInteractor()

    val currentLock = MutableLiveData<Lock>()
    val userLocks = MutableLiveData<ArrayList<Lock>>()

    fun getLocks(user: User) {
        userLocks.value?.clear()
        user.locksOwned.forEach { ownedLockId -> getLock(ownedLockId) }
        user.locksGranted.forEach { grantedLockId -> getLock(grantedLockId) }
    }

    private fun getLock(lockId: String) {
        interactor.getLock(lockId).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { lock ->
                    val tempLocks = ArrayList<Lock>()
                    userLocks.value?.let { tempLocks.addAll(it) }
                    lock.id = lockId
                    if(currentLock.value?.id.equals(lockId)) {
                        currentLock.value = lock
                    }
                    tempLocks.forEach {
                        if(it.id == lockId) {
                            tempLocks.remove(it)
                        }
                    }
                    tempLocks.add(lock)
                    userLocks.value = tempLocks
                },
                onError = { Log.e(TAG, "Error") }
        )
    }

    fun changeLockState(lockId: Lock?, lockState: Boolean) {
        lockId?.let { interactor.changeLockState(lockId, lockState) }
    }
}