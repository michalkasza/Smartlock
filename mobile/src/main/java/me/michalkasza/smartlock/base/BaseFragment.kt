package me.michalkasza.smartlock.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager

abstract class BaseFragment: Fragment(), BaseView {
    private val activity: BaseActivity by lazy { getActivity() as BaseActivity }
    override val familiarName: String by lazy { "Base Fragment" }

    override fun getSupportFragmentManager(): FragmentManager { return activity.supportFragmentManager }

    override fun getLinearVerticalLayoutManager(): androidx.recyclerview.widget.LinearLayoutManager { return LinearLayoutManager(activity, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false) }

    override fun showSnackbar(message: String) { activity.showSnackbar(message) }

    override fun showSnackbar(messageResourceId: Int) { activity.showSnackbar(messageResourceId) }

    override fun setAppToolbarTitle(title: String?) { activity.setAppToolbarTitle(title) }

    override fun setAppToolbarTitle(titleResourceId: Int) { activity.setAppToolbarTitle(titleResourceId) }
}