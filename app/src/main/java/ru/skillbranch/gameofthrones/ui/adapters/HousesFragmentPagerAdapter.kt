package ru.skillbranch.gameofthrones.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.ui.HouseCharactersFragment

class HousesFragmentPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 7
    }

    override fun getItem(position: Int): Fragment {
        return HouseCharactersFragment(AppConfig.HOUSES_NAMES_LIST[position])
    }
}