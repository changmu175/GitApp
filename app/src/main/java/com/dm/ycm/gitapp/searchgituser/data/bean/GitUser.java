package com.dm.ycm.gitapp.searchgituser.data.bean;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by ycm on 2017/5/7.
 * Description:用户实体
 */

public class GitUser {
    private String userName;
    private String avatarUrl;
    private List<String> languages;

    public GitUser() {

    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getLanguages() {
        StringBuilder sb = new StringBuilder();
        if (languages != null && !languages.isEmpty()) {
            for (String language : languages) {
                if (!TextUtils.equals(language, "null")) {
                    sb.append(language).append(" ");
                }
            }
            return sb.toString();
        }
        return " ";
    }

    @Override
    public String toString() {
        return "username: " + userName + "\t"
                + "avatarUrl" + avatarUrl + "\t"
                + "languages" + getLanguages();
    }
}
