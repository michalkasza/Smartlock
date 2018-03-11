package me.michalkasza.smartlock.ui

import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User

interface MainInterface {
    interface View {
        fun setToolbarTitle(title: String)
        fun setToolbarTitle(titleResourceId: Int)
    }

    interface UserInteractions {
        fun userChanged(user: User)
        fun locksChanged(locks: ArrayList<Lock>)
    }
}