package com.tssss.bysj.game.friend;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.componet.menu.Menu;
import com.tssss.bysj.componet.menu.OnMenuItemClickListener;
import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.game.role.info.UserInfoActivity;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.other.jmessage.JMessageHelper;
import com.tssss.bysj.other.jmessage.JMessageManager;
import com.tssss.bysj.other.jmessage.TextContentFactory;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.AnimationUtil;
import com.tssss.bysj.util.IntentUtil;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

@ViewInject(layoutId = R.layout.activity_friend)
public class FriendsActivity extends BaseActivity implements OnMenuItemClickListener,
        IFriendContract.IView, OnFriendItemClickListener {

    //    private GTextView all;
//    private GTextView group;
    private ImageView nullFriends;
    private RecyclerView friendRv;
    private GTextView loading;

    private Menu menu;
    private FriendPresenter presenter;
    private Handler handler;
    private FriendAdapter adapter;
    private List<GameRole> friendList;
    private boolean handlerRequest;
    private Menu itemMenu;
    private AlertDialog.Builder builder;

    @Override
    protected void findViews() {
//        all = findViewById(R.id.friends_category_all_tv);
//        group = findViewById(R.id.friends_category_group_tv);
        nullFriends = findViewById(R.id.friends_null_iv);
        friendRv = findViewById(R.id.friends_rv);
        loading = findViewById(R.id.friends_loading_tv);
    }

    @Override
    protected void setEventListeners() {
//        all.setOnClickListener(this);
//        group.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {
        presenter = new FriendPresenter(this);
        handler = new Handler();
        JMessageManager.registerEvent(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.loadFriendList();

        }
        try {
            menu.dismiss();
        } catch (Exception e) {
            Logger.log("Just Ignore");
        }
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.friends_title;
    }

    @Override
    protected int getTopBarRightViewStyle() {
        return R.drawable.ic_btn_more;
    }

    @Override
    protected void clickTopBarRight() {
        super.clickTopBarRight();
        List<String> items = new ArrayList<>();
        items.add("添加好友");
//        items.add("好友统计");
        menu = new Menu.Builder(this, this)
                .items(items)
                .build();
        menu.display();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            /*case R.id.friends_category_all_tv:
                initCategoryTabState();
                startColorAnimation(all);
                break;
            case R.id.friends_category_group_tv:
                initCategoryTabState();
                startColorAnimation(group);
                break;*/
            default:
        }
    }

    @Override
    public void onMenuItemClick(View v, int position) {
        switch (position) {
            case 0:
                openActivityDelay(AddFriendActivity.class, 120);
                break;
            /*case 1:
                menu.dismiss();
                int friendCount = 0;
                if (null != friendList && friendList.size() > 0) {
                    friendCount = friendList.size();
                }
                AlertDialog dialog = new AlertDialog(this);
                dialog.desc("好友统计");
                dialog.subDesc("你总共有好友：" + 0);
                dialog.noDesc("知道了");
                dialog.operationType(AlertDialog.OPERATION_TYPE_NO);
                dialog.operationListener(new AlertDialog.OnDialogOperationListener() {
                    @Override
                    public void ok() {

                    }

                    @Override
                    public void no() {
                    }
                });
                dialog.display();
                break;*/
            default:
        }
    }

    private void startColorAnimation(GTextView view) {
        initCategoryTabState();
        AnimationUtil.startBackgroundColorAnimator(view);
    }

    private void initCategoryTabState() {
        /*all.setTextColor(0xFF7E561B);
        group.setTextColor(0xFF7E561B);
        all.setBackgroundColor(0x00ffffff);
        group.setBackgroundColor(0x00ffffff);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageManager.unregisterEvent(this);
    }

    @Override
    public void showFriend(List<GameRole> gameRoleList) {
        if (null != gameRoleList && gameRoleList.size() > 0) {
            friendList = gameRoleList;
            loading.setVisibility(View.GONE);
            nullFriends.setVisibility(View.GONE);
            friendRv.setVisibility(View.VISIBLE);
            friendRv.setLayoutManager(new LinearLayoutManager(this));
            adapter = new FriendAdapter(this, friendList, this);
            friendRv.setAdapter(adapter);

        } else {
            loading.setVisibility(View.GONE);
            nullFriends.setVisibility(View.VISIBLE);
        }
    }

    public void onEventMainThread(MessageEvent event) {
        JMessageManager.handlerMessageEvent(event);
    }

    public void onEventMainThread(NotificationClickEvent event) {
        JMessageManager.handlerNotificationEvent(event, this, true);
    }

    public void onEvent(ContactNotifyEvent event) {
        handlerRequest = true;
        switch (event.getType()) {
            case invite_received:
                String desc = event.getFromUsername();
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .desc(desc + " 想加你好友" + "\n" + "10秒考虑时间")
                        .subDesc(event.getReason())
                        .operationListener(new AlertDialog.OnDialogOperationListener() {
                            @Override
                            public void ok() {
                                ContactManager.acceptInvitation(event.getFromUsername(), null, new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {
                                        if (i == 0) {
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ToastUtil.showToast(FriendsActivity.this, "又多了个朋友", ToastUtil.TOAST_DEFAULT);
//                                                    finish();
                                                    newFriend(event);
                                                }
                                            });

                                        } else {
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ToastUtil.showToast(FriendsActivity.this, "出现问题", ToastUtil.TOAST_ERROR);
                                                    finish();
                                                }
                                            });

                                        }
                                    }
                                });
                                handlerRequest = false;
                            }

                            @Override
                            public void no() {
                                handlerRequest = false;
                            }
                        });
                builder.display();
                break;
            case invite_accepted:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this)
                        .desc("你与 " + event.getFromUsername() + " 好友申请通过")
                        .operationType(AlertDialog.OPERATION_TYPE_OK)
                        .operationListener(new AlertDialog.OnDialogOperationListener() {
                            @Override
                            public void ok() {
                                handlerRequest = false;
                                newFriend(event);
                            }

                            @Override
                            public void no() {
                                handlerRequest = false;
                            }
                        });
                builder1.display();


                break;
            case invite_declined:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this)
                        .desc("你对TA的好友申请被残忍拒绝")
                        .subDesc(event.getFromUsername())
                        .operationType(AlertDialog.OPERATION_TYPE_OK)
                        .operationListener(new AlertDialog.OnDialogOperationListener() {
                            @Override
                            public void ok() {
                                handlerRequest = false;
                                finish();
                            }

                            @Override
                            public void no() {
                                handlerRequest = false;
                            }
                        });
                builder2.display();
                break;

        }
    }

    private void newFriend(ContactNotifyEvent event) {
        final UserInfo[] userInfoA = {null};
        JMessageClient.getUserInfo(event.getFromUsername(),
                new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, UserInfo userInfo) {
                        if (i == 0) {
                            userInfoA[0] = userInfo;
                        }
                    }
                });
        friendList.add(JMessageHelper.toGameRole(userInfoA[0]));
        adapter.notifyItemInserted(0);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v, int position) {
        if (null == friendList || friendList.size() <= 0) {
            return;
        }
        GameRole gameRole = friendList.get(position);
        itemMenu = new Menu(this, new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View v, int position) {
                switch (position) {
                    case 0:
                        String accountID = gameRole.getUser().getUserId();
                        if (!StringUtil.isBlank(accountID)) {
                            Intent intent = new Intent(FriendsActivity.this, UserInfoActivity.class);
                            intent.putExtra(Constant.ACCOUNT_ID, accountID);
                            startActivity(intent);
                        }
                        itemMenu.dismiss();
                        break;
                    case 1:
                        String accountID01 = gameRole.getUser().getUserId();
                        if (!StringUtil.isBlank(accountID01)) {
                            IntentUtil.startChatIntent(FriendsActivity.this, accountID01);
                        }
                        itemMenu.dismiss();
                        break;
                    case 2:
                        String friendAccountId = gameRole.getUser().getUserId();
                        if (!StringUtil.isBlank(friendAccountId)) {
                            AlertDialog.Builder gameBuilder = new AlertDialog.Builder(FriendsActivity.this)
                                    .operationType(AlertDialog.OPERATION_TYPE_OK)
                                    .desc("请求发送中")
                                    .subDesc("对方同意后，自动开始游戏");
                            gameBuilder.display();
                            GameRole myRole = UserDataCache.readRole();
                            String msg = myRole.getName() + " 向你发来游戏请求";
                            TextContent textContent = TextContentFactory.gameInvitation(msg);
                            JMessageManager.sendTextMessage(friendAccountId, textContent,
                                    new JMessageManager.OnSendCompleteCallBack() {
                                        @Override
                                        public void onSuccess() {
//                                        gameBuilder.dismiss();
                                        }

                                        @Override
                                        public void onFailure(String errorMsg) {
                                            gameBuilder.dismiss();
                                            ToastUtil.showToast(FriendsActivity.this,
                                                    errorMsg,
                                                    ToastUtil.TOAST_ERROR);
                                        }
                                    });
                        }
                        itemMenu.dismiss();
                        break;
                }
            }
        });
        List<String> items = new ArrayList<>();
        items.add("查看好友信息");
        items.add("发起聊天");
        items.add("发起游戏邀请");
        itemMenu.setMenuItems(items);
        itemMenu.display();
    }
}
