package com.bili.diushoujuaner.presenter.presenter.impl;

import android.content.Context;

import com.bili.diushoujuaner.model.action.impl.GoodAction;
import com.bili.diushoujuaner.model.action.impl.UserInfoAction;
import com.bili.diushoujuaner.model.action.respon.ActionRespon;
import com.bili.diushoujuaner.model.preferhelper.CustomSessionPreference;
import com.bili.diushoujuaner.model.tempHelper.GoodTemper;
import com.bili.diushoujuaner.model.tempHelper.RecallTemper;
import com.bili.diushoujuaner.presenter.presenter.HomeFragmentPresenter;
import com.bili.diushoujuaner.utils.Constant;
import com.bili.diushoujuaner.model.apihelper.request.RecallListReq;
import com.bili.diushoujuaner.model.apihelper.response.GoodDto;
import com.bili.diushoujuaner.model.apihelper.response.RecallDto;
import com.bili.diushoujuaner.model.action.impl.RecallAction;
import com.bili.diushoujuaner.model.callback.ActionStringCallbackListener;
import com.bili.diushoujuaner.presenter.base.BasePresenter;
import com.bili.diushoujuaner.presenter.view.IHomeView;

import java.util.List;

/**
 * Created by BiLi on 2016/3/10.
 */
public class HomeFragmentPresenterImpl extends BasePresenter<IHomeView> implements HomeFragmentPresenter {

    private RecallListReq recallListReq = new RecallListReq();

    public HomeFragmentPresenterImpl(IHomeView baseView, Context context) {
        super(baseView, context);
    }

    @Override
    public void changeGoodStatusToLocal(long recallNo, int position) {
        if(getGoodStatusByRecallNo(recallNo)){
            GoodTemper.setGoodStatus(recallNo, false);
            RecallTemper.removeGood(position);
        } else {
            GoodTemper.setGoodStatus(recallNo, true);

            GoodDto goodDto = new GoodDto();
            goodDto.setUserNo(CustomSessionPreference.getInstance().getCustomSession().getUserNo());
            goodDto.setNickName(UserInfoAction.getInstance(context).getUserFromLocal().getNickName());
            RecallTemper.addGood(goodDto, position);
        }
    }

    @Override
    public boolean getGoodStatusByRecallNo(long recallNo) {
        return GoodTemper.getGoodStatus(recallNo);
    }

    @Override
    public void getGoodAdd(long recallNo){
        GoodAction.getInstance(context).getGoodAdd(recallNo, new ActionStringCallbackListener<ActionRespon<String>>() {
            @Override
            public void onSuccess(ActionRespon<String> result) {
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
        GoodAction.getInstance(context).getGoodRemove(recallNo, new ActionStringCallbackListener<ActionRespon<String>>() {
            @Override
            public void onSuccess(ActionRespon<String> result) {
                showMessage(result.getRetCode(), result.getMessage());
            }

            @Override
            public void onFailure(int errorCode) {
                showError(errorCode);
            }
        });
    }

    @Override
    public void getRecallList(final int refreshType){
        showLoadingByRefreshType(refreshType);
        initRecallListReq();

        RecallAction.getInstance(context).getRecallList(recallListReq, new ActionStringCallbackListener<ActionRespon<List<RecallDto>>>() {
            @Override
            public void onSuccess(ActionRespon<List<RecallDto>> result) {
                if (showMessage(result.getRetCode(), result.getMessage()) && isBindViewValid()) {
                    if(result.getData() != null){
                        getBindView().showRecallList(result.getData());
                    }

                    updateRequestParam(result.getData());
                    RecallTemper.clear();
                    GoodTemper.clear();
                    RecallTemper.addRecallDtoList(result.getData());
                }
                hideLoading();
            }

            @Override
            public void onFailure(int errorCode) {
                showError(errorCode);
                hideLoading();
            }
        });

    }

    @Override
    public void showRecallFromCache(){
        RecallAction.getInstance(context).getRecallListFromACache(new ActionStringCallbackListener<ActionRespon<List<RecallDto>>>() {
            @Override
            public void onSuccess(ActionRespon<List<RecallDto>> result) {
                if (showMessage(result.getRetCode(), result.getMessage()) && isBindViewValid()) {
                    RecallTemper.clear();
                    RecallTemper.addRecallDtoList(result.getData());
                    getBindView().showRecallList(result.getData());
                }
            }

            @Override
            public void onFailure(int errorCode) {
                showError(errorCode);
            }
        });
    }

    @Override
    public void getMoreRecallList(){
        RecallAction.getInstance(context).getRecallList(recallListReq, new ActionStringCallbackListener<ActionRespon<List<RecallDto>>>() {
            @Override
            public void onSuccess(ActionRespon<List<RecallDto>> result) {
                if (showMessage(result.getRetCode(), result.getMessage()) && isBindViewValid()) {
                    updateRequestParam(result.getData());
                    getBindView().showMoreRecallList(result.getData());
                    RecallTemper.addRecallDtoList(result.getData());
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
    public boolean executeGoodChange(boolean goodstatus, long recallNo){
        if(goodstatus == GoodTemper.getGoodStatus(recallNo)){
            return goodstatus;
        }
        if(GoodTemper.getGoodStatus(recallNo)){
            getGoodAdd(recallNo);
            return true;
        }else{
            getGoodRemove(recallNo);
            return false;
        }
    }

    private void showLoadingByRefreshType(int refreshType){
        if(refreshType == Constant.REFRESH_DEFAULT){
            showLoading(Constant.LOADING_DEFAULT, "");
        }
    }

    private void hideLoading(){
        hideLoading(Constant.LOADING_DEFAULT);
        if(isBindViewValid()){
            getBindView().setRefreshingEnd();
        }
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

    private void initRecallListReq(){
        recallListReq.setType(Constant.RECALL_ALL);
        recallListReq.setPageIndex(1);
        recallListReq.setPageSize(20);
        //此处为查询所有用户的，为了和用户个人空间区分，这里设置userNo为-1  无效
        recallListReq.setUserNo(-1);
        recallListReq.setLastRecall(-1);
    }

}
