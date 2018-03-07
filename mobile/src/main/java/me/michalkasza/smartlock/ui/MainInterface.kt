package me.michalkasza.smartlock.ui

import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User

interface MainInterface {
    interface View: BaseView {

    }

    interface UserInteractions {
        fun userChanged(user: User)
        fun locksChanged(locks: ArrayList<Lock>)
    }
}