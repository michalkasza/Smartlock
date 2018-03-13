package me.michalkasza.smartlock.base

import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

abstract class BaseActivity : AppCompatActivity(), BaseView {
    override val familiarName: String by lazy { "Base Activity" }

    override fun showSnackbar(message: String) {
        Snackbar.make(currentFocus, message, Snackbar.LENGTH_SHORT)
    }

    override fun showSnackbar(messageResourceId: Int) {
        Snackbar.make(currentFocus, messageResourceId, Snackbar.LENGTH_SHORT)
    }

    override fun getLinearVerticalLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}