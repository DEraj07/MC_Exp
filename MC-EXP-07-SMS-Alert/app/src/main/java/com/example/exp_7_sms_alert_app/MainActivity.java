package com.example.exp_7_sms_alert_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static MainActivity instance;
    private ListView lvMessages;
    private ArrayList<SMSModel> smsList = new ArrayList<>();
    private SMSAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        lvMessages = findViewById(R.id.lv_messages);
        adapter = new SMSAdapter();
        lvMessages.setAdapter(adapter);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 1);
        } else {
            fetchInboxMessages();
        }
    }

    private void fetchInboxMessages() {
        smsList.clear();
        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uriSms, new String[]{"address", "body"}, null, null, "date DESC");
        if (cursor != null && cursor.moveToFirst()) {
            int count = 0;
            do {
                smsList.add(new SMSModel(cursor.getString(0), cursor.getString(1)));
                count++;
            } while (cursor.moveToNext() && count < 15);
            cursor.close();
            adapter.notifyDataSetChanged();
        }
    }

    public void updateList(String sender, String body) {
        smsList.add(0, new SMSModel(sender, body));
        adapter.notifyDataSetChanged();
    }

    class SMSModel {
        String sender, body;
        boolean isExpanded = false;
        SMSModel(String s, String b) { this.sender = s; this.body = b; }
    }

    class SMSAdapter extends BaseAdapter {
        @Override
        public int getCount() { return smsList.size(); }
        @Override
        public Object getItem(int i) { return smsList.get(i); }
        @Override
        public long getItemId(int i) { return i; }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) view = LayoutInflater.from(MainActivity.this).inflate(R.layout.sms_item, viewGroup, false);

            TextView tvSender = view.findViewById(R.id.tv_sender);
            TextView tvBody = view.findViewById(R.id.tv_body);
            ImageButton btnDelete = view.findViewById(R.id.btn_delete);

            SMSModel sms = smsList.get(i);
            tvSender.setText(sms.sender);
            tvBody.setText(sms.body);

            // Expand/Collapse logic based on click
            tvBody.setMaxLines(sms.isExpanded ? Integer.MAX_VALUE : 1);

            view.setOnClickListener(v -> {
                sms.isExpanded = !sms.isExpanded;
                notifyDataSetChanged();
            });

            // Delete item from list
            btnDelete.setOnClickListener(v -> {
                smsList.remove(i);
                notifyDataSetChanged();
            });

            return view;
        }
    }
}