package com.example.yemengshuai.avmoo.util;



import com.example.yemengshuai.avmoo.bean.CommonException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yemengshuai on 2016/8/24.
 */
public class DataUtil {
    /**
     * 返回该链接地址的html字符串数据
     */
    public static String doGet(String urlStr)throws CommonException{
        StringBuffer sb=new StringBuffer();
        try{
            URL url=new URL(urlStr);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            int resultCode =conn.getResponseCode();
            if (conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                InputStreamReader reader=new InputStreamReader(is,"UTF-8");
                char[] ch=new char[1024];
                int length=0;
                while ((length=reader.read(ch))!=-1){
                    sb.append(new String(ch,0,length));
                }
                is.close();
                reader.close();
            }else {
                throw new CommonException("网络链接失败");
            }



        }catch (Exception e){
            throw new CommonException("网络连接失败");
        }return sb.toString();
    }
}
