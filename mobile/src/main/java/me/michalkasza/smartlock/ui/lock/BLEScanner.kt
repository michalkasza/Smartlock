package me.michalkasza.smartlock.ui.lock

import android.util.Log
import no.nordicsemi.android.support.v18.scanner.*
import java.util.*


class BLEScanner {
    init {
        val mScanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                Log.e(TAG, "single dev: " + result?.device + " addr: " + result?.rssi)
            }
            override fun onBatchScanResults(results: List<ScanResult>) {
                if (!results.isEmpty()) {
                    val result = results[0]
                    val device = result.device
                    val deviceAddress = device.address
                    Log.e(TAG, "batch dev: " + device + " addr: " + deviceAddress)
                }
            }
            override fun onScanFailed(errorCode: Int) {
                Log.e(TAG, "Error scan failed")
            }
        }

        val scanner = BluetoothLeScannerCompat.getScanner()

        val settings = ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setReportDelay(1000)
                .build()

        val scanFilter = ScanFilter.Builder().build()
        scanner.startScan(Arrays.asList(scanFilter), settings, mScanCallback)
    }

    companion object {
        val TAG = BLEScanner::class.java.simpleName
    }
}