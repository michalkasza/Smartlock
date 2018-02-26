package me.michalkasza.smartlock.ui.components.binding

import android.databinding.BindingAdapter
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager

object ViewPagerBinding {
    @JvmStatic
    @BindingAdapter(value = ["adapter"], requireAll = true)
    fun setPagerAdapter(viewPager: ViewPager, pagerAdapter: FragmentStatePagerAdapter, tabLayout: TabLayout) {
        viewPager.adapter = pagerAdapter
    }
}