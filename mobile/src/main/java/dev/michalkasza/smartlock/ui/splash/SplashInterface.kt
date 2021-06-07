package dev.michalkasza.smartlock.ui.splash

import dev.michalkasza.smartlock.base.BaseView

interface SplashInterface {
    interface View: BaseView {
        fun initAuthentication()
        fun initMain()
    }

    interface UserInteractions {
        fun checkSession()
        fun getCurrentUser()
        fun registerUser()
    }
}