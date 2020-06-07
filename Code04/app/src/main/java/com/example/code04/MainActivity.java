package com.example.code04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etMessage=findViewById(R.id.message);

        Button btnSend=(Button)findViewById(R.id.send_Message);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString();
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(MainActivity.this,MessageActivity.class);
                intent.putExtra("sendMessage",message);
                startActivity(intent);
            }
        });
    }
}
