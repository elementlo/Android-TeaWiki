package com.zlz.activity;



import org.w3c.dom.Text;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.day1105_class_viewpagerfragment.R;
import com.handmark.pulltorefresh.library.internal.Utils;
import com.zlz.adaptar.MyFragmentPagerAdaptar;

public class MainActivity extends FragmentActivity{
	private ViewPager vp_list;
	private DrawerLayout drawer;
	private ImageView imgv_openDrawer,imgv_closeDrawer;
	private LinearLayout drawerMenu;
	private RadioGroup rg_indexActivity;
	private RadioButton rb1_indexActivity,rb3_indexActivity,rb2_indexActivity,rb4_indexActivity,rb5_indexActivity;
	private EditText et_search;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initUI();
		setImgListeners();
		setVpListListener();
		setRgindexActivityListener();
		MyFragmentPagerAdaptar mfAdaptar=new MyFragmentPagerAdaptar(getSupportFragmentManager());
		vp_list.setAdapter(mfAdaptar);
		}
	private void setRgindexActivityListener() {
		// TODO Auto-generated method stub
		rg_indexActivity.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg1) {
				case R.id.rb1_indexActivity:
					vp_list.setCurrentItem(0);
					break;
				case R.id.rb2_indexActivity:
					vp_list.setCurrentItem(1);
					break;
				case R.id.rb3_indexActivity:
					vp_list.setCurrentItem(2);
					break;
				case R.id.rb4_indexActivity:
					vp_list.setCurrentItem(3);
					break;
				case R.id.rb5_indexActivity:
					vp_list.setCurrentItem(4);
					break;
				default:
					break;
				}
			}
		});
	}
	private void setVpListListener() {
		// TODO Auto-generated method stub
		vp_list.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					rb1_indexActivity.setChecked(true);
					break;
				case 1:
					rb2_indexActivity.setChecked(true);
					break;
				case 2:
					rb3_indexActivity.setChecked(true);
					break;
				case 3:
					rb4_indexActivity.setChecked(true);
					break;
				case 4:
					rb5_indexActivity.setChecked(true);
					break;

				default:
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void setImgListeners() {
		imgv_openDrawer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				drawer.openDrawer(drawerMenu);
			}
		});
		imgv_closeDrawer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(drawerMenu);
			}
		});
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.iv_search:
			if(TextUtils.isEmpty(et_search.getText())){
				Toast.makeText(this, "«Î ‰»Îƒ⁄»›", 0).show();
			}else {
				Intent intent_search=new Intent(this,SearchActivity.class);
				intent_search.putExtra("searchwhat", et_search.getText());
				startActivity(intent_search);
			}
			break;

		default:
			break;
		}
	}
	private void initUI() {
		vp_list=(ViewPager) findViewById(R.id.vp_list);
		drawer=(DrawerLayout) findViewById(R.id.drawl);
		rb1_indexActivity=(RadioButton) findViewById(R.id.rb1_indexActivity);
		rb2_indexActivity=(RadioButton) findViewById(R.id.rb2_indexActivity);
		rb3_indexActivity=(RadioButton) findViewById(R.id.rb3_indexActivity);
		rb4_indexActivity=(RadioButton) findViewById(R.id.rb4_indexActivity);
		rb5_indexActivity=(RadioButton) findViewById(R.id.rb5_indexActivity);
		imgv_openDrawer=(ImageView) findViewById(R.id.imgv_openDrawer);
		imgv_closeDrawer=(ImageView) findViewById(R.id.imgv_closeDrawer);
		drawerMenu=(LinearLayout) findViewById(R.id.drawerMenu);
		rg_indexActivity=(RadioGroup) findViewById(R.id.rg_indexActivity);
		et_search=(EditText) findViewById(R.id.et_search);
	}
}
