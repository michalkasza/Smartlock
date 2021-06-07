package dev.michalkasza.smartlock.data.repository

import androidx.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import dev.michalkasza.smartlock.data.model.Lock
import dev.michalkasza.smartlock.data.model.LogEntry
import dev.michalkasza.smartlock.data.model.User

object LogsRepository {
    private val TAG = LogsRepository::class.java.simpleName
    private val interactor = LogsInteractor()

    val currentLockLogs = MutableLiveData<ArrayList<LogEntry>>()

    fun getLogs(lock: Lock) {
        currentLockLogs.value = ArrayList()
        lock.logs.forEach { logId -> getLog(logId) }
    }

    private fun getLog(logId: String) {
        interactor.getLog(logId).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { log ->
                    val tempLogs = arrayListOf<LogEntry>()
                    currentLockLogs.value?.let { tempLogs.addAll(it) }
                    log.id = logId
                    tempLogs.forEach {
                        if(it.id == logId) {
                            tempLogs.remove(it)
                        }
                    }
                    tempLogs.add(log)
                    currentLockLogs.value = tempLogs
                },
                onError = { Log.e(TAG, "Error") }
        )
    }

    fun addLog(lockId: Lock?, userId: User?) {
        lockId?.let { userId?.let { interactor.addLog(lockId, userId) } }
    }
}