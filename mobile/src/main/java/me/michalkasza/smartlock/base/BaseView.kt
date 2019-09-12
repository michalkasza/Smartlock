package me.michalkasza.smartlock.base

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager

interface BaseView {
    val familiarName : String?
    fun getSupportFragmentManager(): FragmentManager
    fun getLinearVerticalLayoutManager(): androidx.recyclerview.widget.LinearLayoutManager
    fun showSnackbar(message: String)
    fun showSnackbar(messageResourceId: Int)
    fun setAppToolbarTitle(title: String?)
    fun setAppToolbarTitle(titleResourceId: Int)
}