package dev.michalkasza.smartlock.ui.locks_list

import android.app.Application
import androidx.databinding.ObservableField
import dev.michalkasza.smartlock.base.BaseView
import dev.michalkasza.smartlock.base.BaseViewModel
import dev.michalkasza.smartlock.data.model.Lock

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

    override fun addLockClicked() {
        view.initNewLock()
    }
}