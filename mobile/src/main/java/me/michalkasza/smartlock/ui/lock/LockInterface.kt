package me.michalkasza.smartlock.ui.lock

import me.michalkasza.smartlock.base.BaseView

interface LockInterface {
    interface View: BaseView {

    }

    interface UserInteractions {
        fun lockChanged()
    }
}