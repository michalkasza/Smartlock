package me.michalkasza.smartlock.base

import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager

interface BaseView {
    val familiarName : String?
    fun getSupportFragmentManager(): FragmentManager
    fun getLinearVerticalLayoutManager(): LinearLayoutManager
    fun showSnackbar(message: String)
    fun showSnackbar(messageResourceId: Int)
    fun setSmartlockToolbarTitle(title: String?)
    fun setToolbarTitle(titleResourceId: Int)
}