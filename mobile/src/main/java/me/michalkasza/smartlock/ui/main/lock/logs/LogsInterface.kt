package me.michalkasza.smartlock.ui.lock.logs

import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.data.model.LogEntry

interface LogsInterface {
    interface View: BaseView {
    }
    interface UserInteractions {
        fun logsChanged(logs: ArrayList<LogEntry>)
    }
}