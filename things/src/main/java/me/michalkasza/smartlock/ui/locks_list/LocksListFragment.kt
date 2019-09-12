package me.michalkasza.smartlock.ui.locks_list

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseFragment
import me.michalkasza.smartlock.data.repository.LocksRepository
import me.michalkasza.smartlock.databinding.FragmentLockslistBinding
import me.michalkasza.smartlock.ui.MainActivity
import me.michalkasza.smartlock.ui.components.ViewModelFactory


class LocksListFragment: BaseFragment(), LocksListInterface.View {
    private lateinit var locksListViewModel: LocksListViewModel
    private val mainActivity: MainActivity by lazy { activity as MainActivity }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentLockslistBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_lockslist,
                container,
                false)

        locksListViewModel = ViewModelProviders.of(this, ViewModelFactory(this, activity!!.application)).get(LocksListViewModel::class.java)
        viewBinding.viewModel = locksListViewModel

        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        observeLocksList()
    }

    private fun observeLocksList() = LocksRepository.locks.observe(this, Observer { locks ->
        locks?.let { locksListViewModel.locksChanged(locks) }
    })

    override fun showNewLockDialog() {

    }

    companion object {
        val TAG = LocksListFragment::class.java.simpleName
    }
}