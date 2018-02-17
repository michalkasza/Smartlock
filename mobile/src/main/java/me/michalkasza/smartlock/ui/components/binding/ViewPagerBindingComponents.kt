package me.michalkasza.smartlock.ui.components.binding

import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.databinding.BindingAdapter



class ViewPagerBindingComponents {
    @BindingAdapter(value = ["android:pagerAdapter"], requireAll = false)
    fun setViewPager(viewPager: ViewPager, adapter: FragmentPagerAdapter) {
        viewPager.adapter = adapter
    }
}