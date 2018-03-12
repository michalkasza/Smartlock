package me.michalkasza.smartlock.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottomsheet_logs.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.main_content.view.*
import kotlinx.android.synthetic.main.main_navdrawer.*
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseActivity
import me.michalkasza.smartlock.data.repository.LocksRepository
import me.michalkasza.smartlock.data.repository.UsersRepository
import me.michalkasza.smartlock.ui.components.ViewModelFactory
import me.michalkasza.smartlock.ui.lock.pager.LockPagerFragment
import me.michalkasza.smartlock.ui.lock.status.logs.LogsBottomsheet
import me.michalkasza.smartlock.utils.FragmentFlowUtils
import me.michalkasza.smartlock.databinding.ActivityMainBinding
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle

class MainActivity : BaseActivity(), MainInterface.View {
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(this, application)).get(MainViewModel::class.java)

        val viewBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewBinding.viewModel = mainViewModel

        initToolbar()
        initDrawerLayout()
        initFragment()

        UsersRepository.getUser("jQ3SygKyWeeYbJmLsuaInQcEZFA3")
    }

    override fun onResume() {
        super.onResume()
        observeCurrentUser()
        observeLocks()
        rv_locks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun observeCurrentUser() = UsersRepository.currentUser.observe(this, Observer { currentUser ->
        currentUser?.let {
            mainViewModel.userChanged(currentUser)
            LocksRepository.getLocks(currentUser)
        }
    })

    private fun observeLocks() = LocksRepository.userLocks.observe(this, Observer { userLocks ->
        userLocks?.let { mainViewModel.locksChanged(userLocks) }
    })

    private fun initFragment() {
        FragmentFlowUtils.replaceFragment(supportFragmentManager, LockPagerFragment(), LockPagerFragment.TAG, false, false)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    fun initStatusBottomsheet(bottomsheet: LogsBottomsheet) {
        fl_bottomsheet_container.fl_bottomsheet_container.removeAllViews()
        fl_bottomsheet_container.addView(bottomsheet.getInflatedView(layoutInflater, fl_bottomsheet_container))
        rv_logs.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_logs.isNestedScrollingEnabled = true
    }

    override fun setToolbarTitle(title: String) {
        toolbar.title = title
    }

    override fun setToolbarTitle(titleResourceId: Int) {
        toolbar.title = getString(titleResourceId)
    }

    private fun initDrawerLayout() {
        val drawerToggle = DuoDrawerToggle(this,  drawer, toolbar, R.string.drawer_opened_tag, R.string.drawer_closed_tag)
        drawer.setDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }
}
