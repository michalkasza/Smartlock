package me.michalkasza.smartlock.ui

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.repository.LocksRepository
import me.michalkasza.smartlock.databinding.ItemLockBinding

class MainNavDrawerAdapter(var locks: ArrayList<Lock>?) : RecyclerView.Adapter<MainNavDrawerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_lock, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        locks?.get(position)?.let { lock -> holder.bindView(lock) }
    }

    override fun getItemCount(): Int {
        return locks?.size ?: 0
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        lateinit var lock: Lock
        val isSelected = ObservableField<Boolean>()

        fun bindView(lock: Lock) {
            isSelected.set(false)
            val binding = DataBindingUtil.bind<ItemLockBinding>(view)
            this.lock = lock
            binding?.lock = lock
            binding?.viewHolder = this
            binding?.executePendingBindings()
        }

        fun onLockClicked(view: View) {
            LocksRepository.currentLock.value = lock
            isSelected.set(true)
        }
    }
}