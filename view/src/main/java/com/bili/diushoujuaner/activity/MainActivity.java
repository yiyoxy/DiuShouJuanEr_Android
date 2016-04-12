package com.bili.diushoujuaner.activity;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bili.diushoujuaner.R;
import com.bili.diushoujuaner.base.BaseFragmentActivity;
import com.bili.diushoujuaner.presenter.presenter.MainActivityPresenter;
import com.bili.diushoujuaner.presenter.event.UpdateAutographEvent;
import com.bili.diushoujuaner.presenter.event.ShowHeadEvent;
import com.bili.diushoujuaner.presenter.event.ShowMainMenuEvent;
import com.bili.diushoujuaner.fragment.ContactFragment;
import com.bili.diushoujuaner.fragment.HomeFragment;
import com.bili.diushoujuaner.fragment.MessageFragment;
import com.bili.diushoujuaner.model.databasehelper.dao.User;
import com.bili.diushoujuaner.presenter.presenter.impl.MainActivityPresenterImpl;
import com.bili.diushoujuaner.presenter.view.IMainView;
import com.bili.diushoujuaner.utils.Common;
import com.bili.diushoujuaner.utils.Constant;
import com.bili.diushoujuaner.presenter.event.UpdateUserInfoEvent;
import com.bili.diushoujuaner.presenter.event.UpdateWallPaperEvent;
import com.bili.diushoujuaner.utils.manager.ActivityManager;
import com.bili.diushoujuaner.widget.CustomViewPager;
import com.bili.diushoujuaner.widget.TintedBitmapDrawable;
import com.bili.diushoujuaner.widget.badgeview.BGABadgeTextView;
import com.bili.diushoujuaner.widget.badgeview.BGABottomNavigation;
import com.bili.diushoujuaner.widget.badgeview.BGABottomNavigationItem;
import com.bili.diushoujuaner.widget.badgeview.BGABottomNavigationItemView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class MainActivity extends BaseFragmentActivity<MainActivityPresenter> implements View.OnClickListener, IMainView, BGABottomNavigation.IViewInitFinishListener {

    @Bind(R.id.customViewPager)
    CustomViewPager customViewPager;
    @Bind(R.id.menuHead)
    SimpleDraweeView menuHead;
    @Bind(R.id.menuBg)
    SimpleDraweeView menuBg;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.btnMenuExit)
    Button btnMenuExit;
    @Bind(R.id.btnMenuSetting)
    Button btnMenuSetting;
    @Bind(R.id.txtSystemNotice)
    BGABadgeTextView txtSystemNotice;
    @Bind(R.id.txtAutograph)
    TextView txtAutograph;
    @Bind(R.id.txtUserName)
    TextView txtUserName;
    @Bind(R.id.bottomNavigation)
    BGABottomNavigation bottomNavigation;
    @Bind(R.id.layoutAutograph)
    LinearLayout layoutAutograph;
    @Bind(R.id.layoutFeedback)
    LinearLayout layoutFeedback;
    @Bind(R.id.layoutSpace)
    LinearLayout layoutSpace;
    @Bind(R.id.layoutFocus)
    LinearLayout layoutFocus;
    @Bind(R.id.layoutFile)
    LinearLayout layoutFile;
    @Bind(R.id.layoutNotice)
    LinearLayout layoutNotice;
    @Bind(R.id.ivUser)
    ImageView ivUser;
    @Bind(R.id.ivFocus)
    ImageView ivFocus;
    @Bind(R.id.ivFolder)
    ImageView ivFolder;
    @Bind(R.id.ivNotice)
    ImageView ivNotice;
    @Bind(R.id.ivFeedBack)
    ImageView ivFeedBack;

    private boolean isWaitingExit = false;

    private List<Fragment> fragmentList;
    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private ContactFragment contactFragment;

    private BGABottomNavigationItemView navHome;
    private BGABottomNavigationItemView navMess;
    private BGABottomNavigationItemView navCont;

    private int badgeHomeCount = 0;
    private int badgeMessCount = 0;
    private int badgeContCount = 0;

    private User user;

    @Override
    public void beforeInitView() {
        fragmentList = new ArrayList<>();
        basePresenter = new MainActivityPresenterImpl(this, context);
        ActivityManager.getInstance().finishBefore();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void setViewStatus() {
        EventBus.getDefault().register(this);
        setTintStatusColor(R.color.TRANSPARENT);
        initOnClickListner();
        initFragment();
        initBottomButton();
        initMenuIv();

        txtSystemNotice.showTextBadge("2");
        getBindPresenter().getUserInfo();
    }

    @Override
    public void initFinished() {
        navHome = bottomNavigation.getBGAButtonItemView(0);
        navMess = bottomNavigation.getBGAButtonItemView(1);
        navCont = bottomNavigation.getBGAButtonItemView(2);

        navHome.showTextBadge("1");
    }

    private void initMenuIv(){
        ivUser.setImageDrawable(new TintedBitmapDrawable(getResources(),R.mipmap.icon_menu_user,ContextCompat.getColor(context,R.color.COLOR_THEME_SUB)));
        ivFeedBack.setImageDrawable(new TintedBitmapDrawable(getResources(),R.mipmap.icon_menu_feedback,ContextCompat.getColor(context,R.color.COLOR_THEME_SUB)));
        ivFocus.setImageDrawable(new TintedBitmapDrawable(getResources(),R.mipmap.icon_menu_focus,ContextCompat.getColor(context,R.color.COLOR_THEME_SUB)));
        ivFolder.setImageDrawable(new TintedBitmapDrawable(getResources(),R.mipmap.icon_menu_folder,ContextCompat.getColor(context,R.color.COLOR_THEME_SUB)));
        ivNotice.setImageDrawable(new TintedBitmapDrawable(getResources(), R.mipmap.icon_menu_notice, ContextCompat.getColor(context, R.color.COLOR_THEME_SUB)));
    }

    private void initFragment() {
        homeFragment = HomeFragment.instantiation(0);
        messageFragment = MessageFragment.instantiation(1);
        contactFragment = ContactFragment.instantiation(2);

        fragmentList.add(homeFragment);
        fragmentList.add(messageFragment);
        fragmentList.add(contactFragment);

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        customViewPager.setAdapter(fragmentPagerAdapter);
        customViewPager.setOffscreenPageLimit(3);
    }

    private void initOnClickListner() {
        menuHead.setOnClickListener(this);
        btnMenuExit.setOnClickListener(this);
        btnMenuSetting.setOnClickListener(this);
        layoutAutograph.setOnClickListener(this);
        layoutFeedback.setOnClickListener(this);
        layoutNotice.setOnClickListener(this);
        layoutFile.setOnClickListener(this);
        layoutFocus.setOnClickListener(this);
        layoutSpace.setOnClickListener(this);
    }

    private void initBottomButton() {
        BGABottomNavigationItem itemHome = new BGABottomNavigationItem(getResources().getString(R.string.main_nav_home), R.mipmap.nav_home, ContextCompat.getColor(context, R.color.COLOR_THEME_MAIN));
        BGABottomNavigationItem itemMess = new BGABottomNavigationItem(getResources().getString(R.string.main_nav_mess), R.mipmap.nav_mess, ContextCompat.getColor(context, R.color.COLOR_THEME_MAIN));
        BGABottomNavigationItem itemCont = new BGABottomNavigationItem(getResources().getString(R.string.main_nav_cont), R.mipmap.nav_cont, ContextCompat.getColor(context, R.color.COLOR_THEME_MAIN));

        ArrayList<BGABottomNavigationItem> items = new ArrayList<>();
        items.add(itemHome);
        items.add(itemMess);
        items.add(itemCont);

        bottomNavigation.addItems(items);
        bottomNavigation.setAccentColor(ContextCompat.getColor(context, R.color.COLOR_THEME_MAIN));
        bottomNavigation.setInactiveColor(ContextCompat.getColor(context, R.color.TC_ADADBB));
        bottomNavigation.setiViewInitFinishListener(this);
        bottomNavigation.setAHBottomNavigationListener(new BGABottomNavigation.AHBottomNavigationListener() {
            @Override
            public void onTabSelected(int position) {
                customViewPager.setCurrentItem(position, false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMenuExit:
                ActivityManager.getInstance().exit();
                break;
            case R.id.menuHead:
                startActivity(new Intent(MainActivity.this, UserActivity.class));
                break;
            case R.id.layoutAutograph:
                startActivity(new Intent(MainActivity.this, ContentEditActivity.class).putExtra(ContentEditActivity.TAG, Constant.EDIT_CONTENT_AUTOGRAPH));
                break;
            case R.id.layoutFeedback:
                startActivity(new Intent(MainActivity.this, ContentEditActivity.class).putExtra(ContentEditActivity.TAG, Constant.EDIT_CONTENT_FEEDBACK));
                break;
            case R.id.layoutNotice:
                startActivity(new Intent(MainActivity.this, NoticeActivity.class));
                break;
            case R.id.layoutFile:
                startActivity(new Intent(MainActivity.this, FileActivity.class));
                break;
            case R.id.layoutFocus:
                startActivity(new Intent(MainActivity.this, FocusActivity.class));
                break;
            case R.id.layoutSpace:
                startActivity(new Intent(MainActivity.this, SpaceActivity.class).putExtra(SpaceActivity.TAG,this.user != null ? this.user.getUserNo() : -1));
                break;
            case R.id.btnMenuSetting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowMainMenuEvent(ShowMainMenuEvent showMainMenuEvent) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateAutographEvent(UpdateAutographEvent updateAutographEvent){
        txtAutograph.setText(updateAutographEvent.getAutograph());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateWallPaperEvent(UpdateWallPaperEvent updateWallPaperEvent){
        Common.displayDraweeView(updateWallPaperEvent.getPath(), menuBg);
        this.user.setWallPaper(updateWallPaperEvent.getPath());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateUserInfoEvent(UpdateUserInfoEvent updateUserInfoEvent){
        getBindPresenter().getUserInfo();
    }

    @Override
    public boolean onKeyDown(int key, KeyEvent event) {
        switch (key) {
            case KeyEvent.KEYCODE_BACK:
                if (isWaitingExit) {
                    isWaitingExit = false;
                    ActivityManager.getInstance().exit();
                } else {
                    Toast.makeText(this, "再按一次退出!", Toast.LENGTH_SHORT).show();
                    isWaitingExit = true;
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            isWaitingExit = false;
                        }
                    }, 3000);
                }
                break;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowHeadEvent(ShowHeadEvent showHeadEvent){
        if(menuHead != null){
            Common.displayDraweeView(showHeadEvent.getHeadPicUrl(), menuHead);
        }
        if(this.user != null){
            this.user.setPicPath(showHeadEvent.getHeadPicUrl());
        }
    }

    @Override
    public void showUserInfo(User user) {
        this.user = user;
        Common.displayDraweeView(user.getPicPath(), menuHead);
        Common.displayDraweeView(user.getWallPaper(), menuBg);
        txtAutograph.setText(user.getAutograph());
        txtUserName.setText(user.getNickName());
    }

    @Override
    public void onPageDestroy() {
        EventBus.getDefault().unregister(this);
    }
}
