package com.dm.ycm.gitapp.searchgituser.common;

import android.text.TextUtils;

import com.dm.ycm.gitapp.searchgituser.data.bean.GitUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ycm on 2017/5/7.
 * Description:搜索工具类
 */

public class SearchUtils {

    private static final String ITEMS = "items";
    private static final String AVATAR_URL = "avatar_url";
    private static final String LOGIN = "login";
    private static final String LANGUAGE = "language";
    /**
     * 解析搜索结果json
     * @param json 搜索结果
     * @return 用户列表（没有偏爱语言属性）
     */
    public static List<GitUser> parseUserInfoJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        JSONObject jsonObject = null;
        JSONArray jsonArray;
        List<GitUser> gitUserList = new ArrayList<>();
        try {
            jsonObject = new JSONObject(json);
            jsonArray = jsonObject.getJSONArray(ITEMS);
            for (int i = 0; i < jsonArray.length(); i++) {
                GitUser gitUser = new GitUser();
                JSONObject object = jsonArray.getJSONObject(i);
                gitUser.setAvatarUrl(object.getString(AVATAR_URL));
                gitUser.setUserName(object.getString(LOGIN));
                gitUserList.add(gitUser);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject == null) {
            return null;
        }
        return gitUserList;
    }

    /**
     * 解析搜索的仓库JSON
     * @param json 仓库JSON
     * @param gitUser 用户
     * @return 用户
     */
    public static GitUser parseReposInfoJson(String json, GitUser gitUser) {
        int max = 0;
        Map<String, Integer> hashMap = new HashMap<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String language = object.getString(LANGUAGE);
                if (language != null) {
                    Integer value = hashMap.get(language);
                    int count = value == null ? 0 : value;
                    hashMap.put(language, ++count);
                    max = count > max ? count : max;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<String> languages = favorLanguage(hashMap, max);
        if (languages.isEmpty()) {
            gitUser.setLanguages(null);
        } else {
            gitUser.setLanguages(languages);
        }
        return gitUser;
    }

    /**
     * 统计偏爱编程语言
     * @param hashMap 语言信息
     * @param count 使用偏爱语言的最高次数
     * @return 偏爱语言（可能不止一个）
     */
    private static List<String> favorLanguage(Map<String, Integer> hashMap, int count) {
        List<String> favorLanguages = new ArrayList<>();
        if (hashMap == null) {
            return null;
        }
        for (String key : hashMap.keySet()) {
            if (count == hashMap.get(key)) {
                favorLanguages.add(key);
            }
        }
        return favorLanguages;
    }
}
