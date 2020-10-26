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
            2 -> LayoutTransitionDemoFragment()
            3 -> SwitchesDemoFragment()
            else -> {
                return ButtonsDemoFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 4;
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Buttons"
            1 -> "Sliders"
            2 -> "Layout alpha transition"
            3 -> "Switches"
            else -> {
                return "Buttons"
            }
        }
    }
}