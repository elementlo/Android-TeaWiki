package com.zlz.mylistfragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.day1105_class_viewpagerfragment.R;
import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.zlz.activity.ContentActivity;
//import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zlz.adaptar.MyAdaptar;
import com.zlz.adaptar.MyPagerAdaptar;
import com.zlz.classentity.MyData;
import com.zlz.classentity.Tea;
import com.zlz.helpers.HttpUtils;
import com.zlz.helpers.HttpUtils.getResultCallback;
import com.zlz.helpers.JSON_Parser;
import com.zlz.helpers.appURLFinal;
import com.zlz.myviewpager.MyViewPager;

public class MyListFragment extends PullToRefreshListFragment implements OnRefreshListener<ListView> {
	private int num;
	private HashMap<String, String> hm_URL;
	private ImageView iv_header,dot_image;
	private TextView tv_header;
	private View v_headerView;
	private List<View> pagerList;
	private List<Tea> list_head, list_body;
	private View header = null;
	private View footView = null;
	private MyData data;
	private ViewGroup ll_heading;
	private int lastvisibleposition = 0;
	private MListviewScoListener mListener;
	private MyAdaptar myAdaptar;
	// private int[] img={R.drawable.z1,R.drawable.z2,R.drawable.z3};
	private MyViewPager vp_header;
	private int item,page=2;
	private Timer timer;
	//private static PullToRefreshListFragment mPullRefreshListFragment;
	private PullToRefreshListView mPullRefreshListView;
//	private PullToRefreshListFragment mPullRefreshListFragment;
//	private PullToRefreshListView mPullRefreshListView;

	public static MyListFragment newInstance(int num) {
		MyListFragment mListFragment = new MyListFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("num", num);
		mListFragment.setArguments(bundle);// 为什么不用构造方法来实例化,是因为activity在横竖屏切换时会重构fragment导致传参失败,但是用bundle传参会保存下来
		return mListFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		num = bundle == null ? 1 : bundle.getInt("num");
		mListener = new MListviewScoListener(num);
		getHmURL(1);
		// int i=1;
		// if (num == 0) {
		// hm_URL = appURLFinal.getTOUTIAO(i);
		// } else if (num == 1) {
		// hm_URL = appURLFinal.getBAIKE(i);
		// } else if (num == 2) {
		// hm_URL = appURLFinal.getJINGYING(i);
		// } else if (num == 3) {
		// hm_URL = appURLFinal.getZIXUN(i);
		// } else if (num == 4) {
		// hm_URL = appURLFinal.getSHUJU(i);
		// }
	}

