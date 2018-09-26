package me.michalkasza.smartlock.ui

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
import com.google.android.gms.tasks.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User

class MainActivity : Activity() {
    lateinit var advertiseCallback: AdvertiseCallback
    lateinit var bluetoothManager: BluetoothManager
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var bluetoothLeAdvertiser: BluetoothLeAdvertiser
    val locksDb = FirebaseFirestore.getInstance().collection("locks")
    val usersDB = FirebaseFirestore.getInstance().collection("users")

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
        Log.e(TAG, "saveInFirebaseDatastore")
        val city = HashMap<String, Any?>()
        city.put("accessList", arrayListOf("jQ3SygKyWeeYbJmLsuaInQcEZFA3"))
        city.put("lastAccessTime", null)
        city.put("lastAccessUser", null)
        city.put("logs", arrayListOf<String>())
        city.put("name", uuid.substring(uuid.lastIndexOf('-') + 1))
        city.put("ownerId", "jQ3SygKyWeeYbJmLsuaInQcEZFA3")
        city.put("status", true)

        locksDb.document(uuid)
                .set(city)
                .addOnSuccessListener({
                    locksDb.document(uuid).addSnapshotListener({ lockSnapshot, firebaseFirestoreException ->
                        if(lockSnapshot != null) {
                            lockSnapshot.toObject<Lock>(Lock::class.java)?.let{ lock ->
                                if(lock.status) {
                                    setLocked()
                                } else {
                                    setUnlocked()
                                }
                            }
                        }
                    })
                })
                .addOnFailureListener({ e ->
                    Log.w(TAG, "Error writing document", e)
                })

        usersDB.document("jQ3SygKyWeeYbJmLsuaInQcEZFA3").get().addOnCompleteListener(OnCompleteListener { task ->
            val userSnapshot = task.result
            userSnapshot?.let {
                userSnapshot.toObject<User>(User::class.java)?.let { user ->
                    val locksOwned = arrayListOf<String>()
                    locksOwned.addAll(user.locksOwned)
                    locksOwned.add(uuid)
                    val data = HashMap<String, Any>()
                    data.put("locksOwned", locksOwned)
                    usersDB.document("jQ3SygKyWeeYbJmLsuaInQcEZFA3").set(data, SetOptions.merge())
                }
            }
        })
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
}