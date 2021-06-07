package dev.michalkasza.smartlock.base

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

abstract class BaseFragment: Fragment(), BaseView {
    private val activity: BaseActivity by lazy { getActivity() as BaseActivity }
    override val familiarName: String by lazy { "Base Fragment" }

    override fun getLinearVerticalLayoutManager(): androidx.recyclerview.widget.LinearLayoutManager { return LinearLayoutManager(activity, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false) }

    override fun getGridLayoutManager(): GridLayoutManager { return GridLayoutManager(activity, 3)}

    override fun showSnackbar(message: String) { activity.showSnackbar(message) }

    override fun showSnackbar(messageResourceId: Int) { activity.showSnackbar(messageResourceId) }
}