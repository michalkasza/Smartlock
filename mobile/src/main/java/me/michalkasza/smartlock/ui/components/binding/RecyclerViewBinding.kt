package me.michalkasza.smartlock.ui.components.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import me.michalkasza.smartlock.ui.main.MainNavDrawerAdapter
import me.michalkasza.smartlock.ui.lock.access.AccessAdapter
import me.michalkasza.smartlock.ui.lock.logs.LogsAdapter

object RecyclerViewBinding {
    @JvmStatic
    @BindingAdapter(value = ["navRecyclerAdapter"], requireAll = true)
    fun setNavigationRecyclerAdapter(recyclerView: RecyclerView, adapter: MainNavDrawerAdapter) {
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter(value = ["logsRecyclerAdapter", "logsLayoutManager"], requireAll = true)
    fun setLogsRecyclerAdapter(recyclerView: RecyclerView, adapter: LogsAdapter, layoutManager: LinearLayoutManager) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }

    @JvmStatic
    @BindingAdapter(value = ["accessRecyclerAdapter", "accessLayoutManager"], requireAll = true)
    fun setLogsRecyclerAdapter(recyclerView: RecyclerView, adapter: AccessAdapter, layoutManager: LinearLayoutManager) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
}