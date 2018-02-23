package me.michalkasza.smartlock.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

abstract class BaseFragment: Fragment(), BaseView {
    override fun getSupportFragmentManager(): FragmentManager? {
        return activity?.supportFragmentManager
    }
}