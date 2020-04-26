package com.example.code05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {
    private boolean pwdSwitch = false;
    private EditText etpwd;
    private EditText etAccount;
    private CheckBox cbRemembered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.btnLogin);
        final ImageView imageView = findViewById(R.id.iv_pwd_switch);
        etpwd = findViewById(R.id.et_pwd);
        etAccount = findViewById(R.id.et_account);
        cbRemembered = findViewById(R.id.cb_remember_pwd);
        String spfileName = getResources()
                .getString(R.string.shared_preferenced_file_name);
        String spAccount = getResources()
                .getString(R.string.login_acount_name);
        String spPasswd = getResources()
                .getString(R.string.login_passwd);
        String spRememberPasswd = getResources()
                .getString(R.string.login_remember_passwd);
        SharedPreferences sharedPreferences = getSharedPreferences(spfileName,
                MODE_PRIVATE);
        String getAccount = sharedPreferences.getString(spAccount,null);
        String getPasswd = sharedPreferences.getString(spPasswd,null);
        Boolean rememberedPasswd = sharedPreferences.getBoolean(
                spRememberPasswd,false
        );
        if(getAccount != null && !TextUtils.isEmpty(getAccount)){
            etAccount.setText(getAccount);
        }
        if(getPasswd != null && !TextUtils.isEmpty(getPasswd)){
            etpwd.setText(getPasswd);
        }
        cbRemembered.setChecked(rememberedPasswd);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "welcome", Toast.LENGTH_SHORT).show();
                String spFileName = getResources()
                        .getString(R.string.shared_preferenced_file_name);
                String accountKey = getResources()
                        .getString(R.string.login_acount_name);
                String passwdKey = getResources()
                        .getString(R.string.login_passwd);
                String rememberedKey = getResources()
                        .getString(R.string.login_remember_passwd);

                SharedPreferences sp = getSharedPreferences(
                        spFileName, Context.MODE_PRIVATE);
                SharedPreferences.Editor spEdit = sp.edit();
                if (cbRemembered.isChecked()) {//if you want to save account
                    String passwd = etpwd.getText().toString();
                    String account = etAccount.getText().toString();

                    spEdit.putString(accountKey, account);
                    spEdit.putString(passwdKey, passwd);
                    spEdit.putBoolean(rememberedKey, true);
                    spEdit.commit();


                } else {
                    spEdit.remove(accountKey);
                    spEdit.remove(passwdKey);
                    spEdit.remove(rememberedKey);
                    spEdit.apply();
                    spEdit.commit();
                }

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//隐藏密码
                pwdSwitch = !pwdSwitch;
                if (pwdSwitch) {
                    etpwd.setInputType
                            (InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//visible
                    imageView.setImageResource
                            (R.drawable.ic_visibility_black_24dp);
                } else {
                    etpwd.setInputType
                            (InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    etpwd.setTypeface
                            ((Typeface.DEFAULT));
                }
            }
        });


    }
}
