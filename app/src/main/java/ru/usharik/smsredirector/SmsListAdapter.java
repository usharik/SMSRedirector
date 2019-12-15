package ru.usharik.smsredirector;

import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SmsListAdapter extends RecyclerView.Adapter<SmsListAdapter.MyViewHolder> {
    private List<SmsMessage> messages;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public MyViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

    public SmsListAdapter(List<SmsMessage> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public SmsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView smsFrom = holder.view.findViewById(R.id.sms_from);
        smsFrom.setText(messages.get(position).getDisplayOriginatingAddress());
        TextView smsText = holder.view.findViewById(R.id.sms_text);
        smsText.setText(messages.get(position).getDisplayMessageBody());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
