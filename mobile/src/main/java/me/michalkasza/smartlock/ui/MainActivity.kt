package me.michalkasza.smartlock.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseActivity
import me.michalkasza.smartlock.ui.lock.status.bottomsheet.StatusBottomsheet
import me.michalkasza.smartlock.ui.lock.LockFragment
import me.michalkasza.smartlock.utils.FragmentFlowUtils
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle

class MainActivity : BaseActivity() {
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initToolbar()
        initDrawerLayout()
        initFragment()
        initStatusBottomsheet(StatusBottomsheet())
    }

    private fun initFragment() {
        FragmentFlowUtils.replaceFragment(supportFragmentManager, LockFragment(), LockFragment.TAG, false, false)
    }

    private fun initStatusBottomsheet(statusBottomsheet: StatusBottomsheet) {
        fl_bottomsheet_container.removeAllViews()
        fl_bottomsheet_container.addView(statusBottomsheet.getInflatedView(layoutInflater))
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initDrawerLayout() {
        val drawerToggle = DuoDrawerToggle(this,  drawer, toolbar, R.string.drawer_opened_tag, R.string.drawer_closed_tag)
        drawer.setDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }
}
