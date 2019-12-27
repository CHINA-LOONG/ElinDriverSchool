package com.elin.elindriverschool.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by 17535 on 2017/2/17.
 */

public class NetworkConnectReceiver extends BroadcastReceiver {
    public NetworkConnectReceiver(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){

            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                Toast.makeText(context, "无网络可用，请检查网络连接。", Toast.LENGTH_LONG).show();
            }
//            else {
//                Intent intent1 = new Intent("readMsg");
//                context.sendBroadcast(intent1);
//            }
        }
    }
}
