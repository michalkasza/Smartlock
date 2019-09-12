package me.michalkasza.smartlock.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.databinding.Observable
import androidx.lifecycle.ViewModel

open class BaseViewModel(app: Application) : AndroidViewModel(app), Observable {
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { }

}