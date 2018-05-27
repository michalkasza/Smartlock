package me.michalkasza.smartlock.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.Observable

open class BaseViewModel(app: Application) : AndroidViewModel(app), Observable {
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { }

}