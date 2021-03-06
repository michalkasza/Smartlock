package dev.michalkasza.smartlock.ui.main

import android.app.Application
import androidx.databinding.ObservableField
import android.view.View
import dev.michalkasza.smartlock.base.BaseView
import dev.michalkasza.smartlock.base.BaseViewModel
import dev.michalkasza.smartlock.data.model.Lock
import dev.michalkasza.smartlock.data.model.User

class MainViewModel(baseView: BaseView, var app: Application): BaseViewModel(app), MainInterface.UserInteractions {
    val userObservable = ObservableField<User?>()
    val adapterObservable = ObservableField<MainNavDrawerAdapter>()

    val locks = ArrayList<Lock>()
    val view = baseView as MainInterface.View

    init {
        adapterObservable.set(MainNavDrawerAdapter(locks, view.getSupportFragmentManager()))
    }

    override fun userChanged(user: User) {
        userObservable.set(user)
    }

    override fun locksChanged(locks: ArrayList<Lock>) {
        this.locks.clear()
        this.locks.addAll(locks)
        adapterObservable.get()?.notifyDataSetChanged()
    }

    override fun logoutClicked(componentView: View) {
        view.logout()
    }

    override fun closeNavdrawer() {
        view.closeNavdrawer()
    }
}