package com.example.yemengshuai.avmoo.util;

/**
 * Created by yemengshuai on 2016/8/24.
 */
public class UriUtil {
    public static final String NEWS_LIST_URL_NEW="https://avmo.pw/cn/released/page/";
    public static final String NEWS_LIST_URL_ALL="https://avmo.pw/cn/page/";
    public static final String NEWS_LIST_URL_HOT="https://avmo.pw/cn/popular/page/";
    public static final String NEWS_LIST_URL_START="https://avmo.pw/cn/actresses/page/";

    public static String getUri(int newsType,int curPage){
        String url="";
        switch (newsType){
            case Constant.AVMOO_TYPE_START:
                url=NEWS_LIST_URL_START;
                break;
            case Constant.AVMOO_TYPE_NEW:
                url=NEWS_LIST_URL_NEW;
                break;
            case Constant.AVMOO_TYPE_ALL:
                url=NEWS_LIST_URL_ALL;
                break;
            case Constant.AVMOO_TYPE_HOT:
                url=NEWS_LIST_URL_HOT;
                break;
            default:
                break;
        }
        url+=curPage;
        return url;
    }
}
