package com.zlz.adaptar;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.day1105_class_viewpagerfragment.R;
import com.zlz.classentity.Tea;
import com.zlz.helpers.HttpUtils;

public class MyAdaptar extends BaseAdapter{
	private Context context;
	private List<Tea> list;
	public MyAdaptar (Context context,List<Tea> list) {
		this.context=context;
		this.list=list;
	}
	class MyHolder{
		TextView txtvTitle_item,txtvDescription_item;
		ImageView imgv_item;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list==null?0:list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertview, ViewGroup arg2) {
		// TODO Auto-generated method stub
		MyHolder myHolder = null;
		if (convertview==null) {
			myHolder=new MyHolder();
			convertview=View.inflate(context, R.layout.listview_view, null);
			myHolder.imgv_item=(ImageView) convertview.findViewById(R.id.imgv_item);
			myHolder.txtvDescription_item=(TextView) convertview.findViewById(R.id.txtvDescription_item);
			myHolder.txtvTitle_item=(TextView) convertview.findViewById(R.id.txtvTitle_item);
			convertview.setTag(myHolder);
		}else {
			myHolder=(MyHolder) convertview.getTag();
		}
		myHolder.txtvDescription_item.setText(list.get(arg0).getDescription());
		myHolder.txtvTitle_item.setText(list.get(arg0).getTitle());
		if(TextUtils.isEmpty(list.get(arg0).getWap_thumb())){
			myHolder.imgv_item.setVisibility(View.GONE);
			myHolder.txtvTitle_item.setEms(15);
			myHolder.txtvDescription_item.setEms(18);
		}else {
			myHolder.imgv_item.setVisibility(View.VISIBLE);
			HttpUtils.getNetBytes(context, list.get(arg0).getWap_thumb(), myHolder.imgv_item);
			myHolder.txtvTitle_item.setEms(12);
			myHolder.txtvDescription_item.setEms(15);
		}
		return convertview;
	 }

}
