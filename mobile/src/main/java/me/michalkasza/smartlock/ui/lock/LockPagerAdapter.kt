package me.michalkasza.smartlock.ui.lock

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import me.michalkasza.smartlock.base.BaseFragment
import me.michalkasza.smartlock.ui.lock.access.AccessFragment
import me.michalkasza.smartlock.ui.lock.status.StatusFragment

class LockPagerAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
    val fragments = ArrayList<BaseFragment>()

    init {
        fragments.addAll(arrayListOf(
                StatusFragment(),
                AccessFragment()
        ))
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments.get(position).familiarName
    }

    override fun getCount(): Int {
        return fragments.size
    }

}