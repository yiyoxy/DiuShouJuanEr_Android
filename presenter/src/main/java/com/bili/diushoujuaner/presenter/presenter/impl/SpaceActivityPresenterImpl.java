package com.bili.diushoujuaner.presenter.presenter.impl;

import android.content.Context;

import com.bili.diushoujuaner.model.actionhelper.action.ContactAction;
import com.bili.diushoujuaner.model.actionhelper.action.FileAction;
import com.bili.diushoujuaner.model.actionhelper.action.GoodAction;
import com.bili.diushoujuaner.model.actionhelper.action.RecallAction;
import com.bili.diushoujuaner.model.actionhelper.action.UserInfoAction;
import com.bili.diushoujuaner.model.actionhelper.respon.ActionResponse;
import com.bili.diushoujuaner.model.apihelper.request.ContactAddInfoReq;
import com.bili.diushoujuaner.model.apihelper.request.RecallListReq;
import com.bili.diushoujuaner.model.apihelper.request.RecallRemoveReq;
import com.bili.diushoujuaner.model.tempHelper.ContactTemper;
import com.bili.diushoujuaner.utils.ConstantUtil;
import com.bili.diushoujuaner.utils.StringUtil;
import com.bili.diushoujuaner.utils.TimeUtil;
import com.bili.diushoujuaner.utils.entity.dto.GoodDto;
import com.bili.diushoujuaner.utils.entity.dto.RecallDto;
import com.bili.diushoujuaner.model.callback.ActionFileCallbackListener;
import com.bili.diushoujuaner.model.callback.ActionStringCallbackListener;
import com.bili.diushoujuaner.model.preferhelper.CustomSessionPreference;
import com.bili.diushoujuaner.model.tempHelper.GoodTemper;
import com.bili.diushoujuaner.model.tempHelper.RecallTemper;
import com.bili.diushoujuaner.presenter.base.BasePresenter;
import com.bili.diushoujuaner.presenter.presenter.SpaceActivityPresenter;
import com.bili.diushoujuaner.presenter.view.ISpaceView;
import com.bili.diushoujuaner.utils.entity.vo.FriendVo;
import com.bili.diushoujuaner.model.eventhelper.UpdateWallPaperEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by BiLi on 2016/4/9.
 */
public class SpaceActivityPresenterImpl extends BasePresenter<ISpaceView> implements SpaceActivityPresenter {

    private RecallListReq recallListReq;

    public SpaceActivityPresenterImpl(ISpaceView baseView, Context context) {
        super(baseView, context);
        recallListReq = new RecallListReq();
    }

    @Override
    public boolean isFriend(long userNo) {
        return ContactTemper.getInstance().getFriendVo(userNo) != null;
    }

    @Override
    public void updateWallpaper(String path) {
        showLoading(ConstantUtil.LOADING_TOP,"正在上传壁纸...");
        FileAction.getInstance(context).uploadWallpaper(path, new ActionFileCallbackListener<ActionResponse<String>>() {
            @Override
            public void onSuccess(ActionResponse<String> result) {
                hideLoading(ConstantUtil.LOADING_TOP);
                if(showMessage(result.getRetCode(), result.getMessage()) && isBindViewValid()){
                    getBindView().updateWallPaper(result.getData());
                }
            }

            @Override
            public void onFailure(int errorCode) {
                hideLoading(ConstantUtil.LOADING_TOP);
                showError(errorCode);
            }

            @Override
            public void onProgress(float progress) {

            }
        });
    }

