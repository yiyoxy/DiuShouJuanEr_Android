package com.bili.diushoujuaner.utils;

import android.os.Environment;

/**
 * Created by BiLi on 2016/3/11.
 */
public interface Constant {

    // 返回结果的retCode
    String RETCODE_SUCCESS = "success";
    String RETCODE_FAIL = "fail";
    String RETCODE_ERROR = "error";

    int SHOW_TYPE_LOGIN = 0;
    int SHOW_TYPE_MAIN = 1;

    int ERROR_PARSE = -1;

    // 加载框类型
    int LOADING_NONE = -1;
    int LOADING_DEFAULT = 0; // 标题栏的加载框
    int LOADING_TOP = 1; // 顶部横向长条加载，重型加载
    int LOADING_CENTER = 2; // 屏幕中间加载，中型加载

    // 通用警告类型
    int WARNING_PARSE = 0;
    int WARNING_NET = 1;
    int WARNING_500 = 2;
    int WARNING_401 = 3;
    int WARNING_403 = 4;
    int WARNING_503 = 5;
    int WARNING_FILE = 6;

    String DATABASE_NAME = "diushoujuaner_db";

    String HOST_ADDRESS = "http://192.168.137.1:8080/diushoujuaner/";
    String SERVER_IP = "192.168.137.1";

    // 通讯录类型
    int CONTACT_PARTY = 1;
    int CONTACT_FRIEND = 2;

    // 童趣类型
    int RECALL_ALL = 1;
    int RECALL_USER = 2;

    int REFRESH_DEFAULT = 1;
    int REFRESH_INTENT = 2;

    String ACTION_LOAD_LOCAL_SUCCESS = "本地数据加载成功";
    String ACTION_LOAD_LOCAL_FAILURE = "本地数据加载失败";

    int COMMENT_CLICK_LAYOUT_RESPON = 1;
    int COMMENT_CLICK_COMMENT_CONTENT = 2;
    int COMMENT_CLICK_SUB_RESPON = 3;

    int DELETE_COMMENT = 1;
    int DELETE_RESPON = 2;

    int EDIT_CONTENT_NONE = 0;
    int EDIT_CONTENT_AUTOGRAPH = 1;
    int EDIT_CONTENT_FEEDBACK = 2;

    int EDIT_CONTENT_LENGTH_AUTOGRAPH = 50;
    int EDIT_CONTENT_LENGTH_FEEDBACK = 200;


    int ACACHE_TIME_RECENT_RECALL = 600000;
    String ACACHE_RECENT_RECALL_PREFIX = "A_RECENT_RECALL_";
    String ACACHE_RECALL_LIST = "A_RECALL_LIST";
    String ACACHE_LAST_TIME_CONTACT = "A_CONTACT_TIME";
    String ACACHE_USER_RECALL_PREFIX = "A_USER_RECALL_";

    //区分是忘记密码还是注册
    int ACOUNT_UPDATE_REGIST = 1;
    int ACOUNT_UPDATE_RESET = 2;
    //传递给RecallAdapter的页面类型
    int RECALL_ADAPTER_HOME = 1;
    int RECALL_ADAPTER_SPACE = 2;
    //传递给RecallAdapter的页面索引，仅表明home的就可以，因为SpaceActivity有静态变量公布
    int RECALL_GOOD_HOME_INDEX = 0;

    int CORP_IMAGE_HEAD_EAGE = 280;
    int CORP_IMAGE_WALLPAPER_EAGE = 320;

    int CORP_IMAGE_OUT_WIDTH = 800;
    int CORP_IMAGE_OUT_HEIGHT = 800;

    int RECALL_ADD_PIC_PATH = 1;
    int RECALL_ADD_PIC_RES = 2;

    // 聊天的消息类型
    int CHAT_INIT = -1;
    int CHAT_PING = 0;
    int CHAT_PONG = 1;
    int CHAT_FRI = 2;
    int CHAT_TIME = 3;
    int CHAT_CLOSE = 4;
    int CHAT_PAR = 5;
    int CHAT_GOOD = 6;
    // 聊天消息中content的类型
    int CHAT_CONTENT_EMPTY = 0;
    int CHAT_CONTENT_TEXT = 1;
    int CHAT_CONTENT_IMG = 2;
    int CHAT_CONTENT_VOICE = 3;

    int IDEL_TIMEOUT_FOR_INTERVAL = 20;//如果20S内没有收到来自服务端的心跳请求，则触发离线
    int CONNECTTIMEOUT = 5000;//5S连接超时

    int MESSAGE_STATUS_SENDING = 0;
    int MESSAGE_STATUS_FAIL = 1;
    int MESSAGE_STATUS_SUCCESS = 2;

}
