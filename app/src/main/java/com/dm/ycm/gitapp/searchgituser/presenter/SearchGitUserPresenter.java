package com.dm.ycm.gitapp.searchgituser.presenter;

import com.dm.ycm.gitapp.searchgituser.common.SearchUtils;
import com.dm.ycm.gitapp.searchgituser.contract.SearchGitUserContract;
import com.dm.ycm.gitapp.searchgituser.data.ILoadData;
import com.dm.ycm.gitapp.searchgituser.data.LoadDataImp;
import com.dm.ycm.gitapp.searchgituser.data.bean.GitUser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ycm on 2017/5/7.
 * Description:MVP Presenter 逻辑处理
 */

public class SearchGitUserPresenter implements SearchGitUserContract.Presenter {
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private ILoadData iLoadData;
    private SearchGitUserContract.View view;

    public SearchGitUserPresenter() {
        iLoadData = new LoadDataImp();
    }

    /**
     * 绑定视图
     * @param view 视图
     */
    @Override
    public void attachView(SearchGitUserContract.View view) {
        this.view = view;
    }

    /**
     * 获取视图
     * @return 视图
     */
    @Override
    public SearchGitUserContract.View getView() {
        return this.view;
    }

    /**
     * 搜索用户信息
     * @param key 搜索键
     * @return 搜索结果JSON 字符串
     */
    @Override
    public String searchUserInfo(String key) {
        String jsonResult = null;
        Call<String> call = iLoadData.searchUserInfo(key);
        try {
            Response<String> response = call.execute();
            jsonResult = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * 根据名称搜索仓库
     * @param name 用户名
     * @param gitUser 用户（含有语言偏爱）
     */
    @Override
    public void searchRepoInfo(String name, final GitUser gitUser) {
        mSubscriptions
                .add(iLoadData.searchRepoInfo(name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                getView().showLoading();
                            }
                        })
                        .doOnTerminate(new Action0() {
                            @Override
                            public void call() {
                                getView().dismiss();
                            }
                        })
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(String name) {
                                //解析搜索结果
                                GitUser user = SearchUtils.parseReposInfoJson(name, gitUser);
                                getView().setIsSearching(false);
                                //显示搜索结果
                                getView().showResult(user);
                            }
                        }));
    }
}
