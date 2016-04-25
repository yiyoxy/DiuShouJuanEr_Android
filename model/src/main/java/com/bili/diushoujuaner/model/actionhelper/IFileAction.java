package com.bili.diushoujuaner.model.actionhelper;

import com.bili.diushoujuaner.model.actionhelper.respon.ActionRespon;
import com.bili.diushoujuaner.model.apihelper.request.PartyAddReq;
import com.bili.diushoujuaner.model.apihelper.request.PartyHeadUpdateReq;
import com.bili.diushoujuaner.model.apihelper.request.RecallSerialReq;
import com.bili.diushoujuaner.model.callback.ActionFileCallbackListener;

/**
 * Created by BiLi on 2016/4/11.
 */
public interface IFileAction {

    void uploadHeadPic(String path, ActionFileCallbackListener<ActionRespon<String>> actionFileCallbackListener);

    void uploadWallpaper(String path, ActionFileCallbackListener<ActionRespon<String>> actionFileCallbackListener);

    void uploadRecallPic(RecallSerialReq recallSerialReq, String path, ActionFileCallbackListener<ActionRespon<String>> actionFileCallbackListener);

    void upoadPartyHeadPic(PartyHeadUpdateReq partyHeadUpdateReq, String path, ActionFileCallbackListener<ActionRespon<String>> actionFileCallbackListener);

}
