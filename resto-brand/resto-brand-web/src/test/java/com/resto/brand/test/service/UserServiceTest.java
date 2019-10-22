package com.resto.brand.test.service;

import java.util.Date;
import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.resto.brand.core.util.ApplicationUtils;
import com.resto.brand.web.config.SpringContextConfig;
import com.resto.brand.web.model.User;
import com.resto.brand.web.service.UserService;

@ContextConfiguration(classes=SpringContextConfig.class)
public class UserServiceTest extends AbstractJUnit4SpringContextTests {

    @Resource
    private UserService userService;

    @Test
    public void test_insert() {
        User model = new User();
        model.setUsername("starzou");
        model.setPassword(ApplicationUtils.sha256Hex("123456"));
        model.setCreateTime(new Date());
        userService.insert(model);
    }

//    @Test
    public void test_10insert() {
        for (int i = 0; i < 10; i++) {
            User model = new User();
            model.setUsername("starzou" + i);
            model.setPassword(ApplicationUtils.sha256Hex("123456"));
            model.setCreateTime(new Date());
            userService.insert(model);
        }
    }

}
