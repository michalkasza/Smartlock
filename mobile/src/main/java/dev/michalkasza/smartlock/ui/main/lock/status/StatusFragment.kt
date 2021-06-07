package dev.michalkasza.smartlock.ui.main.lock.status

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
import dev.michalkasza.smartlock.databinding.FragmentStatusBinding
import dev.michalkasza.smartlock.ui.main.MainActivity
import dev.michalkasza.smartlock.ui.components.ViewModelFactory

class StatusFragment: BaseFragment(), StatusInterface.View {
    private lateinit var statusViewModel: StatusViewModel
    private val mainActivity: MainActivity by lazy { activity as MainActivity }

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

    override fun onResume() {
        super.onResume()
        observeCurrentLock()
    }

    private fun observeCurrentLock() = LocksRepository.currentLock.observe(this, Observer { lock ->
        lock?.let { statusViewModel.lockChanged(lock) }
    })

    companion object {
        val TAG = StatusFragment::class.java.simpleName
    }
}