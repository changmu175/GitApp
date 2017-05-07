package com.dm.ycm.gitapp.searchgituser.data;

import com.dm.ycm.gitapp.searchgituser.data.api.GithubApi;
import com.dm.ycm.gitapp.searchgituser.data.config.Config;
import com.dm.ycm.gitapp.searchgituser.data.service.GithubService;

import retrofit2.Call;
import rx.Observable;

/**
 * Created by ycm on 2017/5/7.
 * 加载数据实现
 */

public class LoadDataImp implements ILoadData {
    private GithubApi mGithubService;

    public LoadDataImp() {
        this.mGithubService = GithubService.createRetrofitService(GithubApi.class);
    }

    /**
     * 根据名称搜索仓库
     * @param name 名称
     * @return 搜索结果
     */
    @Override
    public Observable<String> searchRepoInfo(String name) {
        return mGithubService.searchRepoInfo(name);
    }

    /**
     * 搜索用户信息
     * @param key 搜索键
     * @return 搜索结果
     */
    @Override
    public Call<String> searchUserInfo(String key) {
        return mGithubService.searchUserInfo1(key+ Config.SEARCH_FIELD);
    }

}
