package com.example.yemengshuai.avmoo.bean;

/**
 * Created by yemengshuai on 2016/8/24.
 */
public class CommonException extends Exception{
    private static final long serialVersionUID =1L;
    public CommonException(){
        super();
    }
    public CommonException(String message,Throwable arg){
        super(message,arg);
    }
    public CommonException(String message){
        super(message);
    }
    public CommonException(Throwable arg){
        super(arg);
    }
}
