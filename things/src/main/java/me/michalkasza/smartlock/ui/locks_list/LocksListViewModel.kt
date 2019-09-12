package me.michalkasza.smartlock.ui.locks_list

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.LogEntry
import me.michalkasza.smartlock.data.repository.LocksRepository
import java.util.ArrayList

class LocksListViewModel(baseView: BaseView, app: Application): BaseViewModel(app), LocksListInterface.UserInteractions {
    val view = baseView as LocksListInterface.View

    val adapterObservable = ObservableField<LocksAdapter>()
    val layoutManagerObservable = ObservableField<androidx.recyclerview.widget.GridLayoutManager>()

    val locks = arrayListOf<Lock>()

    init {
        adapterObservable.set(LocksAdapter(locks))
        layoutManagerObservable.set(view.getGridLayoutManager())
    }

    override fun locksChanged(locks: List<Lock>) {
        this.locks.clear()
        this.locks.addAll(locks)
        adapterObservable.get()?.notifyDataSetChanged()
    }

    override fun initNewLock() {

    }

    override fun removeLock() {

    }

    override fun addLockClicked(componentView: View) {
        view.showNewLockDialog()
    }
}