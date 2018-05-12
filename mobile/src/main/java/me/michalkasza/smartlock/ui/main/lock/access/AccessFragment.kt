package me.michalkasza.smartlock.ui.lock.access

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseFragment
import me.michalkasza.smartlock.data.repository.LocksRepository
import me.michalkasza.smartlock.data.repository.UsersRepository
import me.michalkasza.smartlock.databinding.FragmentAccessBinding
import me.michalkasza.smartlock.ui.main.MainActivity
import me.michalkasza.smartlock.ui.components.ViewModelFactory

class AccessFragment: BaseFragment(), AccessInterface.View {
    override val familiarName = "Access"
    private lateinit var accessViewModel: AccessViewModel
    private val mainActivity: MainActivity by lazy { activity as MainActivity }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentAccessBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_access,
                container,
                false)

        accessViewModel = ViewModelProviders.of(this, ViewModelFactory(this, activity!!.application)).get(AccessViewModel::class.java)
        viewBinding.viewModel = accessViewModel

        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        observeCurrentLock()
        observeCurrentLockAccessedUsers()
    }

    private fun observeCurrentLock() = LocksRepository.currentLock.observe(this, Observer { lock ->
        lock?.let { accessViewModel.lockChanged(lock) }
    })

    private fun observeCurrentLockAccessedUsers() = UsersRepository.currentLockAccessedUsers.observe(this, Observer { users ->
        users?.let { accessViewModel.accessedUsersChanged(users) }
    })
}