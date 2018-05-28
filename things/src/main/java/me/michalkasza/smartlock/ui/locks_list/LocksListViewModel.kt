package me.michalkasza.smartlock.ui.locks_list

import android.app.Application
import android.view.View
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel

class LocksListViewModel(baseView: BaseView, app: Application): BaseViewModel(app), LocksListInterface.UserInteractions {
    val view = baseView as LocksListInterface.View

    override fun getLocks() {

    }

    override fun initNewLock() {

    }

    override fun removeLock() {

    }

    override fun addLockClicked(componentView: View) {
        view.showNewLockDialog()
    }
}