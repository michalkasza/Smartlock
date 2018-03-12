package me.michalkasza.smartlock.ui.lock.status.logs

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.design.widget.BottomSheetBehavior
import android.view.LayoutInflater
import android.view.View
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.data.model.LogEntry
import me.michalkasza.smartlock.databinding.BottomsheetLogsBinding
import java.text.SimpleDateFormat
import java.util.*

class LogsBottomsheet {
    val lastAccessUsername = ObservableField<String>()
    val lastAccessDate = ObservableField<String>()
    val lastAccessTime = ObservableField<String>()
    val adapterObservable = ObservableField<LogsAdapter>()

    val logs = ArrayList<LogEntry>()

    init {
        adapterObservable.set(LogsAdapter(logs))
    }

    fun getInflatedView(layoutInflater: LayoutInflater, bottomSheetContainer: View): View {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottomsheet_logs, null, false)
        bottomSheetView.addOnLayoutChangeListener({view, i1, i2, i3, i4, i5, i6, i7, i8 ->
            DataBindingUtil.bind<BottomsheetLogsBinding>(view)?.let { binding ->
                binding.bottomSheetView = this
                BottomSheetBehavior.from(bottomSheetContainer).peekHeight = binding.clBottomsheetHeader.height
            }
        })

        return bottomSheetView
    }

    fun setLastAccessUsername(username: String) {
        lastAccessUsername.set(username)
    }

    fun setLastAccessDateTime(dateTime: Date) {
        lastAccessDate.set(SimpleDateFormat("dd.MM.yyyy", Locale.US).format(dateTime))
        lastAccessTime.set(SimpleDateFormat("HH:mm:ss", Locale.US).format(dateTime))
    }

    fun logsChanged(logs: ArrayList<LogEntry>) {
        this.logs.clear()
        this.logs.addAll(logs.sortedWith(compareBy({ log -> log.accessTime })).asReversed())
        adapterObservable.get()?.notifyDataSetChanged()
    }

    companion object {
        val TAG = LogsBottomsheet::class.java.simpleName
    }
}