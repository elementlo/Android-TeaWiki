package com.zlz.adaptar;

import com.zlz.mylistfragment.MyListFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdaptar extends FragmentPagerAdapter{
	private final int NUM_ITEMS=5;
	public MyFragmentPagerAdaptar(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return MyListFragment.newInstance(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return NUM_ITEMS;
	}

}
