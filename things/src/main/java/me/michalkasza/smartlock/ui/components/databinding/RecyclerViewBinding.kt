package me.michalkasza.smartlock.ui.components.databinding

import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import me.michalkasza.smartlock.ui.locks_list.LocksAdapter

object RecyclerViewBinding {
    @JvmStatic
    @BindingAdapter(value = ["logsRecyclerAdapter", "logsLayoutManager"], requireAll = true)
    fun setLogsRecyclerAdapter(recyclerView: RecyclerView, adapter: LocksAdapter, layoutManager: LinearLayoutManager) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
}