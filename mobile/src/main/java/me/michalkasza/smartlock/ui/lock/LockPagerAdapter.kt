package me.michalkasza.smartlock.ui.lock

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class LockPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    val fragments = ArrayList<Fragment>()

    init {
        fragments.addAll(arrayListOf(
                LockFragment(),
                LockFragment(),
                LockFragment(),
                LockFragment()
        ))
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }

}