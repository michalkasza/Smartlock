package me.michalkasza.smartlock.ui.lock.logs

import android.app.Application
import android.databinding.ObservableField
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel
import me.michalkasza.smartlock.data.model.LogEntry
import java.util.*

class LogsViewModel(baseView: BaseView, app: Application): BaseViewModel(app), LogsInterface.UserInteractions {
    val adapterObservable = ObservableField<LogsAdapter>()

    val logs = ArrayList<LogEntry>()

    init {
        adapterObservable.set(LogsAdapter(logs))
    }

    override fun logsChanged(logs: ArrayList<LogEntry>) {
        this.logs.clear()
        this.logs.addAll(logs.sortedWith(compareBy({ log -> log.accessTime })).asReversed())
        adapterObservable.get()?.notifyDataSetChanged()
    }

    companion object {
        val TAG = LogsViewModel::class.java.simpleName
    }
}