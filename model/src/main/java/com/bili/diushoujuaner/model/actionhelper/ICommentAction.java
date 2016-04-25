package com.bili.diushoujuaner.model.actionhelper;

import com.bili.diushoujuaner.model.actionhelper.respon.ActionRespon;
import com.bili.diushoujuaner.model.apihelper.request.CommentAddReq;
import com.bili.diushoujuaner.model.apihelper.request.CommentRemoveReq;
import com.bili.diushoujuaner.utils.entity.dto.CommentDto;
import com.bili.diushoujuaner.model.callback.ActionStringCallbackListener;

/**
 * Created by BiLi on 2016/4/2.
 */
public interface ICommentAction {

    void getCommentAdd(CommentAddReq commentAddReq, ActionStringCallbackListener<ActionRespon<CommentDto>> actionStringCallbackListener);

    void getCommentRemove(CommentRemoveReq commentRemoveReq, ActionStringCallbackListener<ActionRespon<Long>> actionStringCallbackListener);
}
