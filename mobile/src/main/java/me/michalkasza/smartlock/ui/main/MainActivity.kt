package me.michalkasza.smartlock.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.main_navdrawer.*
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseActivity
import me.michalkasza.smartlock.data.repository.LocksRepository
import me.michalkasza.smartlock.data.repository.UsersRepository
import me.michalkasza.smartlock.ui.components.ViewModelFactory
import me.michalkasza.smartlock.utils.FragmentFlowUtils
import me.michalkasza.smartlock.databinding.ActivityMainBinding
import me.michalkasza.smartlock.ui.home.HomeFragment
import me.michalkasza.smartlock.ui.lock.BLEScanner
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle
import java.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.R.attr.data
import android.app.Activity
import com.firebase.ui.auth.IdpResponse



class MainActivity : BaseActivity(), MainInterface.View {
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(this, application)).get(MainViewModel::class.java)

        val viewBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewBinding.viewModel = mainViewModel

        rv_locks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        signIn()
        initToolbar()
        initDrawerLayout()
        initFragment()
        BLEScanner()

        UsersRepository.getUser("jQ3SygKyWeeYbJmLsuaInQcEZFA3")
    }

    fun signIn(){
        val providers = Arrays.asList(
                AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build())

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode === Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
            } else { }
        }
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

    override fun setToolbarTitle(title: String) {
        toolbar.title = title
    }

    override fun setSmartlockToolbarTitle(title: String?) {
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

    companion object {
        val RC_SIGN_IN = 123;
    }
}
