package me.michalkasza.smartlock.ui.main

import android.support.v4.app.FragmentManager
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User

interface MainInterface {
    interface View {
        fun setToolbarTitle(title: String)
        fun setToolbarTitle(titleResourceId: Int)
        fun getSupportFragmentManager(): FragmentManager
    }

    interface UserInteractions {
        fun userChanged(user: User)
        fun locksChanged(locks: ArrayList<Lock>)
    }
}