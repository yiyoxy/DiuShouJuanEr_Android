package com.bili.diushoujuaner.presenter.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.bili.diushoujuaner.model.actionhelper.respon.ActionRespon;
import com.bili.diushoujuaner.presenter.presenter.LoginActivityPresenter;
import com.bili.diushoujuaner.utils.ConstantUtil;
import com.bili.diushoujuaner.model.apihelper.request.UserAccountReq;
import com.bili.diushoujuaner.model.actionhelper.action.CustomSessionAction;
import com.bili.diushoujuaner.model.callback.ActionStringCallbackListener;
import com.bili.diushoujuaner.presenter.base.BasePresenter;
import com.bili.diushoujuaner.presenter.view.ILoginView;
import com.bili.diushoujuaner.utils.StringUtil;

/**
 * Created by BiLi on 2016/3/10.
 */
public class LoginActivityPresenterImpl extends BasePresenter<ILoginView> implements LoginActivityPresenter {

    public LoginActivityPresenterImpl(ILoginView baseView, Context context) {
        super(baseView, context);
    }

    @Override
    public void getUserLogin(String mobile, String psd){
        if(!validate(mobile, psd)){
            return;
        }
        showLoading(ConstantUtil.LOADING_TOP,"登录中...");

        UserAccountReq userAccountReq = new UserAccountReq();
        userAccountReq.setMobile(mobile);
        userAccountReq.setPassword(psd);
        CustomSessionAction.getInstance(context).getUserLogin(userAccountReq, new ActionStringCallbackListener<ActionRespon<Void>>() {
            @Override
            public void onSuccess(ActionRespon<Void> result) {
                if(showMessage(result.getRetCode(), result.getMessage())){
                    if(isBindViewValid()){
                        getBindView().loginSuccess();
                    }
                }
                hideLoading(ConstantUtil.LOADING_TOP);
            }

            @Override
            public void onFailure(int errorCode) {
                hideLoading(ConstantUtil.LOADING_TOP);
                showError(errorCode);
            }
        });
    }

    private boolean validate(String mobile, String psd) {
        if (TextUtils.isEmpty(mobile)) {
            showWarning("请输入手机号");
            return false;
        }
        if (!(StringUtil.checkMobile(mobile))) {
            showWarning("请输入正确的手机号码");
            return false;
        }
        if (TextUtils.isEmpty(psd)) {
            showWarning("请输入登录密码");
            return false;
        }
        return true;
    }

}
