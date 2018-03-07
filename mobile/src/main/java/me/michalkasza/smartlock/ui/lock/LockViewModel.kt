package me.michalkasza.smartlock.ui.lock

import android.app.Application
import android.databinding.ObservableField
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel

class LockViewModel(baseView: BaseView, app: Application): BaseViewModel(app) {
    val view = baseView as LockInterface.View
    val pagerAdapter = ObservableField<LockPagerAdapter>()

    init {
        view.getSupportFragmentManager().let { supportFragmentManager -> pagerAdapter.set(LockPagerAdapter(supportFragmentManager)) }
    }
}