package com.zlz.activity;

import com.example.day1105_class_viewpagerfragment.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class WelcomeActivity extends Activity implements AnimationListener{
	private ImageView iv_wel;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_welcome);
	iv_wel=(ImageView) findViewById(R.id.iv_wel);
	dynamiPic();
}
private void dynamiPic() {
	// TODO Auto-generated method stub
	Animation animation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); // ��ͼƬ�Ŵ�1.2���������Ŀ�ʼ����
	animation.setDuration(2000); // ��������ʱ��
	animation.setFillAfter(true); // ����������ͣ���ڽ�����λ��
	animation.setAnimationListener(this); // ��Ӷ�������
	iv_wel.startAnimation(animation); // ��������
}
@Override
public void onAnimationEnd(Animation arg0) {
	// TODO Auto-generated method stub
	startActivity(new Intent(this,MainActivity.class));
	this.finish();
}
@Override
public void onAnimationRepeat(Animation arg0) {
	// TODO Auto-generated method stub
	
}
@Override
public void onAnimationStart(Animation arg0) {
	// TODO Auto-generated method stub
	
}
}
