package dev.michalkasza.smartlock.ui.main.lock.logs

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.michalkasza.smartlock.R
import dev.michalkasza.smartlock.base.BaseFragment
import dev.michalkasza.smartlock.data.repository.LogsRepository
import dev.michalkasza.smartlock.databinding.FragmentLogsBinding
import dev.michalkasza.smartlock.ui.main.MainActivity
import dev.michalkasza.smartlock.ui.components.ViewModelFactory

class LogsFragment: BaseFragment(), LogsInterface.View {
    private lateinit var logsViewModel: LogsViewModel
    private val mainActivity: MainActivity by lazy { activity as MainActivity }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentLogsBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_logs,
                container,
                false)

        logsViewModel = ViewModelProviders.of(this, ViewModelFactory(this, activity!!.application)).get(LogsViewModel::class.java)
        viewBinding.viewModel = logsViewModel

        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        observeLockLogs()
    }

    private fun observeLockLogs() = LogsRepository.currentLockLogs.observe(this, Observer { logs ->
        logs?.let { logsViewModel.logsChanged(logs) }
    })

}