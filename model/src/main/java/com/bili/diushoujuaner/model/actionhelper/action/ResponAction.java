package com.bili.diushoujuaner.model.actionhelper.action;

import android.content.Context;

import com.bili.diushoujuaner.model.actionhelper.IResponAction;
import com.bili.diushoujuaner.model.actionhelper.respon.ActionResponse;
import com.bili.diushoujuaner.model.apihelper.ApiResponse;
import com.bili.diushoujuaner.model.apihelper.api.ApiAction;
import com.bili.diushoujuaner.model.apihelper.callback.ApiStringCallbackListener;
import com.bili.diushoujuaner.model.apihelper.request.ResponAddReq;
import com.bili.diushoujuaner.model.apihelper.request.ResponRemoveReq;
import com.bili.diushoujuaner.utils.entity.dto.ResponDto;
import com.bili.diushoujuaner.model.callback.ActionStringCallbackListener;
import com.bili.diushoujuaner.utils.GsonUtil;
import com.google.gson.reflect.TypeToken;
import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;

/**
 * Created by BiLi on 2016/4/2.
 */
public class ResponAction implements IResponAction {

    private static ResponAction responAction;
    private Context context;

    public ResponAction(Context context) {
        this.context = context;
    }

    public static synchronized ResponAction getInstance(Context context){
        if(responAction == null){
            responAction = new ResponAction(context);
        }
        return responAction;
    }

    @Override
    public void getResponAdd(ResponAddReq responAddReq, final ActionStringCallbackListener<ActionResponse<ResponDto>> actionStringCallbackListener) {
        ApiAction.getInstance().getResponAdd(responAddReq, new ApiStringCallbackListener() {
            @Override
            public void onSuccess(final String data) {
                Tasks.executeInBackground(context, new BackgroundWork<ActionResponse<ResponDto>>() {
                    @Override
                    public ActionResponse<ResponDto> doInBackground() throws Exception {
                        ApiResponse<ResponDto> result = GsonUtil.getInstance().fromJson(data, new TypeToken<ApiResponse<ResponDto>>() {
                        }.getType());
                        return ActionResponse.getActionResponFromApiRespon(result);
                    }
                }, new Completion<ActionResponse<ResponDto>>() {
                    @Override
                    public void onSuccess(Context context, ActionResponse<ResponDto> result) {
                        actionStringCallbackListener.onSuccess(result);
                    }

                    @Override
                    public void onError(Context context, Exception e) {
                        actionStringCallbackListener.onSuccess(ActionResponse.<ResponDto>getActionResponError());
                    }
                });
            }

            @Override
            public void onFailure(int errorCode) {
                actionStringCallbackListener.onFailure(errorCode);
            }
        });
    }

    @Override
    public void getResponRemove(ResponRemoveReq responRemoveReq, final ActionStringCallbackListener<ActionResponse<Long>> actionStringCallbackListener) {
        ApiAction.getInstance().getResponRemove(responRemoveReq, new ApiStringCallbackListener() {
            @Override
            public void onSuccess(final String data) {
                Tasks.executeInBackground(context, new BackgroundWork<ActionResponse<Long>>() {
                    @Override
                    public ActionResponse<Long> doInBackground() throws Exception {
                        ApiResponse<Long> result = GsonUtil.getInstance().fromJson(data, new TypeToken<ApiResponse<Long>>() {
                        }.getType());
                        return ActionResponse.getActionResponFromApiRespon(result);
                    }
                }, new Completion<ActionResponse<Long>>() {
                    @Override
                    public void onSuccess(Context context, ActionResponse<Long> result) {
                        actionStringCallbackListener.onSuccess(result);
                    }

                    @Override
                    public void onError(Context context, Exception e) {
                        actionStringCallbackListener.onSuccess(ActionResponse.<Long>getActionResponError());
                    }
                });
            }

            @Override
            public void onFailure(int errorCode) {
                actionStringCallbackListener.onFailure(errorCode);
            }
        });
    }
}
