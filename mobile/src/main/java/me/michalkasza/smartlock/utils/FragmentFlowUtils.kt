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
    }
}