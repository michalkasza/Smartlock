package me.michalkasza.smartlock.ui.lock.status.bottomsheet

import android.databinding.DataBindingUtil
import android.support.design.widget.BottomSheetBehavior
import android.view.LayoutInflater
import android.view.View
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.databinding.BottomsheetStatusBinding

class StatusBottomsheet {
    fun getInflatedView(layoutInflater: LayoutInflater): View {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottomsheet_status, null, false)
        bottomSheetView.addOnLayoutChangeListener({view, i1, i2, i3, i4, i5, i6, i7, i8 ->
            DataBindingUtil.bind<BottomsheetStatusBinding>(view)?.let { binding ->
                binding.bottomSheetView = this
//                BottomSheetBehavior.from(bottomSheetView).peekHeight = 200
            }
        })

        return bottomSheetView
    }

    companion object {
        val TAG = StatusBottomsheet::class.java.simpleName
    }
}