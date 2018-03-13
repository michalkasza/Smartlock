package me.michalkasza.smartlock.ui.lock.access

import android.app.Application
import android.databinding.ObservableField
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User
import me.michalkasza.smartlock.data.repository.UsersRepository

class AccessViewModel(baseView: BaseView, app: Application): BaseViewModel(app), AccessInterface.UserInteractions {
    val view = baseView as AccessInterface.View

    val adapterObservable = ObservableField<AccessAdapter>()
    val layoutManagerObservable = ObservableField<LinearLayoutManager>()

    val accessedUsers = ArrayList<User>()

    init {
        adapterObservable.set(AccessAdapter(accessedUsers))
        layoutManagerObservable.set(view.getLinearVerticalLayoutManager())
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