	public HashMap<String, String> getHmURL(int i) {
		if (num == 0) {
			hm_URL = appURLFinal.getTOUTIAO(i);
		} else if (num == 1) {
			hm_URL = appURLFinal.getBAIKE(i);
		} else if (num == 2) {
			hm_URL = appURLFinal.getJINGYING(i);
		} else if (num == 3) {
			hm_URL = appURLFinal.getZIXUN(i);
		} else if (num == 4) {
			hm_URL = appURLFinal.getSHUJU(i);
		}
		return hm_URL;
	}
	private Handler autoscrollHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			vp_header.setCurrentItem(item);
			if (item==data.getData().size()-1) {
				item=0;
			}else {
				item++;
			}
		};
	};

	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View v=inflater.inflate(R.layout.listfragment_listview, container,
	// false);
	// //lv_list.addHeaderView(header);
	// return v;
	// }


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		footView = View.inflate(getActivity(), R.layout.listview_footerview,
				null);
		getListView().addFooterView(footView);
		mPullRefreshListView = this.getPullToRefreshListView();
		mPullRefreshListView.setOnRefreshListener(this);
		mPullRefreshListView.getRefreshableView();
		// 可利用自定义view，组合控件，实现对listview头部内容的封装
		if (num == 0) {
			// setListAdapter(null);
			setHeaderData();
			setHeadlineData();
		} else {
			//getListView().removeHeaderView(header);
			setBodyData();
		}
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if(timer!=null){
		timer.cancel();
		}
		// HttpUtils.stopThread();
		// getListView().removeFooterView(footView);
	}

	// num==0:百科 num==1:咨询 num==2:经营
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getListView().setOnScrollListener(mListener);
	}

	private void setHeadlineData() {
		RequestHeadLineData requestHeadLineData = new RequestHeadLineData();
		HttpUtils.getPostResult(appURLFinal.HEAD_LINES, hm_URL,
				requestHeadLineData);
	}

	class RequestHeadLineData implements getResultCallback {

		@Override
		public void getMessage(String message) {
			// TODO Auto-generated method stub
			list_head = JSON_Parser.parseJSON(message);
			myAdaptar = new MyAdaptar(getActivity(), list_head);
			setListAdapter(myAdaptar);
		}

	}

	private void setBodyData() {
		// TODO Auto-generated method stub

		HttpUtils.getPostResult(appURLFinal.BASE_URL, hm_URL,
				new getResultCallback() {

					@Override
					public void getMessage(String message) {
						// TODO Auto-generated method stub
						list_body = JSON_Parser.parseJSON(message);
						myAdaptar = new MyAdaptar(getActivity(), list_body);
						setListAdapter(myAdaptar);
					}
				});

	}

	class RequestHeaderData implements getResultCallback {
		int tag;

		public void getMessage(String message) {
			pagerList = new ArrayList<View>();
			data = JSON_Parser.parseJSONHeader(message);
			int i;
			tv_header.setText(data.getData().get(0).getTitle());
			for (i = 0; i < data.getData().size(); i++) {
				initViews(pagerList, data, i);
			}
			getListView().addHeaderView(header);
			MyPagerAdaptar myPagerAdaptar = new MyPagerAdaptar(pagerList);
			vp_header.setAdapter(myPagerAdaptar);
		}
	}

	public void setHeaderData() {
		// final List<View> views=new ArrayList<View>();//动态添加XML到viewpager中
		header = View.inflate(getActivity(), R.layout.header_viewpager, null);
		vp_header = (MyViewPager) header.findViewById(R.id.vp_header);
		tv_header = (TextView) header.findViewById(R.id.tv_headertxt);
		ll_heading = (ViewGroup) header.findViewById(R.id.ll_headimg);
		RequestHeaderData requestHeaderData = new RequestHeaderData();
		HttpUtils.getGetResult(appURLFinal.HEADER_PIC, requestHeaderData);
		autoScroll();
		vp_header.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				tv_header.setText(data.getData().get(arg0).getTitle());
				item=arg0;
				selectDot(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}


	private void autoScroll() {
	timer=new Timer();
	timer.schedule(new TimerTask() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			autoscrollHandler.sendEmptyMessage(0);
		}
	}, 3000, 3000);
}
	public void initViews(List<View> pagerList, MyData data, int i) {
		// TODO Auto-generated method stub
		v_headerView = View.inflate(getActivity(),
				R.layout.listview_headerview, null);
		selectDot(0);
		iv_header = (ImageView) v_headerView.findViewById(R.id.iv_headerimg);
		// tv_header=(TextView)
		// v_headerView.findViewById(R.id.tv_headertxt);
		HttpUtils.getNetBytes(getActivity(), data.getData().get(i).getImage(),
				iv_header);
		// tv_header.setText(data.getData().get(i).getTitle());
		pagerList.add(v_headerView);
	}

	public void selectDot(int j) {
		ll_heading.removeAllViews();
		for (int i = 0; i < 3; i++) {
			dot_image = new ImageView(getActivity());
			dot_image.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			if (i == j) {// the picture of selected small dot is different

				dot_image.setImageResource(R.drawable.selected);

			} else {

				dot_image.setImageResource(R.drawable.un_selected);

			}

			ll_heading.addView(dot_image);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String id_;
		if(num==0){
			id_=list_head.get(position).getId();
		}else {
			id_=list_body.get(position).getId();
		}
		Intent cIntent=new Intent(getActivity(),ContentActivity.class);
		cIntent.putExtra("id_", id_);
		cIntent.putExtra("num", num);
		startActivity(cIntent);
		Toast.makeText(getActivity(), position+"*****"+id_, 0).show();
	}

	class MListviewScoListener implements OnScrollListener {
		private int num;

		public MListviewScoListener(int num) {
			this.num = num;
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			if (firstVisibleItem != 0) {
				if (totalItemCount == firstVisibleItem + visibleItemCount) {
					if (!(lastvisibleposition == totalItemCount - 1)) {
						Toast.makeText(getActivity(), "加载更多", 0).show();
						if (num == 0) {

							HttpUtils.getPostResult(appURLFinal.HEAD_LINES,
									getHmURL(page), new getResultCallback() {

										@Override
										public void getMessage(String message) {
											// TODO Auto-generated method stub
											list_head.addAll(JSON_Parser
													.parseJSON(message));
											// myAdaptar = new
											// MyAdaptar(getActivity(), list);
											// setListAdapter(myAdaptar);
											myAdaptar.notifyDataSetChanged();
										}
									});
						} else {
							HttpUtils.getPostResult(appURLFinal.BASE_URL,
									getHmURL(page), new getResultCallback() {

										@Override
										public void getMessage(String message) {
											// TODO Auto-generated method stub
											list_body.addAll(JSON_Parser
													.parseJSON(message));
											myAdaptar.notifyDataSetChanged();
										}
									});
						}
						page++;
						lastvisibleposition = totalItemCount - 1;
					}
				}
			}
		}

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		//setHeaderData();
		if(num==0){
		HttpUtils.getPostResult(appURLFinal.HEAD_LINES, hm_URL, new getResultCallback() {
			
			@Override
			public void getMessage(String message) {
				// TODO Auto-generated method stub
				list_head = JSON_Parser.parseJSON(message);
				myAdaptar = new MyAdaptar(getActivity(), list_head);
				setListAdapter(myAdaptar);
				mPullRefreshListView.onRefreshComplete();
				Toast.makeText(getActivity(), "暂无更多推送", 0).show();
			}
		});
		}else {
			HttpUtils.getPostResult(appURLFinal.BASE_URL, hm_URL, new getResultCallback() {
				
				@Override
				public void getMessage(String message) {
					// TODO Auto-generated method stub
					list_body = JSON_Parser.parseJSON(message);
					myAdaptar = new MyAdaptar(getActivity(), list_body);
					setListAdapter(myAdaptar);
					mPullRefreshListView.onRefreshComplete();
					Toast.makeText(getActivity(), "暂无更多推送", 0).show();
				}
			});
		}
	}
}
