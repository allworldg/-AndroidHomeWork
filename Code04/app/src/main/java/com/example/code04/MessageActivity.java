package com.example.code04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        TextView textView=findViewById(R.id.acceptMessage);
        Intent intent=getIntent();
        String accept_Message=intent.getStringExtra("sendMessage");
        if(accept_Message != null){
            if(textView != null){
                textView.setText(accept_Message);
            }
        }

    }
}
