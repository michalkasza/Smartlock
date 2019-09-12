package me.michalkasza.smartlock.ui.components.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.michalkasza.smartlock.ui.main.MainNavDrawerAdapter
import me.michalkasza.smartlock.ui.lock.access.AccessAdapter
import me.michalkasza.smartlock.ui.lock.logs.LogsAdapter

object RecyclerViewBinding {
    @JvmStatic
    @BindingAdapter(value = ["navRecyclerAdapter"], requireAll = true)
    fun setNavigationRecyclerAdapter(recyclerView: androidx.recyclerview.widget.RecyclerView, adapter: MainNavDrawerAdapter) {
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter(value = ["logsRecyclerAdapter", "logsLayoutManager"], requireAll = true)
    fun setLogsRecyclerAdapter(recyclerView: androidx.recyclerview.widget.RecyclerView, adapter: LogsAdapter, layoutManager: androidx.recyclerview.widget.LinearLayoutManager) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }

    @JvmStatic
    @BindingAdapter(value = ["accessRecyclerAdapter", "accessLayoutManager"], requireAll = true)
    fun setLogsRecyclerAdapter(recyclerView: androidx.recyclerview.widget.RecyclerView, adapter: AccessAdapter, layoutManager: androidx.recyclerview.widget.LinearLayoutManager) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
}