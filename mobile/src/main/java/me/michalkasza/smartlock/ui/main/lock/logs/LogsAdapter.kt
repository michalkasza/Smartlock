package me.michalkasza.smartlock.ui.lock.logs

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.vipulasri.timelineview.TimelineView
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.data.model.LogEntry
import me.michalkasza.smartlock.databinding.ItemLogBinding
import java.text.SimpleDateFormat
import java.util.*

class LogsAdapter(var logs: ArrayList<LogEntry>?) : RecyclerView.Adapter<LogsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false)
        return ViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        logs?.get(position)?.let { logEntry -> holder.bindView(logEntry) }
    }

    override fun getItemCount(): Int {
        return logs?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    class ViewHolder(val view: View, val viewType: Int) : RecyclerView.ViewHolder(view) {
        lateinit var logEntry: LogEntry
        val accessDateTime = ObservableField<String>()
        val accessUser = ObservableField<String>()

        fun bindView(logEntry: LogEntry) {
            accessDateTime.set(SimpleDateFormat("dd.MM.yyyy, HH:mm:ss", Locale.US).format(logEntry.accessTime))
            accessUser.set(logEntry.userId)
            val binding = DataBindingUtil.bind<ItemLogBinding>(view)
            view.findViewById<TimelineView>(R.id.tlv_timeline).initLine(viewType)
            this.logEntry= logEntry
            binding?.log = logEntry
            binding?.viewHolder = this
            binding?.executePendingBindings()
        }
    }
}