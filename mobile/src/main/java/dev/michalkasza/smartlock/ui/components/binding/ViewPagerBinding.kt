package dev.michalkasza.smartlock.ui.components.binding

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import dev.michalkasza.smartlock.ui.main.lock.pager.LockPagerAdapter

object ViewPagerBinding {
    @JvmStatic
    @BindingAdapter(value = ["pagerAdapter"], requireAll = true)
    fun setLockPagerAdapter(viewPager: ViewPager, pagerAdapter: LockPagerAdapter?) {
        viewPager.adapter = pagerAdapter
    }
}