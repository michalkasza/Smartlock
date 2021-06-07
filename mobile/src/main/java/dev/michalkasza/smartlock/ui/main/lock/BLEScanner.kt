package dev.michalkasza.smartlock.ui.main.lock

import android.util.Log
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult


class BLEScanner {
    val mLeScanCallback = object : ScanCallback() {

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            Log.e("BLE", result.device.name)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            Log.e("BLE", "${results?.size}")
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e("BLE", "error")
        }
    }

    fun startScan() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        val mBluetoothLeScanner = mBluetoothAdapter.bluetoothLeScanner;
        mBluetoothLeScanner.startScan(mLeScanCallback)
    }

    companion object {
        val TAG = BLEScanner::class.java.simpleName
    }
}