package me.michalkasza.smartlock.ui.components

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.michalkasza.smartlock.base.BaseView


class ViewModelFactory(val view: BaseView): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BaseView::class.java).newInstance(view)
    }
}