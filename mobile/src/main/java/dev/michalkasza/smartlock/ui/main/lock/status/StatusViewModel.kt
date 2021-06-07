package dev.michalkasza.smartlock.ui.main.lock.status

import android.app.Application
import androidx.databinding.ObservableField
import dev.michalkasza.smartlock.base.BaseView
import dev.michalkasza.smartlock.base.BaseViewModel
import dev.michalkasza.smartlock.data.model.Lock
import dev.michalkasza.smartlock.data.repository.LocksRepository
import dev.michalkasza.smartlock.data.repository.LogsRepository
import dev.michalkasza.smartlock.data.repository.UsersRepository
import dev.michalkasza.smartlock.ui.main.lock.status.StatusInterface

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