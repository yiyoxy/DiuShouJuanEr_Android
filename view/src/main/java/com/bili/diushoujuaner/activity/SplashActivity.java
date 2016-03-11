package com.bili.diushoujuaner.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bili.diushoujuaner.R;
import com.bili.diushoujuaner.model.entities.UserDto;
import com.bili.diushoujuaner.utils.UserInfoPreference;
import com.bili.diushoujuaner.widget.RevealTextView;

/**
 * Created by BiLi on 2016/2/29.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((RevealTextView) findViewById(R.id.textSlogon)).setAnimatedText(getResources().getString(R.string.slogon));
            }
        }, 800);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showNextActivity();
            }
        },3000);
    }

    private void showNextActivity(){
        UserDto userDto = UserInfoPreference.getInstance().getUserInfo();
        if(userDto.getAccessToken().length() <= 0){
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }else{
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        finish();
    }
}