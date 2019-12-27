package com.elin.elindriverschool.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.elin.elindriverschool.MainActivity;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.application.BaseApplication;

import java.util.List;

/**
 * @author: KingLone
 * @�?   �?   �?:	
 * @version 1.0
 * @创建时间�?2015-4-25 上午11:55:31
 * 
 */
public class ViewPagerAdapter extends PagerAdapter {

	// 界面列表
	private List<View> views;
	private Activity activity;
	private SharedPreferences sp_get_token;
	private String token;

	private static final String SHAREDPREFERENCES_NAME = "first_pref";

	public ViewPagerAdapter(List<View> views, Activity activity) {
		this.views = views;
		this.activity = activity;
	}

	// �?毁arg1位置的界�?
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(views.get(arg1));
	}


	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(views.get(arg1), 0);
		if (arg1 == views.size() - 1) {
			TextView tv_enterApp = (TextView) arg0
					.findViewById(R.id.tv_enter_app);
			tv_enterApp.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 设置已经引导
					setGuided();

					if (BaseApplication.getInstance().getToken()!=null&&!("".equals(BaseApplication.getInstance().getToken()))) {
						goMain();
					}else {
						goLogin();
					}
//
				}

			});
		}
		return views.get(arg1);
	}

//	private void goLogin(){
////		openActivity(LoginActivity.class);
//////		overridePendingTransition(R.anim.splash_push_left_in, R.anim.splash_push_left_out);
////		SplashActivity.this.finish();
////		Intent intent = new Intent(activity,LoginActivity.class);
//		Intent intent = new Intent(activity,LoginActivity.class);
//		activity.startActivity(intent);
//		activity.finish();
//	}
	private void goMain() {
		// 跳转
		Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}
	private void goLogin() {
		// 跳转
		Intent intent = new Intent(activity, LoginActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

	/**
	 * 
	 * method desc：设置已经引导过了，下次启动不用再次引导
	 */
	private void setGuided() {
		SharedPreferences preferences = activity.getSharedPreferences(
				SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		// 存入数据
		editor.putBoolean("isFirstIn", false);
		// 提交修改
		editor.commit();
	}

	// 判断是否由对象生成界�?
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

}
