package me.michalkasza.smartlock.ui.lock

import android.app.Application
import android.databinding.Bindable
import android.databinding.ObservableField
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel

class LockViewModel(baseView: BaseView, app: Application): BaseViewModel(app), LockInterface.UserInteractions {
    val view = baseView as LockInterface.View
    val lockPagerAdapter = ObservableField<LockPagerAdapter>()

    init {
        view.getSupportFragmentManager().let { supportFragmentManager -> lockPagerAdapter.set(LockPagerAdapter(supportFragmentManager)) }
    }

    override fun lockChanged() {

    }
}