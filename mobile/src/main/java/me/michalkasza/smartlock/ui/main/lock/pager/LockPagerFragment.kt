package me.michalkasza.smartlock.ui.lock.pager

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lock.*
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseFragment
import me.michalkasza.smartlock.databinding.FragmentLockBinding
import me.michalkasza.smartlock.ui.main.MainActivity
import me.michalkasza.smartlock.ui.components.ViewModelFactory

class LockPagerFragment : BaseFragment(), LockPagerInterface.View {
    private lateinit var lockViewModel: LockPagerViewModel
    private val activity: MainActivity by lazy { getActivity() as MainActivity }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentLockBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_lock,
                container,
                false)

        lockViewModel = ViewModelProviders.of(this, ViewModelFactory(this, activity.application)).get(LockPagerViewModel::class.java)
        viewBinding.viewModel = lockViewModel

        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        initTabLayout()
    }

    private fun initTabLayout() {
        tl_lock.setupWithViewPager(view_pager)
    }

    companion object {
        val TAG = LockPagerFragment::class.java.simpleName
    }
}