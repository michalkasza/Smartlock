package dev.michalkasza.smartlock.ui.locks_list

import dev.michalkasza.smartlock.base.BaseView
import dev.michalkasza.smartlock.data.model.Lock


interface LocksListInterface {
    interface View : BaseView {
        fun initNewLock()
    }

    interface UserInteractions {
        fun locksChanged(locks: List<Lock>)
        fun addLockClicked()
    }
}