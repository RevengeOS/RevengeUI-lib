package com.revengeos.uidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(home_toolbar)

        val pagerAdapter = DemoFragmentsPager(supportFragmentManager)
        demo_pager.adapter = pagerAdapter
        bottom_nav.setupWithViewPager(demo_pager)

        content_root.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        content_root.setOnApplyWindowInsetsListener { _, insets ->
            home_toolbar.setPadding(0, insets.systemWindowInsetTop, 0, 0)
            bottom_nav.setPadding(0, 0, 0, insets.systemWindowInsetBottom)
            insets.consumeSystemWindowInsets()
        }

    }
}