package me.michalkasza.smartlock.ui.lock

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lock.*
import kotlinx.android.synthetic.main.main_navdrawer.*
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseFragment
import me.michalkasza.smartlock.data.repository.LocksRepository
import me.michalkasza.smartlock.databinding.FragmentLockBinding
import me.michalkasza.smartlock.ui.components.ViewModelFactory

class LockFragment: BaseFragment(), LockInterface.View {
    private lateinit var lockViewModel: LockViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentLockBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_lock,
                container,
                false)

        lockViewModel = ViewModelProviders.of(this, ViewModelFactory(this, activity!!.application)).get(LockViewModel::class.java)
        viewBinding.viewModel = lockViewModel

        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        initTabLayout()
    }

    private fun observeCurrentLock() = LocksRepository.currentLock.observe(this, Observer { lock ->

    })

    private fun initTabLayout() {
        tl_lock.setupWithViewPager(view_pager)
    }

    companion object {
        val TAG = LockFragment::class.java.simpleName
    }
}