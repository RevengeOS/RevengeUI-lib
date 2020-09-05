package com.revengeos.uidemo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.revengeos.uidemo.items.*

class DemoFragmentsPager(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ButtonsDemoFragment()
            1 -> SlidersDemoFragment()
            else -> {
                return ButtonsDemoFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2;
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Buttons"
            1 -> "Sliders"
            else -> {
                return "Buttons"
            }
        }
    }
}