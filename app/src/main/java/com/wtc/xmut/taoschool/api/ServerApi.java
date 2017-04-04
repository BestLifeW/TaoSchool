package com.wtc.xmut.taoschool.api;

/**
 * 作者 By lovec on 2017/1/31 0031.15:49
 * 邮箱 lovecanon0823@gmail.com
 */

public class ServerApi {

    //private static final String Host = "http://123.56.223.17:8080"; //服务器地址
    private static final String Host = "http://10.0.2.2:8080"; //手机测试地址
    //private static final String Host = "http://192.168.1.100:8080";
    //private static final String Host = "http://127.0.0.1:8080";  //Junit测试专用地址
    private static final String SERVERHOST = Host + "/TaoSchool";

    //用户api
    private static final String USERHOST = SERVERHOST + "/User";/**/
    //用户注册
    public static final String USERADD = USERHOST + "/addUser.do";
    //用户登录
    public static final String USERLOGIN = USERHOST + "/login.do";
    //根据姓名查找用户
    public static final String FINDUSERBYNAME = USERHOST + "/getUserByName";  //使用方法 /getUserByName/lovec
    //根据用户ID查找用户
    public static final String FINDUSERBYID = USERHOST + "/getUserById";
    //更新用户信息
    public static final String UPDATEUSER = USERHOST + "/updateUser.do";
    //用户上传头像
    public static final String USERHEAD = USERHOST + "/upload.do";
    //图片显示的api
    public static final String SHOWPIC = Host;


    //商品操作
    public static final String SHOPHOST = SERVERHOST + "/Shop";

    //商品的添加
    public static final String SHOPADD = SHOPHOST + "/addShop.do";

    //查询所有商品
    public static final String ALLSHOP = SHOPHOST + "/allShop.do";

    //查询所有商品和用户信息
    public static final String ALLSHOPANDUSER = SHOPHOST + "/allShopAndUser.do";

    //根据商品的ID查询商品信息
    public static final String GETSHOPBYID = SHOPHOST + "/getShopByShopID/";

    //判断网络连接
    public static final String ISCONNECT = SERVERHOST + "/getConnect/getNet.do";


    //点赞的API
    public static final String USERLIKE = SERVERHOST + "/UserLike";


    // 判断是否被点赞 是的话 那就删除 不是就增加
    public static final String ISLOVE = USERLIKE + "/isLove.do"; //UserLike/isLoveShow.do/lovec/1  调用的模式


    //判断是否有赞
    public static final String ISLOVESHOW = USERLIKE + "/isLoveShow.do/";


    //判断有几个赞
    public  static final String COUNTLOVEBYUSERNAME = USERLIKE + "/countUserLove/";   //UserLike/countUserLove/lovec 调用格式



    //评论的API
      static  final  String COMMENT = SERVERHOST + "/Comment";

    //评论的方法 通过 shopId 获取
    public  static final  String GETCOMMENT = COMMENT +"/getComment/"; //同过/getComment/1 获得商品的评论

    //评论的方法 通过POST表单提交
    public  static  final  String ADDCOMMENT = COMMENT +"/addComment.do";
}
