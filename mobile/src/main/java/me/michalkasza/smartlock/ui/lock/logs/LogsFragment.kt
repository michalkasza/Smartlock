package me.michalkasza.smartlock.ui.lock.logs

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_logs.*
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseFragment
import me.michalkasza.smartlock.data.repository.LogsRepository
import me.michalkasza.smartlock.databinding.FragmentLogsBinding
import me.michalkasza.smartlock.ui.MainActivity
import me.michalkasza.smartlock.ui.components.ViewModelFactory

class LogsFragment: BaseFragment(), LogsInterface.View {
    override val familiarName = "Logs"
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
        rv_logs.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        observeLockLogs()
    }

    private fun observeLockLogs() = LogsRepository.currentLockLogs.observe(this, Observer { logs ->
        logs?.let { logsViewModel.logsChanged(logs) }
    })

}