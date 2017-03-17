package com.wtc.xmut.taoschool.Service;

import com.wtc.xmut.taoschool.domain.Shop;
import com.wtc.xmut.taoschool.domain.User;

import java.io.IOException;
import java.util.List;

/**
 * 作者 By lovec on 2017/2/23 0023.11:04
 * 邮箱 lovecanon0823@gmail.com
 */

public interface ShopService {

    void insertShop(Shop shop);

    //删
    void deleteShopById(Integer id);


    //改
    void updateShopById(Shop shop);

    //查询所有
    List<Shop> findAll() throws IOException;

    //查询ID查询
    User findShopById(Integer id);

}
