package me.michalkasza.smartlock.ui.lock.access

import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User

interface AccessInterface {
    interface View: BaseView {
    }
    interface UserInteractions {
        fun lockChanged(lock: Lock)
        fun accessedUsersChanged(users: ArrayList<User>)
    }
}