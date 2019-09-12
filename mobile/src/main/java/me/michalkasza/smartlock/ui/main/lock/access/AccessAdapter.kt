package me.michalkasza.smartlock.ui.lock.access

import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.data.model.User
import me.michalkasza.smartlock.data.repository.LocksRepository
import me.michalkasza.smartlock.databinding.ItemAccessBinding
import java.util.*

class AccessAdapter(var accessList: ArrayList<User>?) : androidx.recyclerview.widget.RecyclerView.Adapter<AccessAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_access, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        accessList?.get(position)?.let { user -> holder.bindView(user) }
    }

    override fun getItemCount(): Int {
        return accessList?.size ?: 0
    }

    class ViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        lateinit var accessedUser: User
        val isAdministrator = ObservableField<Boolean>()
        val accessUsername = ObservableField<String>()

        fun bindView(accessedUser: User) {
            this.accessedUser = accessedUser
            accessUsername.set(StringBuilder(accessedUser.name).append(" ").append(accessedUser.surname).toString())
            LocksRepository.currentLock.value?.ownerId?.let { lockOwnerId ->
                if(lockOwnerId.equals(accessedUser.id))
                    isAdministrator.set(true)
            }
            val binding = DataBindingUtil.bind<ItemAccessBinding>(view)

            binding?.accessedUser = accessedUser
            binding?.viewHolder = this
            binding?.executePendingBindings()
        }
    }
}