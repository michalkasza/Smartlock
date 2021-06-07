package dev.michalkasza.smartlock.ui.main

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import dev.michalkasza.smartlock.R
import dev.michalkasza.smartlock.base.BaseActivity
import dev.michalkasza.smartlock.data.repository.LocksRepository
import dev.michalkasza.smartlock.data.repository.UsersRepository
import dev.michalkasza.smartlock.ui.components.ViewModelFactory
import dev.michalkasza.smartlock.utils.FragmentFlowUtils
import dev.michalkasza.smartlock.databinding.ActivityMainBinding
import dev.michalkasza.smartlock.ui.main.home.HomeFragment
import dev.michalkasza.smartlock.ui.splash.SplashActivity
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.main_navdrawer.*
import com.polidea.rxandroidble2.RxBleClient
import android.bluetooth.BluetoothAdapter
import android.util.Log
import com.polidea.rxandroidble2.scan.ScanSettings
import android.content.pm.PackageManager
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.AlertDialog
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.polidea.rxandroidble2.internal.RxBleLog

class MainActivity : BaseActivity(), MainInterface.View {
    private lateinit var mainViewModel : MainViewModel
    val MY_PERMISSIONS_REQUEST_LOCATION = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(this, application)).get(MainViewModel::class.java)

        val viewBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewBinding.viewModel = mainViewModel

        rv_locks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        initToolbar()
        initDrawerLayout()
        initFragment()
        checkLocationPermission()
    }

    override fun onResume() {
        super.onResume()
        observeCurrentUser()
        observeLocks()
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            scanBLE()
        }
    }

    private fun scanBLE(){
        val rxBleClient = RxBleClient.create(this)
        RxBleClient.setLogLevel(RxBleLog.DEBUG);
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        val REQUEST_ENABLE_BT = 1
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)

        val scanSubscription = rxBleClient.scanBleDevices(ScanSettings.Builder().build())
                .subscribe(
                        { scanResult ->
                            if(scanResult.bleDevice.name=="RPI3") {
//                                connectWithBLEDevice(scanResult.bleDevice.macAddress)
                            }
                        },
                        { throwable ->
                            Log.e("MA", "error: ${throwable.message}")
                        }
                )
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
        val homeF = HomeFragment()
        FragmentFlowUtils.replaceFragment(supportFragmentManager, homeF, homeF.familiarName, false, false)
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


    fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder(this)
                        .setTitle("Location")
                        .setMessage("Please")
                        .setPositiveButton("Ok") { dialogInterface, i ->
                            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
                        }
                        .create()
                        .show()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
            }
            return false
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED) {
//                        scanBLE()
                    }
                }
                return
            }
        }
    }

    override fun closeNavdrawer() {
        drawer.closeDrawer()
    }
}
