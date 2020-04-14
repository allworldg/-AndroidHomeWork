package com.example.code05;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {
    private boolean pwdSwitch=false;
    private EditText etpwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton =(Button)findViewById(R.id.btnLogin);
        final ImageView imageView=findViewById(R.id.iv_pwd_switch);
        etpwd=findViewById(R.id.et_pwd);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdSwitch = !pwdSwitch;
                if(pwdSwitch){
                    etpwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//visible
                    imageView.setImageResource(R.drawable.ic_visibility_black_24dp);
                }
                else{
                    etpwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    etpwd.setTypeface((Typeface.DEFAULT));
                }
            }
        });
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"welcome",Toast.LENGTH_SHORT).show();
                }
            });
    }
}
