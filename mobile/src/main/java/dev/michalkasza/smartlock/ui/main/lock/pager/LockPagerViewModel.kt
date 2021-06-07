package dev.michalkasza.smartlock.ui.main.lock.pager

import android.app.Application
import androidx.databinding.ObservableField
import dev.michalkasza.smartlock.base.BaseView
import dev.michalkasza.smartlock.base.BaseViewModel

class LockPagerViewModel(baseView: BaseView, app: Application): BaseViewModel(app), LockPagerInterface.UserInteractions {
    val view = baseView as LockPagerInterface.View
    val lockPagerAdapter = ObservableField<LockPagerAdapter>()

    init {
        view.getSupportFragmentManager().let { supportFragmentManager -> lockPagerAdapter.set(LockPagerAdapter(supportFragmentManager)) }
    }
}