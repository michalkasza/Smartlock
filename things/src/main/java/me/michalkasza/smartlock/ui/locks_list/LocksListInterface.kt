package me.michalkasza.smartlock.ui.locks_list


interface LocksListInterface {
    interface View {

    }

    interface UserInteractions {
        fun getLocks()
        fun initNewLock()
        fun removeLock()
    }
}