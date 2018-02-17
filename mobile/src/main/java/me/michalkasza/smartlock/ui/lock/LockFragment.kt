package me.michalkasza.smartlock.ui.lock

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.databinding.FragmentLockBinding

class LockFragment: Fragment() {
    private lateinit var lockViewModel: LockViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentLockBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_lock_status,
                container,
                false)

        lockViewModel = ViewModelProviders.of(this).get(LockViewModel::class.java)
        viewBinding.viewModel = lockViewModel

        return viewBinding.root
    }


}