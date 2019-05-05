package com.tssss.bysj.game.friend;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.componet.menu.Menu;
import com.tssss.bysj.componet.menu.OnMenuItemClickListener;
import com.tssss.bysj.game.role.UserInfoActivity;
import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.game.im.ChatActivity;
import com.tssss.bysj.game.im.JMessageManager;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.util.AnimationUtil;
import com.tssss.bysj.util.JMessageUtil;
import com.tssss.bysj.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
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
        JMessageManager.handlerMessageEvent(event, this);
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
    /*private ImageButton mFriendsBackIb, mSeekFriendsIb;
    private ImageView mFriendsNullIv;
    private GTextView mFriendsNumberGtv;

    private FriendsPresenter friendsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friendsPresenter.loadFriends(
                UserManager.getUserManager().getUser().getUserId(),
                this);
    }

    @Override
    protected PresenterImp attachPresenter() {
        friendsPresenter = new FriendsPresenter();
        return friendsPresenter;
    }

    @Override
    protected void findViews() {
        mFriendsBackIb = findViewById(R.id.friends_back_ib);
        mSeekFriendsIb = findViewById(R.id.friends_seek_friends_ib);
    }

    @Override
    protected void setEventListeners() {
        mFriendsBackIb.setOnClickListener(this);
        mSeekFriendsIb.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friends_back_ib:
                finish();
                break;
            case R.id.friends_seek_friends_ib:
                openActivity(PeopleActivity.class);
                break;
        }
    }

    @Override
    public void showNullFriends() {
        mFriendsNullIv.setVisibility(View.VISIBLE);
        mSeekFriendsIb.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFriends(List<GameRole> friends) {
        mFriendsNumberGtv.setText("当前好友数：" + friends.size());
    }*/


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
        friendList.add(JMessageUtil.invertUserInfoToGameRole(userInfoA[0]));
        adapter.notifyItemInserted(0);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v, int position) {
        if (null == friendList || friendList.size() <= 0) {
            return;
        }
        GameRole gameRole = friendList.get(position);
        String myName = JMessageClient.getMyInfo().getUserName();
        itemMenu = new Menu(this, new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View v, int position) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(FriendsActivity.this, UserInfoActivity.class);
                        intent.putExtra(Constant.ACCOUNT_ID, gameRole.getUser().getUserId());
                        startActivity(intent);
                        itemMenu.dismiss();
                        break;
                    case 1:
                        Intent chatIntent = new Intent(FriendsActivity.this, ChatActivity.class);
                        chatIntent.putExtra(Constant.ACCOUNT_ID, gameRole.getUser().getUserId());
                        chatIntent.putExtra(Constant.ROLE_NICK_NAME, gameRole.getName());
                        startActivity(chatIntent);
                        itemMenu.dismiss();
                        break;
                    case 2:
                        AlertDialog.Builder builder = new AlertDialog.Builder(FriendsActivity.this)
                                .operationType(AlertDialog.OPERATION_TYPE_NO)
                                .desc("请求发送中");
                        builder.display();
                        String friendAccountId = gameRole.getUser().getUserId();
                        Conversation mConversation = JMessageClient.getSingleConversation(friendAccountId, null);
                        if (mConversation == null) {
                            mConversation = Conversation.createSingleConversation(friendAccountId, null);
                        }

                        //构造message content对象
                        String msg = myName + " 向你发来游戏请求";
                        TextContent textContent = new TextContent(msg);
                        textContent.setStringExtra("game_invitation", "game_invitation");

                        //创建message实体，设置消息发送回调。
                        final Message message = mConversation.createSendMessage(textContent, friendAccountId);
                        message.setOnSendCompleteCallback(new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                if (i == 0) {
                                    builder.dismiss();
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.showToast(FriendsActivity.this, "发送完成", ToastUtil.TOAST_DEFAULT);
                                        }
                                    });
                                }
                            }
                        });
                        MessageSendingOptions options = new MessageSendingOptions();
                        options.setShowNotification(true);
                        options.setRetainOffline(false);
                        JMessageClient.sendMessage(message, options);
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
