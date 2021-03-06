package dev.michalkasza.smartlock.ui.main.lock.access.grant_dialog

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import dev.michalkasza.smartlock.base.BaseView
import dev.michalkasza.smartlock.base.BaseViewModel
import dev.michalkasza.smartlock.data.model.User
import dev.michalkasza.smartlock.data.repository.UsersRepository

class GrantAccessViewModel(baseView: BaseView, app: Application): BaseViewModel(app), GrantAccessInterface.UserInteractions {
    val view = baseView as GrantAccessInterface.View

    val email = ObservableField<String>()
    val accessingUser = ObservableField<User>()
    val userInfoVisibility = ObservableField<Boolean>()
    val emailGrantButtonVisibility = ObservableField<Boolean>()
    val confirmGrantButtonVisibility = ObservableField<Boolean>()

    var lockId: String? = null

    init {
        userInfoVisibility.set(false)
        emailGrantButtonVisibility.set(true)
        confirmGrantButtonVisibility.set(false)
    }

    override fun onEmailGrantClicked() {
         UsersRepository.getUserByEmail(email.get()).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                 onNext = { user ->
                     accessingUser.set(user)
                     userInfoVisibility.set(true)
                     emailGrantButtonVisibility.set(false)
                     confirmGrantButtonVisibility.set(true)
                 },
                 onError = { Log.e("T", "NO USER FOUND!") }
         )
    }


    override fun onGrantConfirmationClicked() {
        UsersRepository.grantLockToUser(lockId, accessingUser.get())
    }

}