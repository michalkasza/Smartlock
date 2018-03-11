package me.michalkasza.smartlock.ui

import android.app.Application
import android.databinding.ObservableField
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User

class MainViewModel(baseView: BaseView, var app: Application): BaseViewModel(app), MainInterface.UserInteractions {
    val userObservable = ObservableField<User?>()
    val adapterOvservable = ObservableField<MainNavDrawerAdapter>()

    val locks = ArrayList<Lock>()
    val view = baseView as MainInterface.View

    init {
        adapterOvservable.set(MainNavDrawerAdapter(locks))
    }

    override fun userChanged(user: User) {
        userObservable.set(user)
    }

    override fun locksChanged(locks: ArrayList<Lock>) {
        this.locks.clear()
        this.locks.addAll(locks)
        adapterOvservable.get()?.notifyDataSetChanged()
    }
}