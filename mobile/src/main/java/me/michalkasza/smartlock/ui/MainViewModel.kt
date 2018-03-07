package me.michalkasza.smartlock.ui

import android.app.Application
import android.databinding.ObservableField
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User

class MainViewModel(baseView: BaseView, var app: Application): BaseViewModel(app), MainInterface.UserInteractions {
//    Binding fields
    val user = ObservableField<User?>()
    val navDrawerAdapter = ObservableField<MainNavDrawerAdapter>()
//    Reusables
    val view = baseView as MainInterface.View
    val locks = ArrayList<Lock>()

    init {
        navDrawerAdapter.set(MainNavDrawerAdapter(app, locks))
    }

    override fun userChanged(user: User) {
        this.user.set(user)
    }

    override fun locksChanged(locks: ArrayList<Lock>) {
        this.locks.clear()
        this.locks.addAll(locks)
        navDrawerAdapter.get()?.notifyDataSetChanged()
    }
}