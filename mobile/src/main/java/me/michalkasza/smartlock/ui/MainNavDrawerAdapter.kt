package me.michalkasza.smartlock.ui

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.databinding.ItemLockBinding

class MainNavDrawerAdapter(private var context: Context, private var locks: List<Lock>?) : RecyclerView.Adapter<MainNavDrawerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_lock, parent, false)
        val binding = DataBindingUtil.bind<ItemLockBinding>(view)
        return ViewHolder(context, binding!!)
    }

    override fun getItemCount(): Int {
        return locks?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        locks?.get(position)?.let { lock -> holder?.bindView(lock) }
    }

    class ViewHolder(val context: Context, val binding: ItemLockBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(lock: Lock) {
            binding.lock = lock
            binding.viewHolder = this
        }
    }
}