package me.michalkasza.smartlock.ui.lock.status

import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.LogEntry
import java.util.*

interface StatusInterface {
    interface View: BaseView { }
    interface UserInteractions {
        fun lockChanged(lock: Lock)
    }
}