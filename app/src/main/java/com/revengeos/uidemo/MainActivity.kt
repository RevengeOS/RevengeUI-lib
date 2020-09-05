package com.revengeos.uidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(home_toolbar)

        var pagerAdapter = DemoFragmentsPager(supportFragmentManager)
        demo_pager.adapter = pagerAdapter
        bottom_nav.setupWithViewPager(demo_pager)

    }
}