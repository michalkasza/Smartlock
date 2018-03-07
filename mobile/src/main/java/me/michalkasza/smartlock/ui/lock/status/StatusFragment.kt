package me.michalkasza.smartlock.ui.lock.status

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseFragment
import me.michalkasza.smartlock.databinding.FragmentStatusBinding
import me.michalkasza.smartlock.ui.components.ViewModelFactory

class StatusFragment: BaseFragment() {
    override val familiarName = "Status"
    private lateinit var statusViewModel: StatusViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentStatusBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_status,
                container,
                false)

        statusViewModel = ViewModelProviders.of(this, ViewModelFactory(this, activity!!.application)).get(StatusViewModel::class.java)
        viewBinding.viewModel = statusViewModel

        return viewBinding.root
    }

    companion object {
        val TAG = StatusFragment::class.java.simpleName
    }
}