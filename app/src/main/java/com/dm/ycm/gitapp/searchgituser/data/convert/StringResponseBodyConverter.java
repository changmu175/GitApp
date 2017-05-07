package com.dm.ycm.gitapp.searchgituser.data.convert;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
/**
 * Created by ycm on 2017/5/7.
 * Description:响应字符转换
 */

public class StringResponseBodyConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
            return value.string();
        } finally {
            value.close();
        }
    }
}