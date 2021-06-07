package dev.michalkasza.smartlock.ui.main.lock.status

import dev.michalkasza.smartlock.base.BaseView
import dev.michalkasza.smartlock.data.model.Lock

interface StatusInterface {
    interface View: BaseView { }
    interface UserInteractions {
        fun lockChanged(lock: Lock)
        fun lockStatusChanged(isLocked: Boolean)
    }
}