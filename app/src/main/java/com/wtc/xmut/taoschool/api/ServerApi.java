package com.wtc.xmut.taoschool.api;

/**
 * 作者 By lovec on 2017/1/31 0031.15:49
 * 邮箱 lovecanon0823@gmail.com
 */

public class ServerApi {

    //private static final String Host = "Http://123.56.223.17:8080"; //服务器地址
    private static final String Host = "Http://10.0.2.2:8080"; //手机测试地址
    //private static final String Host = "http://127.0.0.1:8080";  //Junit测试专用地址
    private static final String SERVERHOST = Host + "/TaoSchool";

    //用户api
    private static final String USERHOST = SERVERHOST + "/User";
    //用户注册
    public static final String USERADD = USERHOST + "/addUser.do";
    //用户登录
    public static final String USERLOGIN = USERHOST + "/login.do";

    //根据姓名查找用户
    public static final String FINDUSER = USERHOST + "/getUserByName.do";


}
