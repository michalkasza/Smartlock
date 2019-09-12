package me.michalkasza.smartlock.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.databinding.Observable

open class BaseViewModel(app: Application) : AndroidViewModel(app), Observable {
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { }

}