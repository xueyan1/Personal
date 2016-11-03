package com.xueyan.personal.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class SMSReceiver  extends BroadcastReceiver{
    private String TAG="SMSReceiver";

    public void SMSReceiver(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        Object[] pdus = (Object[]) extras.get("pdus");
        SmsMessage[] smsMessage = new SmsMessage[pdus.length];
        for (int i = 0; i <pdus.length ; i++) {
            smsMessage[i]=SmsMessage.createFromPdu((byte[]) pdus[i]);
            String displayMessageBody = smsMessage[i].getDisplayMessageBody();
            Log.i(TAG, "onReceive: "+displayMessageBody);
        }

    }
}
