package me.michalkasza.smartlock.base

import android.support.v4.app.FragmentManager

interface BaseView {
    fun getSupportFragmentManager(): FragmentManager
}