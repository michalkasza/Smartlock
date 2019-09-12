package me.michalkasza.smartlock.data.repository

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User
import java.util.*
import kotlin.collections.ArrayList

object LocksRepository {
    val TAG = LocksRepository::class.java.simpleName
    val interactor = LocksInteractor()

    val locks = MutableLiveData<ArrayList<Lock>>()

    @SuppressLint("CheckResult")
    fun getLocks() {
        interactor.getLocks().observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { lock ->
                    val tempLogs = arrayListOf<Lock>()
                    locks.value?.let { tempLogs.addAll(it) }
                    tempLogs.forEach {
                        if(it.id == lock.id) {
                            tempLogs.remove(it)
                        }
                    }
                    tempLogs.add(lock)
                    tempLogs.sortBy { it.name }
                    locks.value = tempLogs
                },
                onError = { Log.e(TAG, "Error") }
        )
    }

    @SuppressLint("CheckResult")
    fun initLocksChangesListener() {
        interactor.addListener().observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { lock ->
                    val tempLogs = arrayListOf<Lock>()
                    val toRemove = arrayListOf<Int>()
                    locks.value?.let { tempLogs.addAll(it) }
                    tempLogs.forEach { if(it.id == lock.id) {
                            toRemove.add(tempLogs.indexOf(it))
                    } }
                    toRemove.forEach { tempLogs.removeAt(it) }
                    tempLogs.add(lock)
                    tempLogs.sortBy { it.name }
                    locks.value = tempLogs
                },
                onError = { Log.e(TAG, "Error") }
        )
    }
}