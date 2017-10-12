package com.zlz.adaptar;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyPagerAdaptar extends PagerAdapter{
	private List<View> data;
	public MyPagerAdaptar(List<View> data) {
		// TODO Auto-generated constructor stub
		this.data=data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data==null?0:data.size();
	}
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(data.get(position));
		return data.get(position);
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(data.get(position));
	}
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

}
