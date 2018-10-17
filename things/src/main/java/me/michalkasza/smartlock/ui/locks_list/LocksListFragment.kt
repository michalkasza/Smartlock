package me.michalkasza.smartlock.ui.locks_list

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.ui.MainActivity
import me.michalkasza.smartlock.ui.components.ViewModelFactory


class LocksListFragment: Fragment(), LocksListInterface.View {
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

    override fun showNewLockDialog() {

    }
}