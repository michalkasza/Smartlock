package me.michalkasza.smartlock.ui.lock.status

import android.app.Application
import androidx.databinding.ObservableField
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
    val currentLock = ObservableField<Lock>()

    override fun lockChanged(lock: Lock) {
        currentLock.set(lock)
        LogsRepository.getLogs(lock)
        view.setAppToolbarTitle(lock.name)
    }

    override fun lockStatusChanged(isLocked: Boolean) {
        LocksRepository.changeLockState(LocksRepository.currentLock.value, isLocked)
        if(!isLocked) {
            LogsRepository.addLog(LocksRepository.currentLock.value, UsersRepository.currentUser.value)
        }
    }
}