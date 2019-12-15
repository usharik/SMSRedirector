package ru.usharik.smsredirector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

    public SmsReceiver() {
        Log.i("SmsReceiver", "SmsReceiver created");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("SmsReceiver", "New message!!!");

        App app = (App) context.getApplicationContext();

        Bundle data = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            app.smsMessagePublishSubject.onNext(smsMessage);
        }
    }
}
