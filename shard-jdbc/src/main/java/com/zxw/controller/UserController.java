package com.zxw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxw.pojo.User;
import com.zxw.service.UserService;
import io.shardingsphere.core.api.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @author zxw
 * @date 2019/8/29 20:23
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list() {
        HintManager.getInstance().setMasterRouteOnly();
        QueryWrapper wrapper = new QueryWrapper();
        List<User> list = userService.selectList(wrapper);

        return list;
    }

    @GetMapping("/add")
    public void addUser() {
        for (long i = 0; i < 100; i++) {
            User u = new User();
            u.setId(i);
            int random = new Random().nextInt(100);
            if (random % 2 == 0) {
                u.setCity("上海");
            } else {
                u.setCity("杭州");
            }
            u.setName("李四" + i);
            userService.insert(u);
        }
    }


}
