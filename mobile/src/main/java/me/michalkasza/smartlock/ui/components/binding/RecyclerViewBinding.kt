package me.michalkasza.smartlock.ui.components.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import me.michalkasza.smartlock.ui.MainNavDrawerAdapter
import me.michalkasza.smartlock.ui.lock.access.AccessAdapter
import me.michalkasza.smartlock.ui.lock.logs.LogsAdapter

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

    @JvmStatic
    @BindingAdapter(value = ["accessRecyclerAdapter"], requireAll = true)
    fun setLogsRecyclerAdapter(recyclerView: RecyclerView, adapter: AccessAdapter) {
        recyclerView.adapter = adapter
    }
}