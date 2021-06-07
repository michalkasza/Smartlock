package dev.michalkasza.smartlock.ui.main.lock.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import dev.michalkasza.smartlock.base.BaseFragment
import dev.michalkasza.smartlock.ui.main.lock.access.AccessFragment
import dev.michalkasza.smartlock.ui.main.lock.logs.LogsFragment
import dev.michalkasza.smartlock.ui.main.lock.status.StatusFragment

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
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments[position].familiarName
    }

    override fun getCount(): Int {
        return fragments.size
    }

}