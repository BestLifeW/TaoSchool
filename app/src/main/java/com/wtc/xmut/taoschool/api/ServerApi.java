package com.wtc.xmut.taoschool.api;

/**
 * 作者 By lovec on 2017/1/31 0031.15:49
 * 邮箱 lovecanon0823@gmail.com
 */

public class ServerApi {

    // private static final String Host = "http://123.56.223.17:8080"; //服务器地址
    private static final String Host = "http://10.0.2.2:8080"; //手机测试地址
    // private static final String Host = "http://100.84.125.19:8080";
    //private static final String Host = "http://127.0.0.1:8080";  //Junit测试专用地址
    private static final String SERVERHOST = Host + "/TaoSchool";

    //-----用户api
    private static final String USERHOST = SERVERHOST + "/User";/**/
    //用户注册
    public static final String USERADD = USERHOST + "/addUser.do";
    //用户登录
    public static final String USERLOGIN = USERHOST + "/login.do";
    //根据姓名查找用户
    public static final String FINDUSERBYNAME = USERHOST + "/getUserByName/";  //使用方法 /getUserByName/lovec
    //根据用户ID查找用户
    public static final String FINDUSERBYID = USERHOST + "/getUserById/";
    //更新用户信息
    public static final String UPDATEUSER = USERHOST + "/updateUser.do";
    //用户上传头像
    public static final String USERHEAD = USERHOST + "/upload.do";
    //图片显示的api
    public static final String SHOWPIC = Host;


    //-----商品操作
    public static final String SHOPHOST = SERVERHOST + "/Shop";

    //商品的添加
    public static final String SHOPADD = SHOPHOST + "/addShop.do";

    //查询所有商品
    public static final String ALLSHOP = SHOPHOST + "/allShop.do";

    //查询所有商品和用户信息
    public static final String ALLSHOPANDUSER = SHOPHOST + "/allShopAndUser.do";

    //根据商品的ID查询商品信息
    public static final String GETSHOPBYID = SHOPHOST + "/getShopByShopID/";

    //根据商品id和用户姓名获取 提交时的显示信息
    public static final String GETSUBMITDETAIL = SHOPHOST + "/getSubmit";

    //根据用户名查询该用户的所有商品
    public static final String GETSHOPBYUSERNAME = SHOPHOST + "/getShopByUserName/";

    //判断网络连接
    public static final String ISCONNECT = SERVERHOST + "/getConnect/getNet.do";

    //根据id删除商品
    public static final String DELSHOPBYID = SHOPHOST + "/deleteShopById/"; //1

    //根据商品种类获取商品信息
    public static final String GETSHOPBYCATEGORY = SHOPHOST + "/getShopsByCategory/"; //数码

    //根据用户的点赞信息获取商品信息
    public static final String GETSHOPBYLIKENAME = SHOPHOST + "/getShopsByLikeUsername/";



    //-----点赞的API
    public static final String USERLIKE = SERVERHOST + "/UserLike";


    // 判断是否被点赞 是的话 那就删除 不是就增加
    public static final String ISLOVE = USERLIKE + "/isLove.do"; //UserLike/isLoveShow.do/lovec/1  调用的模式


    //判断是否有赞
    public static final String ISLOVESHOW = USERLIKE + "/isLoveShow.do/";


    //判断有几个赞
    public static final String COUNTLOVEBYUSERNAME = USERLIKE + "/countUserLove/";   //UserLike/countUserLove/lovec 调用格式


    //-----评论的API
    static final String COMMENT = SERVERHOST + "/Comment";

    //评论的方法 通过 shopId 获取
    public static final String GETCOMMENT = COMMENT + "/getComment/"; //同过/getComment/1 获得商品的评论

    //评论的方法 通过POST表单提交
    public static final String ADDCOMMENT = COMMENT + "/addComment.do";


    //-----订单的API
    static final String ORDER = SERVERHOST + "/Order";

    //提交订单的
    public static final String ADDORDER = ORDER + "/addOrder.do";

    //根据商品id获取是否拍下
    public static final String ISORDER = ORDER + "/getOrderByShopid/";   //9

    //根据用户姓名获取 商品订单

    public static final String GETORDERBYUSERNAME = ORDER + "/getOrdersByUsername/"; //lovec

    //根据订单ID修改订单信息
    public static final String UPDATEORDERBYID = ORDER + "/updateOrderById.do";//修改信息

    //根据商品id获取订单信息
    public static final String GETORDERBYSHOPID = ORDER + "/getOrdersByShopid/"; //9

    //根据id删除订单
    public static final String DELORDERBYID = ORDER + "/delOrderById/";//1


    //根据买家姓名获取订单
    public static final String GETORDERBYBUYERNAME = ORDER + "/getOrdersBybuyer/"; //\

    //获得买到和卖出去的数据
    public static final String GETSHOPBYSALLORBUYER = ORDER + "/getOrdersByBuyerOrSaller/"; //1表示买家，2表示卖家

    //添加求购商品

    static final String INQUIRY = SERVERHOST + "/Inquiry";

    //提交订单
    public static final String ADDINQUIRY = INQUIRY + "/addInquiry.do";


    //显示界面
    public static final String GETINQUIRY = INQUIRY + "/getInquiryAndUser.do";

    //获取对应信息
    public static final String GETINQUIRYBYID = INQUIRY + "/getInquiryAndUserByInquiryId/"; //5


    public static final String INQUIRYCOMMENT = SERVERHOST + "/InquiryComment";

    //获取求购商品留言的Api
    public static final String GETCOMMENTBYINQUIRYID = INQUIRYCOMMENT + "/getComment/"; //1

    //添加求购商品留言 addComment.do
    public static final String ADDINQUIRYCONMENT = INQUIRYCOMMENT + "/addComment.do";



}
