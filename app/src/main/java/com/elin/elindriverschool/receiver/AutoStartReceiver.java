package com.elin.elindriverschool.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elin.elindriverschool.MainActivity;

/**
 * Created by 17535 on 2017/10/23.
 */

public class AutoStartReceiver extends BroadcastReceiver {
    public AutoStartReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
