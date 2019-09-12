package me.michalkasza.smartlock.ui.lock.access

import android.app.Application
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User
import me.michalkasza.smartlock.data.repository.UsersRepository

class AccessViewModel(baseView: BaseView, app: Application): BaseViewModel(app), AccessInterface.UserInteractions {
    val view = baseView as AccessInterface.View

    val adapterObservable = ObservableField<AccessAdapter>()
    val layoutManagerObservable = ObservableField<androidx.recyclerview.widget.LinearLayoutManager>()

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

    override fun grantUserClicked(componentView: View) {
        view.showGrantUserDialog()
    }
}