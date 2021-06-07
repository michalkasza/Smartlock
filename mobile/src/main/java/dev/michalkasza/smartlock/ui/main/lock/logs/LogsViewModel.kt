package dev.michalkasza.smartlock.ui.main.lock.logs

import android.app.Application
import androidx.databinding.ObservableField
import dev.michalkasza.smartlock.base.BaseView
import dev.michalkasza.smartlock.base.BaseViewModel
import dev.michalkasza.smartlock.data.model.LogEntry
import java.util.*

class LogsViewModel(baseView: BaseView, app: Application): BaseViewModel(app), LogsInterface.UserInteractions {
    val view = baseView as LogsInterface.View

    val adapterObservable = ObservableField<LogsAdapter>()
    val layoutManagerObservable = ObservableField<androidx.recyclerview.widget.LinearLayoutManager>()

    val logs = ArrayList<LogEntry>()

    init {
        adapterObservable.set(LogsAdapter(logs))
        layoutManagerObservable.set(view.getLinearVerticalLayoutManager())
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