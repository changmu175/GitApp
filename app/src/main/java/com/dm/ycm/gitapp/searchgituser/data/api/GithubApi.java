package com.dm.ycm.gitapp.searchgituser.data.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ycm on 2017/5/7.
 * Description:API接口
 */

public interface GithubApi {
    @GET("search/users")
    Observable<String> searchUserInfo(@Query(value = "q", encoded = true) String key);

    @GET("users/{name}/repos")
    Observable<String> searchRepoInfo(@Path("name") String name);

    @GET("search/users")
    Call<String> searchUserInfo1(@Query(value = "q", encoded = true) String key);

    @GET("users/{name}/repos")
    String searchRepoInfo1(@Path("name") String name);
}
