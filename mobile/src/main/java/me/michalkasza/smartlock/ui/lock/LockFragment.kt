package me.michalkasza.smartlock.ui.lock

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lock.*
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseFragment
import me.michalkasza.smartlock.databinding.FragmentLockBinding
import me.michalkasza.smartlock.ui.components.ViewModelFactory

class LockFragment: BaseFragment(), LockInterface.View {
    override val familiarName: String?
        get() = "Lock"
    private lateinit var lockViewModel: LockViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentLockBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_lock,
                container,
                false)

        lockViewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(LockViewModel::class.java)
        viewBinding.viewModel = lockViewModel

        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        initTabLayout()
    }

    private fun initTabLayout() {
        tl_profile.setupWithViewPager(view_pager)
    }

    companion object {
        val TAG = LockFragment::class.java.simpleName
    }
}