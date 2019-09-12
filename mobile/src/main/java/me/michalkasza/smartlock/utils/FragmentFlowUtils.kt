package me.michalkasza.smartlock.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import me.michalkasza.smartlock.R

object FragmentFlowUtils {
    private lateinit var fragmentTransaction: FragmentTransaction

    fun replaceFragment(fragmentManager: FragmentManager,
                        fragment: Fragment,
                        tag: String,
                        animation: Boolean,
                        addToBackStack: Boolean) {
        fragmentTransaction = fragmentManager.beginTransaction()

        if(animation) fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        fragmentTransaction.replace(R.id.fl_main_container, fragment, tag)
        if(addToBackStack) fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }
}