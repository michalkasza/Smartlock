package dev.michalkasza.smartlock.ui

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.os.ParcelUuid
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.BluetoothManager
import android.content.Context
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.BluetoothLeAdvertiser
import android.provider.Settings
import android.util.Log
import java.util.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dev.michalkasza.smartlock.base.BaseActivity
import dev.michalkasza.smartlock.data.model.User
import dev.michalkasza.smartlock.data.repository.LocksRepository
import dev.michalkasza.smartlock.ui.locks_list.LocksListFragment
import android.telephony.TelephonyManager
import dev.michalkasza.smartlock.R


class MainActivity : BaseActivity() {
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
        initFragment()
    }

    override fun onResume() {
        super.onResume()
        startBluetoothAdvertising()
        LocksRepository.getLocks()
        LocksRepository.initLocksChangesListener()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_main_container, LocksListFragment(), LocksListFragment.TAG)
                .commit()
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

    fun getUUID(): String {
        val teleManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var tmSerial: String? = teleManager.simSerialNumber
        var tmDeviceId: String? = teleManager.deviceId
        var androidId: String? = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        if (tmSerial == null) tmSerial = "1"
        if (tmDeviceId == null) tmDeviceId = "1"
        if (androidId == null) androidId = "1"
        val deviceUuid = UUID(androidId.hashCode().toLong(), tmDeviceId.hashCode().toLong() shl 32 or tmSerial.hashCode().toLong())
        Log.e(TAG, deviceUuid.toString())
        return deviceUuid.toString()
    }

    private fun startBluetoothAdvertising() {
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
                .addServiceUuid(ParcelUuid.fromString(getUUID()))
                .build()

        bluetoothLeAdvertiser.startAdvertising(settings, data, advertiseCallback)
    }

    fun initializeNewLock() {
        val uuid = UUID.randomUUID()
        saveInFirebaseDatastore(uuid.toString())
    }

    private fun saveInFirebaseDatastore(uuid: String) {
        val sampleStreets = resources.getStringArray(R.array.streets)

        Log.e(TAG, "saveInFirebaseDatastore")
        val city = HashMap<String, Any?>()
        city.put("accessList", arrayListOf("mXJLzORWjHhs4KKfYvutSHkb1X13"))
        city.put("lastAccessTime", null)
        city.put("lastAccessUser", null)
        city.put("logs", arrayListOf<String>())
        city.put("name", "${sampleStreets[Random().nextInt(sampleStreets.size)]} ${Random().nextInt(39)}/${Random().nextInt(140)}")
        city.put("ownerId", "mXJLzORWjHhs4KKfYvutSHkb1X13")
        city.put("hostSecureId", getUUID())
        city.put("status", true)

        locksDb.document(uuid)
                .set(city)

        usersDB.document("mXJLzORWjHhs4KKfYvutSHkb1X13").get().addOnCompleteListener { task ->
            val userSnapshot = task.result
            userSnapshot?.let {
                userSnapshot.toObject<User>(User::class.java)?.let { user ->
                    val locksOwned = arrayListOf<String>()
                    locksOwned.addAll(user.locksOwned)
                    locksOwned.add(uuid)
                    val data = HashMap<String, Any>()
                    data.put("locksOwned", locksOwned)
                    usersDB.document("mXJLzORWjHhs4KKfYvutSHkb1X13").set(data, SetOptions.merge())
                }
            }
        }
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
}