package com.wtc.xmut.taoschool.Service;

import com.wtc.xmut.taoschool.domain.User;

import java.io.IOException;
import java.util.List;

/**
 * 作者 By lovec on 2017/2/6 0006.07:27
 * 邮箱 lovecanon0823@gmail.com
 */

public interface UserService {

    //增
    String insertUser(User user);

    //删
    void deleteUserById(Integer id);


    //改
    void updateUserById(User user);

    //查询所有
    List<User> findAll();

    //查询ID查询
    User findUserById(Integer id) throws IOException;

    //查询是否存在同一个username
    User findUserByName(String username) throws IOException;

    //查询发布的商品总数

    //登录的方法
    User login(String username,String password) throws IOException;

    boolean UpdateUser(User user) throws Exception;

}
