package me.michalkasza.smartlock.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
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
        fragmentTransaction.replace(R.id.fl_container, fragment, tag)
        if(addToBackStack) fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }
}