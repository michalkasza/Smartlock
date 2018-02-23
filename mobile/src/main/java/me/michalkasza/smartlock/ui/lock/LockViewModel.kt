package me.michalkasza.smartlock.ui.lock

import android.databinding.Bindable
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel

class LockViewModel(baseView: BaseView): BaseViewModel() {
    val view = baseView as LockInterface.View

    @Bindable
    fun getPagerAdapter() = view.getSupportFragmentManager()?.let { supportFragmentManager ->
        LockPagerAdapter(supportFragmentManager)
    }
}