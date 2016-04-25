package com.bili.diushoujuaner.presenter.presenter.impl;

import android.content.Context;

import com.bili.diushoujuaner.model.actionhelper.action.ApplyAction;
import com.bili.diushoujuaner.model.actionhelper.action.ContactAction;
import com.bili.diushoujuaner.model.actionhelper.action.FeedBackAction;
import com.bili.diushoujuaner.model.actionhelper.action.UserInfoAction;
import com.bili.diushoujuaner.model.actionhelper.respon.ActionRespon;
import com.bili.diushoujuaner.model.apihelper.request.AutographModifyReq;
import com.bili.diushoujuaner.model.apihelper.request.FeedBackReq;
import com.bili.diushoujuaner.model.apihelper.request.FriendAddReq;
import com.bili.diushoujuaner.model.apihelper.request.MemberNameUpdateReq;
import com.bili.diushoujuaner.model.apihelper.request.PartyIntroduceUpdateReq;
import com.bili.diushoujuaner.model.apihelper.request.PartyNameUpdateReq;
import com.bili.diushoujuaner.model.apihelper.request.RemarkUpdateReq;
import com.bili.diushoujuaner.model.callback.ActionStringCallbackListener;
import com.bili.diushoujuaner.model.eventhelper.UpdateAutographEvent;
import com.bili.diushoujuaner.model.eventhelper.UpdatePartyEvent;
import com.bili.diushoujuaner.model.tempHelper.ChattingTemper;
import com.bili.diushoujuaner.presenter.base.BasePresenter;
import com.bili.diushoujuaner.presenter.base.IBaseView;
import com.bili.diushoujuaner.presenter.presenter.ContentEditActivityPresenter;
import com.bili.diushoujuaner.utils.ConstantUtil;
import com.bili.diushoujuaner.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by BiLi on 2016/4/6.
 */
public class ContentEditActivityPresenterImpl extends BasePresenter<IBaseView> implements ContentEditActivityPresenter {

    public ContentEditActivityPresenterImpl(IBaseView baseView, Context context) {
        super(baseView, context);
    }

    @Override
    public void getFriendRemarkUpdate(long friendNo, String remark) {
        if(StringUtil.isEmpty(remark)){
            showWarning("备注名不能为空");
            return;
        }
        showLoading(ConstantUtil.LOADING_CENTER, "正在发送");
        RemarkUpdateReq remarkUpdateReq = new RemarkUpdateReq(remark, friendNo);
        ContactAction.getInstance(context).getFriendRemarkUpdate(remarkUpdateReq, new ActionStringCallbackListener<ActionRespon<Void>>() {
            @Override
            public void onSuccess(ActionRespon<Void> result) {
                hideLoading(ConstantUtil.LOADING_CENTER);
                if(showMessage(result.getRetCode(), result.getMessage())){
                    showWarning("备注名修改成功");
                    if(isBindViewValid()){
                        getBindView().finishView();
                    }
                }
            }

            @Override
            public void onFailure(int errorCode) {
                hideLoading(ConstantUtil.LOADING_CENTER);
                showError(errorCode);
            }
        });
    }

    @Override
    public void getFriendAdd(long friendNo, String content) {
        if(StringUtil.isEmpty(content)){
            showWarning("验证信息不能为空");
            return;
        }
        showLoading(ConstantUtil.LOADING_CENTER, "正在发送");
        FriendAddReq friendAddReq = new FriendAddReq(friendNo, content);
        ApplyAction.getInstance(context).getFriendAdd(friendAddReq, new ActionStringCallbackListener<ActionRespon<Void>>() {
            @Override
            public void onSuccess(ActionRespon<Void> result) {
                hideLoading(ConstantUtil.LOADING_CENTER);
                if(showMessage(result.getRetCode(), result.getMessage())){
                    showWarning("请求成功，等待验证");
                    if(isBindViewValid()){
                        getBindView().finishView();
                    }
                }
            }

            @Override
            public void onFailure(int errorCode) {
                hideLoading(ConstantUtil.LOADING_CENTER);
                showError(errorCode);
            }
        });
    }

    @Override
    public void getPartyAdd(long partyNo, String content) {
        if(StringUtil.isEmpty(content)){
            showWarning("验证信息不能为空");
            return;
        }
    }

    @Override
    public void publishNewPartyName(String partyName) {
        if(StringUtil.isEmpty(partyName)){
            showWarning("群名称不能为空");
            return;
        }
        long partyNo = 0;
        if(ChattingTemper.getInstance().getMsgType() == ConstantUtil.CHAT_PAR && isBindViewValid()){
            partyNo = ChattingTemper.getInstance().getToNo();
        }
        final PartyNameUpdateReq partyNameUpdateReq = new PartyNameUpdateReq(partyNo, partyName);
        showLoading(ConstantUtil.LOADING_CENTER, "正在发送");
        ContactAction.getInstance(context).getPartyNameUpdate(partyNameUpdateReq, new ActionStringCallbackListener<ActionRespon<String>>() {
            @Override
            public void onSuccess(ActionRespon<String> result) {
                hideLoading(ConstantUtil.LOADING_CENTER);
                if(showMessage(result.getRetCode(), result.getMessage())){
                    if(isBindViewValid()){
                        getBindView().finishView();
                    }
                    showWarning("修改群名称成功");
                    EventBus.getDefault().post(new UpdatePartyEvent(partyNameUpdateReq.getPartyNo(), result.getData(), ConstantUtil.CHAT_PARTY_NAME));
                }
            }

            @Override
            public void onFailure(int errorCode) {
                hideLoading(ConstantUtil.LOADING_CENTER);
                showError(errorCode);
            }
        });
    }

