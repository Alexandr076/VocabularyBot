package com.azhdankov.vocabularybot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class botService {

    @Autowired
    private UserDao userDao;
    @GetMapping("/getHello")
    public String getHello() {
        UserInfo userInfo = new UserInfo();
        userInfo.setChatID("123");
        userInfo.setPassword("234");

        userDao.save(userInfo);

        return "User was added";
    }
}
