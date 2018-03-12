package me.michalkasza.smartlock.ui.lock.status

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseFragment
import me.michalkasza.smartlock.data.model.LogEntry
import me.michalkasza.smartlock.data.repository.LocksRepository
import me.michalkasza.smartlock.data.repository.LogsRepository
import me.michalkasza.smartlock.databinding.FragmentStatusBinding
import me.michalkasza.smartlock.ui.MainActivity
import me.michalkasza.smartlock.ui.components.ViewModelFactory
import me.michalkasza.smartlock.ui.lock.status.logs.LogsBottomsheet
import java.util.*

class StatusFragment: BaseFragment(), StatusInterface.View {
    override val familiarName = "Status"
    private lateinit var statusViewModel: StatusViewModel
    private val bottomsheet: LogsBottomsheet by lazy { LogsBottomsheet() }
    private val mainActivity: MainActivity by lazy { activity as MainActivity }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding: FragmentStatusBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_status,
                container,
                false)

        statusViewModel = ViewModelProviders.of(this, ViewModelFactory(this, activity!!.application)).get(StatusViewModel::class.java)
        viewBinding.viewModel = statusViewModel

        initStatusBottomsheet()

        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        observeCurrentLock()
        observeLockLogs()
    }

    fun initStatusBottomsheet() {
        mainActivity.initStatusBottomsheet(bottomsheet)
    }

    private fun observeCurrentLock() = LocksRepository.currentLock.observe(this, Observer { lock ->
        lock?.let { statusViewModel.lockChanged(lock) }
    })

    private fun observeLockLogs() = LogsRepository.currentLockLogs.observe(this, Observer { logs ->
        logs?.let { statusViewModel.logsChanged(logs) }
    })

    override fun updateLogs(logs: ArrayList<LogEntry>) {
        bottomsheet.logsChanged(logs)
    }

    override fun updateLastAccess(dateTime: Date, username: String) {
        bottomsheet.setLastAccessUsername(username)
        bottomsheet.setLastAccessDateTime(dateTime)
    }

    companion object {
        val TAG = StatusFragment::class.java.simpleName
    }
}