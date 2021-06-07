package dev.michalkasza.smartlock.ui.main

import androidx.fragment.app.FragmentManager
import dev.michalkasza.smartlock.data.model.Lock
import dev.michalkasza.smartlock.data.model.User

interface MainInterface {
    interface View {
        fun getSupportFragmentManager(): FragmentManager
        fun logout()
        fun closeNavdrawer()
    }

    interface UserInteractions {
        fun userChanged(user: User)
        fun locksChanged(locks: ArrayList<Lock>)
        fun logoutClicked(componentView: android.view.View)
        fun closeNavdrawer()
    }
}