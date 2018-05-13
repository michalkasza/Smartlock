package me.michalkasza.smartlock.ui.splash

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel

class SplashViewModel(baseView: BaseView, var app: Application): BaseViewModel(app), SplashInterface.UserInteractions {
    val view = baseView as SplashInterface.View

    override fun checkSession() {
        val user = FirebaseAuth.getInstance().currentUser

        if(user == null) {
            view.initAuthentication()
        } else {
            view.initMain()
        }
    }
}