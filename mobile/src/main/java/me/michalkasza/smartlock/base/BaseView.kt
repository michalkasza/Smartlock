package me.michalkasza.smartlock.base

import android.support.v4.app.FragmentManager

interface BaseView {
    val familiarName : String?
    fun getSupportFragmentManager(): FragmentManager
}