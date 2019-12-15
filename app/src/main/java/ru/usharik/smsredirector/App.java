package ru.usharik.smsredirector;

import android.app.Application;
import android.telephony.SmsMessage;
import io.reactivex.subjects.PublishSubject;

public class App extends Application {

    public PublishSubject<SmsMessage> smsMessagePublishSubject;

    @Override
    public void onCreate() {
        super.onCreate();
        smsMessagePublishSubject = PublishSubject.create();
    }
}
