package me.michalkasza.smartlock.data.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import me.michalkasza.smartlock.data.model.Lock

object LogsRepository {
    val TAG = LogsRepository::class.java.simpleName
    val interactor = LogsInteractor()

    val currentLockLogs = MutableLiveData<ArrayList<Log>>()

    fun getLogs(lock: Lock) {
        currentLockLogs.value = ArrayList()
        lock.logs.forEach { logId -> getLog(logId) }
    }

    private fun getLog(logId: String) {
        interactor.getLog(logId).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { log ->  currentLockLogs.value?.add(log) },
                onError = { Log.e(TAG, "Error") }
        )
    }
}