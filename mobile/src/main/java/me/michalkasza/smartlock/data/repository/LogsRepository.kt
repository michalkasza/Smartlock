package me.michalkasza.smartlock.data.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.LogEntry

object LogsRepository {
    val TAG = LogsRepository::class.java.simpleName
    val interactor = LogsInteractor()

    val currentLockLogs = MutableLiveData<ArrayList<LogEntry>>()

    fun getLogs(lock: Lock) {
        currentLockLogs.value = ArrayList()
        lock.logs.forEach { logId -> getLog(logId) }
    }

    private fun getLog(logId: String) {
        interactor.getLog(logId).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { log ->
                    val tempLogs = currentLockLogs.value
                    tempLogs?.add(log)
                    currentLockLogs.value = tempLogs
                },
                onError = { Log.e(TAG, "Error") }
        )
    }
}