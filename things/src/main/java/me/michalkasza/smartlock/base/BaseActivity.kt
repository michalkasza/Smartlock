package me.michalkasza.smartlock.base

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.LinearLayoutManager

abstract class BaseActivity : AppCompatActivity(), BaseView {
    override val familiarName: String by lazy { "Base Activity" }

    override fun showSnackbar(message: String) {
        Snackbar.make(currentFocus, message, Snackbar.LENGTH_SHORT)
    }

    override fun showSnackbar(messageResourceId: Int) {
        Snackbar.make(currentFocus, messageResourceId, Snackbar.LENGTH_SHORT)
    }

    override fun getLinearVerticalLayoutManager(): androidx.recyclerview.widget.LinearLayoutManager {
        return LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
    }

    override fun getGridLayoutManager(): GridLayoutManager {
        return GridLayoutManager(this, 3)
    }
}