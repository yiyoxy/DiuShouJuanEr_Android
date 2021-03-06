package com.bili.diushoujuaner.activity;

import android.content.Intent;
import android.os.Handler;

import com.bili.diushoujuaner.R;
import com.bili.diushoujuaner.base.BaseActivity;
import com.bili.diushoujuaner.presenter.presenter.SplashActivityPresenter;
import com.bili.diushoujuaner.presenter.presenter.impl.SplashActivityPresenterImpl;
import com.bili.diushoujuaner.presenter.view.ISplashView;
import com.bili.diushoujuaner.utils.ConstantUtil;
import com.bili.diushoujuaner.widget.RevealTextView;

/**
 * Created by BiLi on 2016/2/29.
 */
public class SplashActivity extends BaseActivity<SplashActivityPresenter> implements ISplashView {

    @Override
    public void beforeInitView() {
        basePresenter = new SplashActivityPresenterImpl(this,context);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void setViewStatus() {
        setTintStatusColor(R.color.TRANSPARENT);
        ((RevealTextView) findViewById(R.id.txtSlogon)).setAnimatedText(getResources().getString(R.string.slogon));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getBindPresenter().getNextActivity();
            }
        }, 3000);
    }

    @Override
    public void showNextActivity(int showType) {
        switch (showType){
            case ConstantUtil.SHOW_TYPE_LOGIN:
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                break;
            case ConstantUtil.SHOW_TYPE_MAIN:
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                break;
        }
        finish();
    }
}
