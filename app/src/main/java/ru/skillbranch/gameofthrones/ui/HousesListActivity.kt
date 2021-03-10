package ru.skillbranch.gameofthrones.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.app.sbgameofthrones.R
import com.google.android.material.tabs.TabLayout
import ru.skillbranch.gameofthrones.ui.adapters.HousesFragmentPagerAdapter

class HousesListActivity : AppCompatActivity() {

    private var mTabLayout: TabLayout? = null
    private var mPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_houses)

        mPager = findViewById(R.id.view_pager)
        mPager?.adapter = HousesFragmentPagerAdapter(supportFragmentManager)

        mTabLayout = findViewById(R.id.tabs)
        mTabLayout?.let {
            it.setupWithViewPager(mPager)
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_primary)
    }
}