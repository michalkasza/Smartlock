package me.michalkasza.smartlock.ui.lock.status

import android.app.Application
import android.databinding.ObservableField
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.repository.LocksRepository
import me.michalkasza.smartlock.data.repository.LogsRepository
import me.michalkasza.smartlock.data.repository.UsersRepository
import java.text.SimpleDateFormat
import java.util.*

class StatusViewModel(baseView: BaseView, app: Application): BaseViewModel(app), StatusInterface.UserInteractions {
    val view = baseView as StatusInterface.View

    val lastAccessUsername = ObservableField<String>()
    val lastAccessDate = ObservableField<String>()
    val lastAccessTime = ObservableField<String>()

    override fun lockChanged(lock: Lock) {
        LogsRepository.getLogs(lock)
        view.setSmartlockToolbarTitle(lock.name)
        lastAccessUsername.set(lock.lastAccessUser)
        lock.lastAccessTime?.let { lastAccessDate.set(SimpleDateFormat("dd.MM.yyyy", Locale.US).format(lock.lastAccessTime)) }
        lock.lastAccessTime?.let { lastAccessTime.set(SimpleDateFormat("HH:mm:ss", Locale.US).format(lock.lastAccessTime)) }
    }

    override fun lockStatusChanged(isLocked: Boolean) {
        LocksRepository.changeLockState(LocksRepository.currentLock.value, isLocked)
        if(!isLocked) {
            LogsRepository.addLog(LocksRepository.currentLock.value, UsersRepository.currentUser.value)
        }
    }
}