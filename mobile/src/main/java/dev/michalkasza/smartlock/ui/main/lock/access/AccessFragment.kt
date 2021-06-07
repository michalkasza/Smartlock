package dev.michalkasza.smartlock.ui.main.lock.access

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.michalkasza.smartlock.R
import dev.michalkasza.smartlock.base.BaseFragment
import dev.michalkasza.smartlock.data.repository.LocksRepository
import dev.michalkasza.smartlock.data.repository.UsersRepository
import dev.michalkasza.smartlock.databinding.FragmentAccessBinding
import dev.michalkasza.smartlock.ui.main.MainActivity
import dev.michalkasza.smartlock.ui.components.ViewModelFactory
import dev.michalkasza.smartlock.ui.main.lock.access.grant_dialog.GrantAccessDialogFragment

class AccessFragment: BaseFragment(), AccessInterface.View {
    private lateinit var accessViewModel: AccessViewModel
    private val mainActivity: MainActivity by lazy { activity as MainActivity }
    private var currentLockId: String? = null

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

    override fun showGrantUserDialog() {
        val ft = fragmentManager!!.beginTransaction()
        val prev = fragmentManager!!.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)

        val newFragment = GrantAccessDialogFragment.newInstance(currentLockId)
        newFragment.show(ft, "dialog")
    }

    private fun observeCurrentLock() = LocksRepository.currentLock.observe(this, Observer { lock ->
        currentLockId = lock.id
        lock?.let { accessViewModel.lockChanged(lock) }
    })

    private fun observeCurrentLockAccessedUsers() = UsersRepository.currentLockAccessedUsers.observe(this, Observer { users ->
        users?.let { accessViewModel.accessedUsersChanged(users) }
    })
}