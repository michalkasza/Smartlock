package me.michalkasza.smartlock.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

open class BaseFragment: Fragment(), BaseView {
    override fun getSupportFragmentManager(): FragmentManager {
        return getSupportFragmentManager()
    }
}