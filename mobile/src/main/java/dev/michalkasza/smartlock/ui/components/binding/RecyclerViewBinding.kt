package dev.michalkasza.smartlock.ui.components.binding

import androidx.databinding.BindingAdapter
import dev.michalkasza.smartlock.ui.main.MainNavDrawerAdapter
import dev.michalkasza.smartlock.ui.main.lock.access.AccessAdapter
import dev.michalkasza.smartlock.ui.main.lock.logs.LogsAdapter

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