package me.michalkasza.smartlock.ui.lock

import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel

class LockViewModel(baseView: BaseView): BaseViewModel() {
    val view = baseView as LockInterface.View
    val pagerAdapter = LockPagerAdapter(view.getSupportFragmentManager())

}