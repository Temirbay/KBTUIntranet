package com.example.miras.kbtuintranet

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MyFragmentPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val fragments : ArrayList<Fragment> = ArrayList()
    private val titles : ArrayList<String> = ArrayList()


    override fun getItem(position: Int): Fragment = fragments.get(position)

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence = titles.get(position)

    fun addFragment (fragment: Fragment, title : String) {
        fragments.add(fragment)
        titles.add(title)
    }

}