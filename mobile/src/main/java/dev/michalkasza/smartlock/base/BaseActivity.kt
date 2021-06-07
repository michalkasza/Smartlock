package dev.michalkasza.smartlock.base

import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar

abstract class BaseActivity : AppCompatActivity(), BaseView {
    override val familiarName: String by lazy { this.javaClass.simpleName }

    override fun showSnackbar(message: String) {
        currentFocus?.let {
            Snackbar.make(it, message, BaseTransientBottomBar.LENGTH_SHORT).show()
        }
    }

    override fun showSnackbar(messageResourceId: Int) {
        currentFocus?.let {
            Snackbar.make(it, messageResourceId, BaseTransientBottomBar.LENGTH_SHORT).show()
        }
    }

    override fun getLinearVerticalLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}