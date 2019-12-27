package com.elin.elindriverschool.util;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

/**
 * @author: KingLone
 * @�?   �?   �?:	
 * @version 1.0
 * @创建时间�?2015-4-28 下午3:00:16
 *
 */
public class MobilePhoneUtils implements Callback.CommonCallback<String> {
	private static Context context;
	private static MobilePhoneUtils mMobilePhoneUtils;

	private MobilePhoneUtils() {

	}

	public static MobilePhoneUtils newInstance() {
		if (mMobilePhoneUtils == null) {
			mMobilePhoneUtils = new MobilePhoneUtils();
			return mMobilePhoneUtils;
		}
		return mMobilePhoneUtils;
	}

	/**
	 *
	 * @Title: isMoblePhoneNum
	 * @�?       �?:判断是否是正确的手机�?
	 * @�?       �?: @param phoneNum
	 * @�?       �?: @return   
	 * @return boolean    返回类型 
	 * @throws
	 */
	public static boolean isMoblePhoneNum(String phoneNum) {
		 /* 
	    移动�?134�?135�?136�?137�?138�?139�?150�?151�?157(TD)�?158�?159�?187�?188 
	    联�?�：130�?131�?132�?152�?155�?156�?185�?186 
	    电信�?133�?153�?180�?189、（1349卫�?�） 
	    总结起来就是第一位必定为1，第二位必定�?3�?5�?8，其他位置的可以�?0-9 
	    */
//		phoneNum.matches("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//		Matcher matcher = pattern.matcher(phoneNum);
		return phoneNum.matches("1[3|4|5|7|8|][0-9]{9}");
	}

	/**
	 * 打电电话
	 */
	public static void makeCall(final String tel,String name, final Context mContext) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("确定拨打"+name+"的电话吗？");
//		builder.setTitle("操作提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
				if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				mContext.startActivity(intent);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	
	/**
	 * 
	 * @Title: isMoblePhoneNum 
	 * @�?       �?:判断是否是正确的手机�?
	 * @�?       �?: @param phoneNum
	 * @�?       �?: @return   
	 * @return boolean    返回类型 
	 * @throws
	 */
//	public void isSendSMS(String phoneNum,Context context,String type){
//		this.context = context;
////		if(phoneNum == null||phoneNum.equals("")){
////			Toast.makeText(context, "请输入手机号�?", 1).show();
////		}else if(phoneNum.length()!=11){
////			Toast.makeText(context, "您输入的手机号码不正�?", 1).show();
////		}else if(phoneNum.matches("1[3|4|5|7|8|][0-9]{9}")){
//		HttpUtils sendMessage = new HttpUtils();
//		RequestParams params = new RequestParams();
//		params.addBodyParameter("phone",phoneNum);
//		params.addBodyParameter("type",type);
//		sendMessage.send(HttpMethod.POST,Constant.URL+Constant.SENDSMS_URL,params,this);
////		}else{
////			Toast.makeText(context, "手机号码格式不正�?", 1).show();
////		}
//	}


	@Override
	public void onSuccess(String result) {
		if(result.equals("")){
			Toast.makeText(context, "您的网络不给力哦", Toast.LENGTH_SHORT).show();
		}else{
			try {
				String result1 = new JSONObject(result).getString("result");
				if(result1.equals("success")){
					Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
				}else if(result1.equals("wrong")){
					Toast.makeText(context, "服务器忙", Toast.LENGTH_SHORT).show();
				}else if(result1.equals("fail")){
					Toast.makeText(context, "该手机号还未注册，请注册", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(context, "服务器正在维护，请稍等哦", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onError(Throwable ex, boolean isOnCallback) {
		Toast.makeText(context, "网络不给力", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCancelled(CancelledException cex) {

	}

	@Override
	public void onFinished() {

	}
}
