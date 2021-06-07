package dev.michalkasza.smartlock.ui.main.lock.access

import android.app.Application
import androidx.databinding.ObservableField
import dev.michalkasza.smartlock.base.BaseView
import dev.michalkasza.smartlock.base.BaseViewModel
import dev.michalkasza.smartlock.data.model.Lock
import dev.michalkasza.smartlock.data.model.User
import dev.michalkasza.smartlock.data.repository.UsersRepository
import dev.michalkasza.smartlock.ui.main.lock.access.AccessAdapter
import dev.michalkasza.smartlock.ui.main.lock.access.AccessInterface

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

    override fun grantUserClicked() {
        view.showGrantUserDialog()
    }
}