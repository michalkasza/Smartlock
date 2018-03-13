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
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : Activity() {
    lateinit var advertiseCallback: AdvertiseCallback
    lateinit var bluetoothManager: BluetoothManager
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var bluetoothLeAdvertiser: BluetoothLeAdvertiser
    val db = FirebaseFirestore.getInstance().collection("locks")

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
        val uuid = UUID.randomUUID()

        saveInFirebaseDatastore(uuid.toString())

        val settings = AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .setConnectable(true)
                .setTimeout(0)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
                .build()

        val data = AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .setIncludeTxPowerLevel(false)
                .addServiceUuid(ParcelUuid(uuid))
                .build()

        bluetoothLeAdvertiser.startAdvertising(settings, data, advertiseCallback)
    }

    private fun setUnlocked() {
        tv_status.setText("Unlocked")
    }

    private fun setLocked() {
        tv_status.setText("Locked")
    }

    private fun saveInFirebaseDatastore(uuid: String) {
        val city = HashMap<String, Any?>()
        city.put("accessList", arrayListOf<String>())
        city.put("lastAccessTime", null)
        city.put("lastAccessUser", null)
        city.put("logs", arrayListOf<String>())
        city.put("name", null)
        city.put("ownerId", null)
        city.put("status", true)

        db.document(uuid)
                .set(city)
                .addOnSuccessListener({
                    db.document(uuid).addSnapshotListener({ lockSnapshot, firebaseFirestoreException ->
                        if(lockSnapshot != null) {
                            val lock = lockSnapshot.toObject<Lock>(Lock::class.java)
                            if(lock.status) {
                                setLocked()
                            } else {
                                setUnlocked()
                            }
                        }
                    })
                })
                .addOnFailureListener({ e ->
                    Log.w(TAG, "Error writing document", e)
                })
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
}