package me.michalkasza.smartlock.ui.main

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseActivity
import me.michalkasza.smartlock.data.repository.LocksRepository
import me.michalkasza.smartlock.data.repository.UsersRepository
import me.michalkasza.smartlock.ui.components.ViewModelFactory
import me.michalkasza.smartlock.utils.FragmentFlowUtils
import me.michalkasza.smartlock.databinding.ActivityMainBinding
import me.michalkasza.smartlock.ui.home.HomeFragment
import me.michalkasza.smartlock.ui.lock.BLEScanner
import me.michalkasza.smartlock.ui.splash.SplashActivity
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.main_navdrawer.*

class MainActivity : BaseActivity(), MainInterface.View {
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(this, application)).get(MainViewModel::class.java)

        val viewBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewBinding.viewModel = mainViewModel

        rv_locks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        initToolbar()
        initDrawerLayout()
        initFragment()
        BLEScanner()
    }

    override fun onResume() {
        super.onResume()
        observeCurrentUser()
        observeLocks()
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
        FragmentFlowUtils.replaceFragment(supportFragmentManager, HomeFragment(), HomeFragment.TAG, false, false)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    override fun setAppToolbarTitle(title: String?) {
        toolbar.title = title
    }

    override fun setAppToolbarTitle(titleResourceId: Int) {
        toolbar.title = getString(titleResourceId)
    }

    override fun logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    startActivity(Intent(this, SplashActivity::class.java))
                    finish()
                }
    }

    private fun initDrawerLayout() {
        val drawerToggle = DuoDrawerToggle(this,  drawer, toolbar, R.string.drawer_opened_tag, R.string.drawer_closed_tag)
        drawer.setDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }
}
