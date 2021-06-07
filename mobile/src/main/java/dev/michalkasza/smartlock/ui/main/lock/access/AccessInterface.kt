package dev.michalkasza.smartlock.ui.main.lock.access

import dev.michalkasza.smartlock.base.BaseView
import dev.michalkasza.smartlock.data.model.Lock
import dev.michalkasza.smartlock.data.model.User

interface AccessInterface {
    interface View: BaseView {
        fun showGrantUserDialog()

    }
    interface UserInteractions {
        fun lockChanged(lock: Lock)
        fun accessedUsersChanged(users: ArrayList<User>)
        fun grantUserClicked()
    }
}