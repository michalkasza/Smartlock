package dev.michalkasza.smartlock.ui.locks_list

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SwitchCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.michalkasza.smartlock.R
import dev.michalkasza.smartlock.data.model.Lock
import dev.michalkasza.smartlock.databinding.ItemLockBinding
import java.util.*

class LocksAdapter(var locks: ArrayList<Lock>?) : RecyclerView.Adapter<LocksAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_lock, parent, false)
        return ViewHolder(view, viewType)
    }

    override fun getItemCount(): Int {
        return locks?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        locks?.get(position)?.let { logEntry -> holder.bindView(logEntry) }
    }

    class ViewHolder(val view: View, val viewType: Int) : RecyclerView.ViewHolder(view) {
        lateinit var lock: Lock
        lateinit var lockSwitchView: SwitchCompat

        fun bindView(lock: Lock) {
            val binding = DataBindingUtil.bind<ItemLockBinding>(view)
            this.lock= lock
            this.lockSwitchView = view.findViewById(R.id.sw_status)
            binding?.lock = lock
            binding?.viewHolder = this
            binding?.executePendingBindings()
        }

        fun lockStatusChanged(isLocked: Boolean) {
            lockSwitchView.isChecked = isLocked
        }
    }
}