package com.revengeos.uidemo

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.revengeos.revengeui.utils.ThemeUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeUtils.setThemeMode(ThemeUtils.getThemeModeValue(this));
        setContentView(R.layout.activity_main)

        setSupportActionBar(home_toolbar)
        home_toolbar.inflateMenu(R.menu.toolbar_menu);

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_theme -> {
                ThemeUtils.showThemeDialog(this)
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}