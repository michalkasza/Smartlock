package me.michalkasza.smartlock.ui.components.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import me.michalkasza.smartlock.ui.MainNavDrawerAdapter
import me.michalkasza.smartlock.ui.lock.status.logs.LogsAdapter

object RecyclerViewBinding {
    @JvmStatic
    @BindingAdapter(value = ["navRecyclerAdapter"], requireAll = true)
    fun setNavigationRecyclerAdapter(recyclerView: RecyclerView, adapter: MainNavDrawerAdapter) {
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter(value = ["logsRecyclerAdapter"], requireAll = true)
    fun setLogsRecyclerAdapter(recyclerView: RecyclerView, adapter: LogsAdapter) {
        recyclerView.adapter = adapter
    }
}