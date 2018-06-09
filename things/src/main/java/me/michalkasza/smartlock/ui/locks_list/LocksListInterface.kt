package me.michalkasza.smartlock.ui.locks_list

import me.michalkasza.smartlock.base.BaseView


interface LocksListInterface {
    interface View : BaseView {
        fun showNewLockDialog()
    }

    interface UserInteractions {
        fun getLocks()
        fun initNewLock()
        fun removeLock()
        fun addLockClicked(componentView: android.view.View)
    }
}