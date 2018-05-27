package me.michalkasza.smartlock.ui.splash

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseView
import me.michalkasza.smartlock.base.BaseViewModel
import me.michalkasza.smartlock.data.model.AuthError
import me.michalkasza.smartlock.data.repository.UsersRepository

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

    override fun getCurrentUser() {
        UsersRepository.getCurrentUser().observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { user -> view.initMain() },
                onError = { getUserError ->
                    when(getUserError) {
                        is AuthError.UserNotExistInFirestore -> registerUser()
                        else -> view.showSnackbar(R.string.auth_error)
                    }
                }
        )
    }

    override fun registerUser() {
        UsersRepository.registerUser(FirebaseAuth.getInstance().currentUser).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { registeredUser -> view.initMain() },
                onError = { registerUserError -> view.showSnackbar(R.string.auth_registration_error) }
        )
    }
}