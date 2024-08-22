package com.loyltworks.mandelapremium.ui.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    val mFragmentList: MutableList<Fragment> = ArrayList()
    val mFragmentTitleList: MutableList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFrag(fragment: Fragment, title: String) {
        if (!fragment.isAdded) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
//        return null
         return mFragmentTitleList.get(position);
    }
}