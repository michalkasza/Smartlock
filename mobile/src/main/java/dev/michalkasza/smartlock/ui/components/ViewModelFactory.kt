package dev.michalkasza.smartlock.ui.components

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.michalkasza.smartlock.base.BaseView

class ViewModelFactory(val view: BaseView, val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BaseView::class.java, Application::class.java).newInstance(view, app)
    }
}