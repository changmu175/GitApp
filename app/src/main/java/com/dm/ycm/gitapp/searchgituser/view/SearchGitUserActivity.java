package com.dm.ycm.gitapp.searchgituser.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.dm.ycm.gitapp.R;
import com.dm.ycm.gitapp.searchgituser.common.SearchUtils;
import com.dm.ycm.gitapp.searchgituser.contract.SearchGitUserContract;
import com.dm.ycm.gitapp.searchgituser.data.bean.GitUser;
import com.dm.ycm.gitapp.searchgituser.presenter.SearchGitUserPresenter;
import com.dm.ycm.gitapp.searchgituser.view.adapter.SearchResultAdapter;
import com.dm.ycm.gitapp.searchgituser.view.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by ycm on 2017/5/7.
 * MVP View 处理界面逻辑
 */

public class SearchGitUserActivity extends AppCompatActivity implements SearchGitUserContract.View,
        SearchView.OnQueryTextListener {
    private String TAG = SearchGitUserActivity.class.getSimpleName();
    private SearchView searchView;
    private ListView listView;
    private TextView noResult;
    private SearchResultAdapter resultAdapter;
    private SearchGitUserPresenter presenter;
    private List<GitUser> gitUserList;
    private boolean isSearching = false;
    private LoadTask loadDataTask;
    private LoadingView loadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        presenter = new SearchGitUserPresenter();
        presenter.attachView(this);
        gitUserList = new ArrayList<>();
        resultAdapter = new SearchResultAdapter(this);
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        searchView = (SearchView) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.user_list);
        noResult = (TextView) findViewById(R.id.search_no_result);
        listView.setEmptyView(noResult);
        loadingView = new LoadingView(this, getString(R.string.loading));
        searchView.setOnQueryTextListener(this);
    }

    /**
     * 开始搜索
     * @param keyWord 搜索键
     */
    public void startToSearch(String keyWord) {
        //先停掉之前的任务
        if (loadDataTask != null && isSearching) {
            loadDataTask.cancel(true);
        }
        //清楚之前的结果
        if (resultAdapter != null) {
            resultAdapter.clear();
            resultAdapter.notifyDataSetChanged();
        }
        //输入为空时取消任务
        if (TextUtils.isEmpty(keyWord)) {
            loadDataTask.cancel(true);
            return;
        }

        loadDataTask = new LoadTask(keyWord);
        loadDataTask.executeOnExecutor(Executors.newScheduledThreadPool(20));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //开始搜索
        startToSearch(newText);
        return false;
    }

    /**
     * 显示搜索结果
     * @param dataSource 数据源
     */
    @Override
    public void showResult(Object dataSource) {
        GitUser gitUser = (GitUser) dataSource;
        Log.d(TAG, gitUser.toString());
        gitUserList.add(gitUser);
        resultAdapter.setDataSource(gitUserList);
        listView.setAdapter(resultAdapter);
    }

    /**
     * 设置是否正在搜索
     * @param isSearching 是否正在搜索
     */
    @Override
    public void setIsSearching(boolean isSearching) {
        this.isSearching = isSearching;
    }

    @Override
    public boolean isSearching() {
        return this.isSearching;
    }

    /**
     * 显示正在加载
     */
    @Override
    public void showLoading() {
        loadingView.show();
    }

    /**
     * 关闭正在加载
     */
    @Override
    public void dismiss() {
        loadingView.dismiss();
    }

    /**
     * 搜索异步任务，采用异步任务是为了方便取消任务。
     */
    private class LoadTask extends AsyncTask<Void, Integer, String> {
        String key;

        LoadTask(String key) {
            this.key = key;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isSearching = true;
        }

        @Override
        protected String doInBackground(Void... params) {
            //后台开始搜索
            return presenter.searchUserInfo(key);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            isSearching = false;
            //解析搜索结果
            List<GitUser> gitUsers = SearchUtils.parseUserInfoJson(s);
            if (gitUsers == null) {
                return;
            }
            //搜索结果为空，则清除之前的搜索结果
            if (gitUsers.isEmpty()) {
                if (resultAdapter != null) {
                    resultAdapter.clear();
                }
                if (!gitUserList.isEmpty()) {
                    gitUserList.clear();
                }
                return;
            }
            //开始搜索每个人的仓库，判断偏爱语言
            for (GitUser gitUser : gitUsers) {
                presenter.searchRepoInfo(gitUser.getUserName(), gitUser);
            }
        }
    }

}
