package me.michalkasza.smartlock.ui.components.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import me.michalkasza.smartlock.ui.MainNavDrawerAdapter

object RecyclerViewBinding {
    @JvmStatic
    @BindingAdapter(value = ["navRecyclerAdapter"], requireAll = true)
    fun setNavigationRecyclerAdapter(recyclerView: RecyclerView, adapter: MainNavDrawerAdapter) {
        recyclerView.adapter = adapter
    }
}