package me.michalkasza.smartlock

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.os.ParcelUuid
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.BluetoothManager
import android.content.Context
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.BluetoothLeAdvertiser
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : Activity() {
    val SERVICE_UUID = UUID.fromString("795090c7-420d-4048-a24e-18e60180e23c");
    lateinit var advertiseCallback: AdvertiseCallback
    lateinit var bluetoothManager: BluetoothManager
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var bluetoothLeAdvertiser: BluetoothLeAdvertiser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBluetoothServices()
        initAdvertisingButton()
    }

    private fun initAdvertisingButton() {
        bt_advertise.setOnClickListener {
            startAdvertising()
        }
    }

    private fun initBluetoothServices() {
        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        bluetoothLeAdvertiser = bluetoothAdapter.bluetoothLeAdvertiser
        advertiseCallback = object : AdvertiseCallback() {
            override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
                Log.e(TAG, "LE Advertise Started.")
            }

            override fun onStartFailure(errorCode: Int) {
                Log.e(TAG, "LE Advertise Failed: " + errorCode)
            }
        }
    }

    private fun startAdvertising() {
        bluetoothLeAdvertiser.stopAdvertising(advertiseCallback)

        val settings = AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .setConnectable(true)
                .setTimeout(0)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
                .build()

        val data = AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .setIncludeTxPowerLevel(false)
                .addServiceUuid(ParcelUuid(SERVICE_UUID))
                .build()

        bluetoothLeAdvertiser.startAdvertising(settings, data, advertiseCallback)
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
}