package dev.michalkasza.smartlock.ui.components.databinding

import androidx.databinding.BindingAdapter
import dev.michalkasza.smartlock.ui.locks_list.LocksAdapter

object RecyclerViewBinding {
    @JvmStatic
    @BindingAdapter(value = ["locksRecyclerAdapter", "locksLayoutManager"], requireAll = true)
    fun setLocksRecyclerAdapter(recyclerView: androidx.recyclerview.widget.RecyclerView, adapter: LocksAdapter, layoutManager: androidx.recyclerview.widget.GridLayoutManager) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
}