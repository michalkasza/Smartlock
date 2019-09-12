package me.michalkasza.smartlock.ui.components.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.michalkasza.smartlock.ui.locks_list.LocksAdapter

object RecyclerViewBinding {
    @JvmStatic
    @BindingAdapter(value = ["locksRecyclerAdapter", "locksLayoutManager"], requireAll = true)
    fun setLocksRecyclerAdapter(recyclerView: androidx.recyclerview.widget.RecyclerView, adapter: LocksAdapter, layoutManager: androidx.recyclerview.widget.GridLayoutManager) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
}