    @Override
    public void getRecallRemove(final long recallNo, final int position) {
        showLoading(ConstantUtil.LOADING_CENTER, "正在删除...");
        RecallAction.getInstance(context).getRecallRemove(new RecallRemoveReq(recallNo), new ActionStringCallbackListener<ActionResponse<Long>>() {
            @Override
            public void onSuccess(ActionResponse<Long> result) {
                hideLoading(ConstantUtil.LOADING_CENTER);
                if(showMessage(result.getRetCode(), result.getMessage())){
                    getBindView().removeRecallByPosition(position);
                    RecallTemper.getInstance().removeRecallDto(result.getData());
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
    public void addGoodToLocal(RecallDto recallDto) {
        GoodDto goodDto = new GoodDto();
        goodDto.setUserNo(CustomSessionPreference.getInstance().getCustomSession().getUserNo());
        goodDto.setNickName(UserInfoAction.getInstance(context).getUserFromLocal().getNickName());

        if(recallDto != null){
            recallDto.getGoodList().add(0, goodDto);
        }
        RecallTemper.getInstance().addGood(goodDto, recallDto.getRecallNo());
    }

    @Override
    public void removeGoodFromLocal(RecallDto recallDto) {
        if(recallDto != null){
            RecallTemper.getInstance().removeGood(recallDto.getRecallNo());
            List<GoodDto> goodDtoList = recallDto.getGoodList();
            if(goodDtoList.isEmpty()){
                return;
            }
            for(GoodDto goodDto : goodDtoList){
                if(goodDto.getUserNo() == CustomSessionPreference.getInstance().getCustomSession().getUserNo()){
                    goodDtoList.remove(goodDto);
                    break;
                }
            }
        }
    }

    @Override
    public boolean executeGoodChange(boolean goodStatus, long recallNo) {
        if(goodStatus == GoodTemper.getInstance().getGoodStatus(recallNo)){
            return goodStatus;
        }
        if(GoodTemper.getInstance().getGoodStatus(recallNo)){
            getGoodAdd(recallNo);
            return true;
        }else{
            getGoodRemove(recallNo);
            return false;
        }
    }

    @Override
    public void getGoodAdd(long recallNo){
        GoodAction.getInstance(context).getGoodAdd(recallNo, new ActionStringCallbackListener<ActionResponse<String>>() {
            @Override
            public void onSuccess(ActionResponse<String> result) {
                showMessage(result.getRetCode(), result.getMessage());
            }

            @Override
            public void onFailure(int errorCode) {
                showError(errorCode);
            }
        });
    }

    @Override
    public void getGoodRemove(long recallNo){
        GoodAction.getInstance(context).getGoodRemove(recallNo, new ActionStringCallbackListener<ActionResponse<String>>() {
            @Override
            public void onSuccess(ActionResponse<String> result) {
                showMessage(result.getRetCode(), result.getMessage());
            }

            @Override
            public void onFailure(int errorCode) {
                showError(errorCode);
            }
        });
    }

    @Override
    public boolean getGoodStatusByRecallNo(long recallNo) {
        return GoodTemper.getInstance().getGoodStatus(recallNo);
    }

    @Override
    public void setGoodStatusByRecallNo(long recallNo, boolean status) {
        GoodTemper.getInstance().setGoodStatus(recallNo, status);
    }

    @Override
    public long getCustomSessionUserNo() {
        return CustomSessionPreference.getInstance().getCustomSession().getUserNo();
    }

    @Override
    public void addRecallDtoToTemper(RecallDto recallDto) {
        RecallTemper.getInstance().addRecallDto(recallDto);
    }

    @Override
    public void getContactInfo(final long userNo) {
        ContactAction.getInstance(context).getContactFromLocal(userNo, new ActionStringCallbackListener<ActionResponse<FriendVo>>() {
            @Override
            public void onSuccess(ActionResponse<FriendVo> result) {
                if(!isBindViewValid()){
                    return;
                }
                if (showMessage(result.getRetCode(), result.getMessage())) {
                    //TODO 更新获取联系人全量信息的时间间隔
                    if (result.getData() == null || StringUtil.isEmpty(result.getData().getUpdateTime()) || TimeUtil.getHourDifferenceBetweenTime(result.getData().getUpdateTime(), TimeUtil.getCurrentTimeYYMMDD_HHMMSS()) >= 1) {
                        //数据已经无效，则在不为空的情况下，先进行显示，在进行更新获取
                        if (result.getData() != null && isBindViewValid()) {
                            getBindView().showContactInfo(result.getData());
                        }
                        getFriendVoFromApi(userNo);
                    }else{
                        //数据仍在有效期内，直接显示，无需更新
                        hideLoading(ConstantUtil.LOADING_DEFAULT);
                        getBindView().showContactInfo(result.getData());
                    }
                }
            }

            @Override
            public void onFailure(int errorCode) {
            }
        });
    }

    private void getFriendVoFromApi(long userNo){
        ContactAction.getInstance(context).getContactFromApi(new ContactAddInfoReq(userNo), new ActionStringCallbackListener<ActionResponse<FriendVo>>() {
            @Override
            public void onSuccess(ActionResponse<FriendVo> result) {
                hideLoading(ConstantUtil.LOADING_DEFAULT);
                if(showMessage(result.getRetCode(), result.getMessage())){
                    if(isBindViewValid()){
                        getBindView().showContactInfo(result.getData());
                    }
                }
            }

            @Override
            public void onFailure(int errorCode) {
                hideLoading(ConstantUtil.LOADING_DEFAULT);
                showError(errorCode);
            }
        });
    }

    @Override
    public void getMoreRecallList(long userNo) {
        RecallAction.getInstance(context).getRecallList(recallListReq, new ActionStringCallbackListener<ActionResponse<List<RecallDto>>>() {
            @Override
            public void onSuccess(ActionResponse<List<RecallDto>> result) {
                if (showMessage(result.getRetCode(), result.getMessage()) && isBindViewValid()) {
                    updateRequestParam(result.getData());
                    getBindView().showMoreRecallList(result.getData());
                    RecallTemper.getInstance().addRecallDtoList(result.getData());
                }else{
                    if(isBindViewValid()){
                        getBindView().setLoadMoreEnd();
                    }
                }
            }

            @Override
            public void onFailure(int errorCode) {
                showError(errorCode);
                if(isBindViewValid()){
                    getBindView().setLoadMoreEnd();
                }
            }
        });
    }

    @Override
    public void getRecallList(long userNo) {
        initRecallListReq(userNo);
        showLoading(ConstantUtil.LOADING_DEFAULT,"");

        RecallAction.getInstance(context).getRecallList(recallListReq, new ActionStringCallbackListener<ActionResponse<List<RecallDto>>>() {
            @Override
            public void onSuccess(ActionResponse<List<RecallDto>> result) {
                if (showMessage(result.getRetCode(), result.getMessage()) && isBindViewValid()) {
                    if(result.getData() != null){
                        getBindView().showRecallList(result.getData());
                    }
                    updateRequestParam(result.getData());
                }
                hideLoading(ConstantUtil.LOADING_DEFAULT);
            }

            @Override
            public void onFailure(int errorCode) {
                showError(errorCode);
                hideLoading(ConstantUtil.LOADING_DEFAULT);
            }
        });
    }

    @Override
    public void showRecallFromCache(long userNo) {
        RecallAction.getInstance(context).getUserRecallListFromACache(userNo, new ActionStringCallbackListener<ActionResponse<List<RecallDto>>>() {
            @Override
            public void onSuccess(ActionResponse<List<RecallDto>> result) {
                if (showMessage(result.getRetCode(), result.getMessage()) && isBindViewValid()) {
                    getBindView().showRecallList(result.getData());
                }
            }

            @Override
            public void onFailure(int errorCode) {
                showError(errorCode);
            }
        });
    }

    private void updateRequestParam(List<RecallDto> recallDtoList){
        if(recallDtoList == null){
            return;
        }
        if(recallDtoList.size() >= recallListReq.getPageSize()){
            recallListReq.setPageIndex(recallListReq.getPageIndex() + 1);
            recallListReq.setLastRecall(recallDtoList.get(0).getRecallNo());
        }
    }

    private void initRecallListReq(long userNo){
        recallListReq.setType(ConstantUtil.RECALL_USER);
        recallListReq.setPageIndex(1);
        recallListReq.setPageSize(20);
        recallListReq.setUserNo(userNo);
        recallListReq.setLastRecall(-1);
    }
}
