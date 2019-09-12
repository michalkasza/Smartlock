package me.michalkasza.smartlock.ui.main

import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.repository.LocksRepository
import me.michalkasza.smartlock.databinding.ItemLockBinding
import me.michalkasza.smartlock.ui.lock.pager.LockPagerFragment
import me.michalkasza.smartlock.utils.FragmentFlowUtils

class MainNavDrawerAdapter(var locks: ArrayList<Lock>?, var supportFragmentManager: FragmentManager) : androidx.recyclerview.widget.RecyclerView.Adapter<MainNavDrawerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_lock, parent, false)
        return ViewHolder(view, supportFragmentManager)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        locks?.get(position)?.let { lock -> holder.bindView(lock) }
    }

    override fun getItemCount(): Int {
        return locks?.size ?: 0
    }

    class ViewHolder(val view: View, val supportFragmentManager: FragmentManager) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
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
            FragmentFlowUtils.replaceFragment(supportFragmentManager, LockPagerFragment(), LockPagerFragment.TAG, false, false)
            isSelected.set(true)
        }
    }
}