package dev.michalkasza.smartlock.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager

abstract class BaseFragment: Fragment(), BaseView {
    private val activity: BaseActivity by lazy { getActivity() as BaseActivity }
    override val familiarName: String by lazy { this.javaClass.simpleName }

    override fun getSupportFragmentManager(): FragmentManager { return activity.supportFragmentManager }

    override fun getLinearVerticalLayoutManager(): LinearLayoutManager { return LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false) }

    override fun showSnackbar(message: String) { activity.showSnackbar(message) }

    override fun showSnackbar(messageResourceId: Int) { activity.showSnackbar(messageResourceId) }

    override fun setAppToolbarTitle(title: String?) { activity.setAppToolbarTitle(title) }

    override fun setAppToolbarTitle(titleResourceId: Int) { activity.setAppToolbarTitle(titleResourceId) }
}