    @Override
    public void publishNewPartyIntroduce(String introduce) {
        long partyNo = 0;
        if(ChattingTemper.getInstance().getMsgType() == ConstantUtil.CHAT_PAR && isBindViewValid()){
            partyNo = ChattingTemper.getInstance().getToNo();
        }
        final PartyIntroduceUpdateReq partyIntroduceUpdateReq = new PartyIntroduceUpdateReq(partyNo, introduce);
        showLoading(ConstantUtil.LOADING_CENTER, "正在发送");
        ContactAction.getInstance(context).getPartyIntroduceUpdate(partyIntroduceUpdateReq, new ActionStringCallbackListener<ActionRespon<String>>() {
            @Override
            public void onSuccess(ActionRespon<String> result) {
                hideLoading(ConstantUtil.LOADING_CENTER);
                if(showMessage(result.getRetCode(), result.getMessage())){
                    if(isBindViewValid()){
                        getBindView().finishView();
                    }
                    showWarning("修改群介绍成功");
                    EventBus.getDefault().post(new UpdatePartyEvent(partyIntroduceUpdateReq.getPartyNo(), result.getData(), ConstantUtil.CHAT_PARTY_INTRODUCE));
                }
            }

            @Override
            public void onFailure(int errorCode) {
                hideLoading(ConstantUtil.LOADING_CENTER);
                showError(errorCode);
            }
        });
    }

    @Override
    public void publishNewMemberName(final String memberName) {
        if(StringUtil.isEmpty(memberName)){
            showWarning("群名片不能为空");
            return;
        }
        long partyNo = 0;
        if(ChattingTemper.getInstance().getMsgType() == ConstantUtil.CHAT_PAR && isBindViewValid()){
            partyNo = ChattingTemper.getInstance().getToNo();
        }
        final MemberNameUpdateReq memberNameUpdateReq = new MemberNameUpdateReq(memberName, partyNo);
        showLoading(ConstantUtil.LOADING_CENTER, "正在发送");
        ContactAction.getInstance(context).getMemberNameUpdate(memberNameUpdateReq, new ActionStringCallbackListener<ActionRespon<String>>() {
            @Override
            public void onSuccess(ActionRespon<String> result) {
                hideLoading(ConstantUtil.LOADING_CENTER);
                if(showMessage(result.getRetCode(), result.getMessage())){
                    if(isBindViewValid()){
                        getBindView().finishView();
                    }
                    showWarning("修改群名片成功");
                    EventBus.getDefault().post(new UpdatePartyEvent(memberNameUpdateReq.getPartyNo(),result.getData(), ConstantUtil.CHAT_PARTY_MEMBER_NAME));
                }
            }

            @Override
            public void onFailure(int errorCode) {
                hideLoading(ConstantUtil.LOADING_CENTER);
                showError(errorCode);
            }
        });
    }

    @Override
    public void publishNewFeedBack(String feedBack) {
        if(StringUtil.isEmpty(feedBack)){
            showWarning("意见反馈不能为空");
            return;
        }
        FeedBackReq feedBackReq = new FeedBackReq();
        feedBackReq.setContent(feedBack);
        showLoading(ConstantUtil.LOADING_CENTER, "正在发送");
        FeedBackAction.getInstance(context).getFeedBackAdd(feedBackReq, new ActionStringCallbackListener<ActionRespon<Void>>() {
            @Override
            public void onSuccess(ActionRespon<Void> result) {
                hideLoading(ConstantUtil.LOADING_CENTER);
                if(showMessage(result.getRetCode(), result.getMessage())){
                    if(isBindViewValid()){
                        getBindView().finishView();
                    }
                    showWarning("意见反馈成功");
                }
            }

            @Override
            public void onFailure(int errorCode) {
                hideLoading(ConstantUtil.LOADING_CENTER);
                showError(errorCode);
            }
        });
    }

    @Override
    public void publishNewAutograph(String autograph) {
        AutographModifyReq autographModifyReq = new AutographModifyReq();
        autographModifyReq.setAutograph(autograph);
        showLoading(ConstantUtil.LOADING_CENTER, "正在发送");
        UserInfoAction.getInstance(context).getAutographModify(autographModifyReq, new ActionStringCallbackListener<ActionRespon<String>>() {
            @Override
            public void onSuccess(ActionRespon<String> result) {
                if(showMessage(result.getRetCode(), result.getMessage())){
                    if(isBindViewValid()){
                        getBindView().finishView();
                    }
                    showWarning("修改签名成功");
                    EventBus.getDefault().post(new UpdateAutographEvent(result.getData()));
                }
            }

            @Override
            public void onFailure(int errorCode) {
                showError(errorCode);
            }
        });
    }

}
