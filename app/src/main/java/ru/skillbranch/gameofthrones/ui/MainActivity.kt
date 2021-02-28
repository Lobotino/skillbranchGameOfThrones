package ru.skillbranch.gameofthrones.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.app.sbgameofthrones.R
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private val housesList : List<String> = arrayListOf("Winterfell", "Lannister", "Targaryen", "Greyjoy", "Tyrell", "Baratheon", "Martell")

    private var mTabLayout : TabLayout? = null
    private var mPager : ViewPager? = null

    private class OnPageViewChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            //do nothing
        }

        override fun onPageSelected(position: Int) {
            //TODO
        }

        override fun onPageScrollStateChanged(state: Int) {
            //do nothing
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mPager = findViewById(R.id.view_pager)
        mPager?.addOnPageChangeListener(OnPageViewChangeListener())

        mTabLayout = findViewById(R.id.tabs)
        mTabLayout?.let {
            for(house in housesList) {
                it.addTab(it.newTab().setText(house))
            }
        }
    }
}