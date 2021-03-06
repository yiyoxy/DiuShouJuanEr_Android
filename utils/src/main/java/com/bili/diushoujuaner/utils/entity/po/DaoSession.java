package com.bili.diushoujuaner.utils.entity.po;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.bili.diushoujuaner.utils.entity.po.User;
import com.bili.diushoujuaner.utils.entity.po.Friend;
import com.bili.diushoujuaner.utils.entity.po.Party;
import com.bili.diushoujuaner.utils.entity.po.Member;
import com.bili.diushoujuaner.utils.entity.po.Chat;
import com.bili.diushoujuaner.utils.entity.po.Apply;

import com.bili.diushoujuaner.utils.entity.po.UserDao;
import com.bili.diushoujuaner.utils.entity.po.FriendDao;
import com.bili.diushoujuaner.utils.entity.po.PartyDao;
import com.bili.diushoujuaner.utils.entity.po.MemberDao;
import com.bili.diushoujuaner.utils.entity.po.ChatDao;
import com.bili.diushoujuaner.utils.entity.po.ApplyDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig friendDaoConfig;
    private final DaoConfig partyDaoConfig;
    private final DaoConfig memberDaoConfig;
    private final DaoConfig chatDaoConfig;
    private final DaoConfig applyDaoConfig;

    private final UserDao userDao;
    private final FriendDao friendDao;
    private final PartyDao partyDao;
    private final MemberDao memberDao;
    private final ChatDao chatDao;
    private final ApplyDao applyDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        friendDaoConfig = daoConfigMap.get(FriendDao.class).clone();
        friendDaoConfig.initIdentityScope(type);

        partyDaoConfig = daoConfigMap.get(PartyDao.class).clone();
        partyDaoConfig.initIdentityScope(type);

        memberDaoConfig = daoConfigMap.get(MemberDao.class).clone();
        memberDaoConfig.initIdentityScope(type);

        chatDaoConfig = daoConfigMap.get(ChatDao.class).clone();
        chatDaoConfig.initIdentityScope(type);

        applyDaoConfig = daoConfigMap.get(ApplyDao.class).clone();
        applyDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        friendDao = new FriendDao(friendDaoConfig, this);
        partyDao = new PartyDao(partyDaoConfig, this);
        memberDao = new MemberDao(memberDaoConfig, this);
        chatDao = new ChatDao(chatDaoConfig, this);
        applyDao = new ApplyDao(applyDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(Friend.class, friendDao);
        registerDao(Party.class, partyDao);
        registerDao(Member.class, memberDao);
        registerDao(Chat.class, chatDao);
        registerDao(Apply.class, applyDao);
    }
    
    public void clear() {
        userDaoConfig.getIdentityScope().clear();
        friendDaoConfig.getIdentityScope().clear();
        partyDaoConfig.getIdentityScope().clear();
        memberDaoConfig.getIdentityScope().clear();
        chatDaoConfig.getIdentityScope().clear();
        applyDaoConfig.getIdentityScope().clear();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public FriendDao getFriendDao() {
        return friendDao;
    }

    public PartyDao getPartyDao() {
        return partyDao;
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    public ChatDao getChatDao() {
        return chatDao;
    }

    public ApplyDao getApplyDao() {
        return applyDao;
    }

}
