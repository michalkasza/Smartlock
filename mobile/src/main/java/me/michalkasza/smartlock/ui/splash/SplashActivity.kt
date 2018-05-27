package me.michalkasza.smartlock.ui.splash

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import me.michalkasza.smartlock.R
import me.michalkasza.smartlock.base.BaseActivity
import me.michalkasza.smartlock.data.repository.UsersRepository
import me.michalkasza.smartlock.databinding.ActivitySplashBinding
import me.michalkasza.smartlock.ui.components.ViewModelFactory
import me.michalkasza.smartlock.ui.main.MainActivity
import java.util.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import me.michalkasza.smartlock.data.model.AuthError
import com.google.firebase.auth.FirebaseAuth

class SplashActivity: BaseActivity(), SplashInterface.View {
    private lateinit var splashViewModel: SplashViewModel
    private val authenticationProviders = Arrays.asList(
            AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
            AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashViewModel = ViewModelProviders.of(this, ViewModelFactory(this, application)).get(SplashViewModel::class.java)

        val viewBinding: ActivitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewBinding.viewModel = splashViewModel

        splashViewModel.checkSession()
    }

    override fun setAppToolbarTitle(title: String?) { }

    override fun setAppToolbarTitle(titleResourceId: Int) { }

    override fun initAuthentication() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(authenticationProviders)
                        .setIsSmartLockEnabled(false)
                        .setTheme(R.style.AppTheme)
                        .setLogo(R.drawable.logo)
                        .build(),
                AUTHENTICATION)
    }

    override fun initMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode.equals(AUTHENTICATION)) {
            if (resultCode.equals(Activity.RESULT_OK)) {
                UsersRepository.getCurrentUser().observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                        onNext = { user -> initMain() },
                        onError = { userError ->
                            when(userError) {
                                is AuthError.UserNotExistInFirestore -> {
                                    UsersRepository.registerUser(FirebaseAuth.getInstance().currentUser).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                                            onNext = { user -> initMain() },
                                            onError = { registrationError -> showSnackbar(R.string.auth_registration_error) }
                                    )
                                }
                                else -> { showSnackbar(R.string.auth_error) }
                            }
                        }
                )
            } else {
                showSnackbar(R.string.auth_error)
            }
        }
    }

    companion object {
        val AUTHENTICATION = 1;
    }
}