package ru.usharik.smsredirector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.telephony.SmsMessage;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.usharik.smsredirector.databinding.ActivityMainBinding;
import ru.usharik.smsredirector.rest.ExportRest;
import ru.usharik.smsredirector.rest.SmsDto;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 10;

    private ActivityMainBinding binding;

    private List<SmsMessage> messages;
    private SmsListAdapter smsListAdapter;

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (!isSmsPermissionGranted()) {
            requestReadAndSendSmsPermission();
        }

        binding.endpointUrl.setText("http://localhost:8080");

        messages = new ArrayList<>();
        smsListAdapter = new SmsListAdapter(messages);

        binding.myRecyclerView.setHasFixedSize(true);
        binding.myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.myRecyclerView.setAdapter(smsListAdapter);

        App app = (App) getApplicationContext();
        app.smsMessagePublishSubject
                .map(smsMessage -> {
                    String message = "Sender : " + smsMessage.getDisplayOriginatingAddress()
                            + "Email From: " + smsMessage.getEmailFrom()
                            + "Emal Body: " + smsMessage.getEmailBody()
                            + "Display message body: " + smsMessage.getDisplayMessageBody()
                            + "Time in millisecond: " + smsMessage.getTimestampMillis()
                            + "Message: " + smsMessage.getMessageBody();
                    Log.i("MainActivity", "New message " + message);

                    messages.add(smsMessage);
                    smsListAdapter.notifyDataSetChanged();
                    return new SmsDto(smsMessage.getDisplayOriginatingAddress(),
                            smsMessage.getDisplayMessageBody(), smsMessage.getTimestampMillis());
                })
                .observeOn(Schedulers.io())
                .subscribe(smsDto -> {
                    try {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(binding.endpointUrl.getText().toString())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        retrofit.create(ExportRest.class)
                                .postJson(smsDto)
                                .execute();
                    } catch (Exception ex) {
                        Log.e("MainActivity", "Error", ex);
                    }
                });
    }

    /**
     * Check if we have SMS permission
     */
    public boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request runtime SMS permission
     */
    private void requestReadAndSendSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, SMS_PERMISSION_CODE);
    }
}
