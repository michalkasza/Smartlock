package me.michalkasza.smartlock.ui.components.binding

import android.databinding.BindingAdapter
import android.support.v4.view.ViewPager
import me.michalkasza.smartlock.ui.lock.LockPagerAdapter

object ViewPagerBinding {
    @JvmStatic
    @BindingAdapter(value = ["pagerAdapter"], requireAll = true)
    fun setLockPagerAdapter(viewPager: ViewPager, pagerAdapter: LockPagerAdapter?) {
        viewPager.adapter = pagerAdapter
    }
}