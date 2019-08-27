package com.zxw.controller;

import com.zxw.mapper.PersonRepositroy;
import com.zxw.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxw
 * @date 2019/8/27 20:31
 */
@RestController
public class PersonController {
    @Autowired
    PersonRepositroy personRepositroy;

    @GetMapping("/hello")
    public String hello(){
        Person p = new Person("aaa","aa");
        personRepositroy.save(p);
        return "";
    }
}
