package me.michalkasza.smartlock.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager

abstract class BaseFragment: Fragment(), BaseView {
    private val activity: BaseActivity by lazy { getActivity() as BaseActivity }
    override val familiarName: String by lazy { "Base Fragment" }

    override fun getSupportFragmentManager(): FragmentManager { return activity.supportFragmentManager }

    override fun getLinearVerticalLayoutManager(): LinearLayoutManager { return LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false) }

    override fun showSnackbar(message: String) { activity.showSnackbar(message) }

    override fun showSnackbar(messageResourceId: Int) { activity.showSnackbar(messageResourceId) }

    override fun setToolbarTitle(title: String) { activity.setToolbarTitle(title) }

    override fun setToolbarTitle(titleResourceId: Int) { activity.setToolbarTitle(titleResourceId) }
}