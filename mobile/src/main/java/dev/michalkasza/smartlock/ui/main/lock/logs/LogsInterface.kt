package dev.michalkasza.smartlock.ui.main.lock.logs

import dev.michalkasza.smartlock.base.BaseView
import dev.michalkasza.smartlock.data.model.LogEntry

interface LogsInterface {
    interface View: BaseView {
    }
    interface UserInteractions {
        fun logsChanged(logs: ArrayList<LogEntry>)
    }
}