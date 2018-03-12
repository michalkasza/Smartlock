package me.michalkasza.smartlock.base

import android.support.v4.app.FragmentManager

interface BaseView {
    val familiarName : String?
    fun getSupportFragmentManager(): FragmentManager
    fun showSnackbar(message: String)
    fun showSnackbar(messageResourceId: Int)
    fun setToolbarTitle(title: String)
    fun setToolbarTitle(titleResourceId: Int)
}