package com.wtc.xmut.taoschool;

import com.wtc.xmut.taoschool.Service.UserService;
import com.wtc.xmut.taoschool.Service.impl.UserServiceImol;
import com.wtc.xmut.taoschool.domain.User;


/**
 * Example local unit test, which will execute on the development machine (host).
 * <p>
 * 城市:<input type="text" name="name">
 * 手机号:<input type="text" name="telephone">
 * ID:<input type="text" name="likecount">
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @org.junit.Test
    public void addition_isCorrect() throws Exception {

        UserService userService  = new UserServiceImol();
        User user = new User();
        user.setId(2);
        user.setPassword("123");
        user.setUsername("要去个美丽的地方");
        userService.UpdateUser(user);

        }

}