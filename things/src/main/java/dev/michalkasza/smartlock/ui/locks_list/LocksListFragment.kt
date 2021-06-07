package dev.michalkasza.smartlock.ui.locks_list

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dev.michalkasza.smartlock.R
import dev.michalkasza.smartlock.base.BaseFragment
import dev.michalkasza.smartlock.data.repository.LocksRepository
import dev.michalkasza.smartlock.databinding.FragmentLockslistBinding
import dev.michalkasza.smartlock.ui.MainActivity
import dev.michalkasza.smartlock.ui.components.ViewModelFactory


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

    override fun initNewLock() {
        mainActivity.initializeNewLock()
    }

    companion object {
        val TAG = LocksListFragment::class.java.simpleName
    }
}