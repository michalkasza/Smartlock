package me.michalkasza.smartlock.ui.components.binding

import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.databinding.BindingAdapter
import android.support.design.widget.TabLayout


class ViewPagerBinding {
    @BindingAdapter(value = ["pagerAdapter"], requireAll = true)
    fun setViewPager(viewPager: ViewPager, adapter: FragmentPagerAdapter) {
        viewPager.adapter = adapter
    }

    @BindingAdapter(value = ["viewPager"], requireAll = true)
    fun setViewPagerTabs(tabLayout: TabLayout, viewPager: ViewPager) {
        tabLayout.post {
            tabLayout.setupWithViewPager(viewPager)
        }
    }
}