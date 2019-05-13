package com.tssss.bysj.game.news.presenter;

import android.os.Handler;

import com.tssss.bysj.game.news.News;
import com.tssss.bysj.game.news.PictureNews;
import com.tssss.bysj.game.news.TextNews;
import com.tssss.bysj.game.news.TextPicNews;
import com.tssss.bysj.game.news.contract.INewsOtherContract;
import com.tssss.bysj.http.HttpUrl;
import com.tssss.bysj.http.IHttpCallback;
import com.tssss.bysj.http.OkHttpProvider;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OtherNewsPresenter extends BaseMvpPresenter<INewsOtherContract.IVew>
        implements INewsOtherContract.IPresenter {

    private boolean cancelLoadNews;
    private Handler handler;

    public OtherNewsPresenter(INewsOtherContract.IVew view) {
        super(view);
        handler = new Handler();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelLoadNews = true;
    }

    @Override
    public void destroyView() {
        super.destroyView();
        cancelLoadNews = true;
    }

    @Override
    public void onViewDestroy() {
        super.onViewDestroy();
        cancelLoadNews = true;
    }


    @Override
    public void loadNews() {
        Map<String, String> paraMap = new HashMap<>();
        paraMap.put(Constant.ACCOUNT_ID, UserDataCache.readAccount(Constant.ACCOUNT_ID));
        OkHttpProvider.getInstance().requestAsyncGet(HttpUrl.URL_NEWS_OTHER, paraMap, new IHttpCallback() {
            @Override
            public void onSuccess(String result) {
                if (!cancelLoadNews) {
                    if (StringUtil.isBlank(result)) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                getView().showNews(null);
                            }
                        });

                    } else {
                        try {
                            List<News> newsList = new ArrayList<>();
                            JSONObject resultJson = new JSONObject(result);
                            JSONArray resultJsonArray = resultJson.getJSONArray(Constant.JSON_KEY_NEWS);
                            for (int i = 0; i < resultJsonArray.length(); i++) {
                                JSONObject singleNewsJson = (JSONObject) resultJsonArray.get(i);
                                int newsType = singleNewsJson.getInt(Constant.JSON_KEY_NEWS_TYPE);
                                if (newsType == News.STYLE_TEXT) {
                                    newsList.add(new TextNews(
                                            singleNewsJson.getString(Constant.JSON_KEY_NEWS_TEXT_CONTENT),
                                            singleNewsJson.getString(Constant.JSON_KEY_NEWS_TEXT_FROM),
                                            singleNewsJson.getString(Constant.JSON_KEY_NEWS_TEXT_TIME)
                                    ));

                                } else if (newsType == News.STYLE_PICTURE) {
                                    List<String> pictures = new ArrayList<>();
                                    JSONArray picturesJsonArray = singleNewsJson.getJSONArray(Constant.JSON_KEY_NEWS_PICTURE_CONTENT);
                                    for (int a = 0; a < picturesJsonArray.length(); a++) {
                                        pictures.add(picturesJsonArray.getString(a));
                                    }
                                    newsList.add(new PictureNews(
                                            singleNewsJson.getString(Constant.JSON_KEY_NEWS_PICTURE_FROM),
                                            singleNewsJson.getString(Constant.JSON_KEY_NEWS_PICTURE_TIME),
                                            pictures));
                                } else if (newsType == News.STYLE_TEXT_PICTURE) {
                                    List<String> pictures = new ArrayList<>();
                                    JSONArray picturesJsonArray = singleNewsJson.getJSONArray(Constant.JSON_KEY_NEWS_TP_CONTENT_PICTURE);
                                    for (int a = 0; a < picturesJsonArray.length(); a++) {
                                        pictures.add(picturesJsonArray.getString(a));
                                    }
                                    newsList.add(new TextPicNews(
                                            singleNewsJson.getString(Constant.JSON_KEY_NEWS_TP_CONTENT_TEXT),
                                            singleNewsJson.getString(Constant.JSON_KEY_NEWS_TP_FROM),
                                            singleNewsJson.getString(Constant.JSON_KEY_NEWS_TP_TIME),
                                            pictures
                                    ));

                                }
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showNews(newsList);
                                }
                            });
                        } catch (JSONException e) {
                            Logger.log("json解析异常");
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showNews(null);
                                }
                            });
                        }

                    }
                } else {
                    Logger.log("操作取消，丢弃数据");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Logger.log("获取动态数据失败");
            }
        });
    }

    @Override
    protected INewsOtherContract.IVew getEmptyView() {
        return INewsOtherContract.emptyView;
    }
}
