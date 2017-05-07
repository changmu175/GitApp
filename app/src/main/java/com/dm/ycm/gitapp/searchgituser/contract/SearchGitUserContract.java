package com.dm.ycm.gitapp.searchgituser.contract;

import com.dm.ycm.gitapp.searchgituser.BasePresenter;
import com.dm.ycm.gitapp.searchgituser.BaseView;
import com.dm.ycm.gitapp.searchgituser.data.bean.GitUser;

/**
 * Created by ycm on 2017/5/7.
 * Description:MVP中联系Presenter 和 View
 */

public interface SearchGitUserContract {

    interface View<D> extends BaseView<Presenter> {
        /**
         * 显示结果
         * @param dataSource 数据
         */
        void showResult(D dataSource);

        /**
         * 设置搜索状态
         * @param isSearching 是否正在搜素
         */
        void setIsSearching(boolean isSearching);

        /**
         * 获取搜索状态
         * @return 是否正在搜索
         */
        boolean isSearching();

        /**
         * 显示正在加载
         */
        void showLoading();

        /**
         * 取消正在加载
         */
        void dismiss();
    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 绑定视图
         * @param view 视图
         */
        void attachView(View view);

        /**
         * 获取视图
         * @return 视图
         */
        View getView();

        /**
         * 搜索用户信息
         * @param key 搜索键
         * @return 搜索结果JSON
         */
        String searchUserInfo(String key);

        /**
         * 搜索用户的仓库信息
         * @param name 用户名
         * @param gitUser 用户对象
         */
        void searchRepoInfo(String name, GitUser gitUser);
    }
}
