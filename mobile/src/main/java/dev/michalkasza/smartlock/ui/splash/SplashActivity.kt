package dev.michalkasza.smartlock.ui.splash

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import dev.michalkasza.smartlock.R
import dev.michalkasza.smartlock.base.BaseActivity
import dev.michalkasza.smartlock.databinding.ActivitySplashBinding
import dev.michalkasza.smartlock.ui.components.ViewModelFactory
import dev.michalkasza.smartlock.ui.main.MainActivity

class SplashActivity: BaseActivity(), SplashInterface.View {
    private lateinit var splashViewModel: SplashViewModel
    private val authenticationProviders = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
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
            if (resultCode.equals(AppCompatActivity.RESULT_OK)) {
                splashViewModel.getCurrentUser()
            } else {
                showSnackbar(R.string.auth_error)
            }
        }
    }

    companion object {
        val AUTHENTICATION = 1;
    }
}