package com.zlz.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.day1105_class_viewpagerfragment.R;
import com.zlz.classentity.ContentDataList;
import com.zlz.helpers.HttpUtils;
import com.zlz.helpers.HttpUtils.getResultCallback;
import com.zlz.helpers.JSON_Parser;
import com.zlz.helpers.appURLFinal;

public class ContentActivity extends Activity {
	private WebView wv_contet;
	private ProgressDialog pd_content;
	private ContentDataList condatalist;
	private TextView tv_contenttitle, textView_content_create_time,
			textView_content_source;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_webview);
		initUI();
		loadContentData();
	}

	public void clickButton(View v) {
		switch (v.getId()) {
		case R.id.imageView_content_back:
			this.finish();
			break;
		case R.id.imageView_content_collect:

			break;
		case R.id.imageView_content_share:
			shareDialog();
			break;
		default:
			break;
		}
	}

	private void shareDialog() {
		// TODO Auto-generated method stub
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("分享至");
		builder.setItems(new String[] { "短信", "邮件" }, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:
					Intent intent_mes = new Intent(Intent.ACTION_SEND);
					intent_mes.setType("text/plain");
					intent_mes.putExtra(Intent.EXTRA_TEXT, condatalist
							.getContentData().getTitle()
							+ ""
							+ condatalist.getContentData().getWeiboUrl());
					startActivity(Intent.createChooser(intent_mes, getTitle()));
					break;
				case 1:
					Intent intent_email=new Intent(Intent.ACTION_SEND);
					intent_email.setType("text/plain");
					String  emailSubject =  "分享文章" ;
					intent_email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject); 
					intent_email.putExtra(Intent.EXTRA_TEXT, condatalist
							.getContentData().getTitle()
							+ ""
							+ condatalist.getContentData().getWeiboUrl());
					startActivityForResult(Intent.createChooser(intent_email,  "请选择邮件发送软件" ), 1001 );  
					break;

				default:
					break;
				}
			}
		});
		Dialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();

	}

	private void loadContentData() {
		int num = getIntent().getExtras().getInt("num");
		String id_ = getIntent().getStringExtra("id_");
		pd_content.show();
		if (num == 0) {
			getIndexContent(id_);
		} else {
			getBodyContent(id_);
		}
	}

	private void getIndexContent(String id_) {
		HttpUtils.getPostResult(appURLFinal.CONTENT_URL,
				appURLFinal.getCONTENT(id_), new getResultCallback() {

					@Override
					public void getMessage(String message) {
						// TODO Auto-generated method stub
						condatalist = JSON_Parser.parseJSONContent(message);
						System.out.println(condatalist.getContentData()
								.getTitle());
						tv_contenttitle.setText(condatalist.getContentData()
								.getTitle());
						textView_content_create_time.setText(condatalist
								.getContentData().getCreate_time());
						textView_content_source.setText(condatalist
								.getContentData().getSource());
						wv_contet.loadDataWithBaseURL(null, condatalist
								.getContentData().getWap_content(),
								"text/html", "utf-8", null);
						// 调用布局管理器,使控件自适应手机屏幕,使所有内容等宽显示,可以解决图片过大问题,但是有可能让页面中连接失效.
						wv_contet.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
						pd_content.dismiss();
					}
				});
	}

	private void getBodyContent(String id_) {
		HttpUtils.getPostResult(appURLFinal.CONTENT_URL,
				appURLFinal.getCONTENT(id_), new getResultCallback() {

					@Override
					public void getMessage(String message) {
						// TODO Auto-generated method stub
						condatalist = JSON_Parser.parseJSONContent(message);
						System.out.println(condatalist.getContentData()
								.getTitle());
						tv_contenttitle.setText(condatalist.getContentData()
								.getTitle());
						textView_content_create_time.setText(condatalist
								.getContentData().getCreate_time());
						textView_content_source.setText(condatalist
								.getContentData().getSource());
						wv_contet.loadDataWithBaseURL(null, condatalist
								.getContentData().getWap_content(),
								"text/html", "utf-8", null);
						// 调用布局管理器,使控件自适应手机屏幕,使所有内容等宽显示,可以解决图片过大问题,但是有可能让页面中连接失效.
						wv_contet.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
						pd_content.dismiss();
					}
				});
	}

	private void initUI() {
		// TODO Auto-generated method stub
		wv_contet = (WebView) findViewById(R.id.wv_content);
		pd_content = new ProgressDialog(this);
		pd_content.setMessage("正在加载...");
		tv_contenttitle = (TextView) findViewById(R.id.tv_contenttitle);
		textView_content_create_time = (TextView) findViewById(R.id.textView_content_create_time);
		textView_content_source = (TextView) findViewById(R.id.textView_content_source);
	}
}
