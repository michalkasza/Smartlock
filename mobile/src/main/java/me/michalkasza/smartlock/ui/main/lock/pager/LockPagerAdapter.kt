package me.michalkasza.smartlock.ui.lock.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import me.michalkasza.smartlock.base.BaseFragment
import me.michalkasza.smartlock.ui.lock.access.AccessFragment
import me.michalkasza.smartlock.ui.lock.logs.LogsFragment
import me.michalkasza.smartlock.ui.lock.status.StatusFragment

class LockPagerAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
    val fragments = ArrayList<BaseFragment>()

    init {
        fragments.addAll(arrayListOf(
                StatusFragment(),
                LogsFragment(),
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