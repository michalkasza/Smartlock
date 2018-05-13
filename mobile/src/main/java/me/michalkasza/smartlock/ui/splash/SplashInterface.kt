package me.michalkasza.smartlock.ui.splash

interface SplashInterface {
    interface View {
        fun initAuthentication()
        fun initMain()
    }

    interface UserInteractions {
        fun checkSession()
    }
}