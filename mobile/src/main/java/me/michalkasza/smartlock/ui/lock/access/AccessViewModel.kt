package me.michalkasza.smartlock.ui.lock.access

import android.app.Application
import android.databinding.ObservableField
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User
import me.michalkasza.smartlock.data.repository.UsersRepository

class AccessViewModel(baseView: BaseView, app: Application): BaseViewModel(app), AccessInterface.UserInteractions {
    val adapterObservable = ObservableField<AccessAdapter>()

    val accessedUsers = ArrayList<User>()

    init {
        adapterObservable.set(AccessAdapter(accessedUsers))
    }

    override fun lockChanged(lock: Lock) {
        UsersRepository.getUsers(lock.accessList)
    }

    override fun accessedUsersChanged(users: ArrayList<User>) {
        this.accessedUsers.clear()
        this.accessedUsers.addAll(users)
        adapterObservable.get()?.notifyDataSetChanged()
    }
}