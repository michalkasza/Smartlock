package me.michalkasza.smartlock.ui.locks_list

import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.data.model.Lock


interface LocksListInterface {
    interface View : BaseView {
        fun showNewLockDialog()
    }

    interface UserInteractions {
        fun locksChanged(locks: List<Lock>)
        fun initNewLock()
        fun removeLock()
        fun addLockClicked(componentView: android.view.View)
    }
}