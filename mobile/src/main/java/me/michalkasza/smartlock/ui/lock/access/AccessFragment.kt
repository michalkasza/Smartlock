package me.michalkasza.smartlock.ui.lock.access

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseFragment
import me.michalkasza.smartlock.databinding.FragmentAccessBinding

class AccessFragment: BaseFragment() {
    override val familiarName: String?
        get() = "Access"
    private lateinit var accessViewModel: AccessViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentAccessBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_access,
                container,
                false)

        accessViewModel = ViewModelProviders.of(this).get(AccessViewModel::class.java)
        viewBinding.viewModel = accessViewModel

        return viewBinding.root
    }
}