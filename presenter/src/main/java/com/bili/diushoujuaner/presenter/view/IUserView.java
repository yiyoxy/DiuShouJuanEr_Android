package com.bili.diushoujuaner.presenter.view;

import com.bili.diushoujuaner.utils.entity.po.User;
import com.bili.diushoujuaner.presenter.base.IBaseView;

/**
 * Created by BiLi on 2016/3/13.
 */
public interface IUserView extends IBaseView {

    void showUserInfo(User user);

    void finishView();

    void updateHeadPic(String headPath);

}
