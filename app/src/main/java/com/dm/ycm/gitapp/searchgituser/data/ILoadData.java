package com.dm.ycm.gitapp.searchgituser.data;

import retrofit2.Call;
import rx.Observable;

/**
 * Created by ycm on 2017/5/7.
 * Description:加载数据接口
 */

public interface ILoadData {
    /**
     * 搜索用户仓库信息
     * @param name 用户名
     * @return 搜索结果
     */
    Observable<String> searchRepoInfo(String name);

    /**
     * 搜索用户信息
     * @param key 搜索键
     * @return 搜索结果
     */
    Call<String> searchUserInfo(String key);

}
