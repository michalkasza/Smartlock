package me.michalkasza.smartlock.ui.lock.status

import android.app.Application
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.LogEntry
import me.michalkasza.smartlock.data.repository.LogsRepository

class StatusViewModel(baseView: BaseView, app: Application): BaseViewModel(app), StatusInterface.UserInteractions {
    val view = baseView as StatusInterface.View

    override fun lockChanged(lock: Lock) {
        LogsRepository.getLogs(lock)
        view.setToolbarTitle(lock.name)
        view.updateLastAccess(lock.lastAccessTime, lock.lastAccessUser)
    }

    override fun logsChanged(logs: ArrayList<LogEntry>) {
        view.updateLogs(logs)
    }
}