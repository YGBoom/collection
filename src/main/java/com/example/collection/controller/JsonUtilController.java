package com.example.collection.controller;

import com.alibaba.fastjson.JSON;
import com.example.collection.entity.User;
import com.example.collection.utils.JsonUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/json-test")
public class JsonUtilController {


    /**
     * 使用Json导出
     * @param response response
     * @param id id
     */
    @GetMapping(value = "/export")
    public void exportJson(HttpServletResponse response,
                           @RequestParam(name="id",required=false)String id,
                           @RequestParam(name="name",required=false)String name) {
        List<User> users = new ArrayList<>();
        User user1 = new User(); //根据具体业务定义
        user1.setAge(10);
        user1.setName("json");
        users.add(user1);

        User user3 = new User(); //根据具体业务定义
        user3.setAge(30);
        users.add(user3);

        //导出
        JsonUtils.exportJson(response, users, name  + "-" + UUID.randomUUID() + ".json");
    }

    /**
     * 使用Json导入
     * @param multipartFile multipartFile
     * @return Object
     */
    @PostMapping(value = "/import")
    public Object importJson(MultipartFile multipartFile) {
        final Map<String, String> result = JsonUtils.readJson(multipartFile);
        if ("200".equals(result.get("code"))) {
            final String jsonString = result.get("result");
            List<User> users = JSON.parseArray(jsonString, User.class);
            //导入
            for (User user : users) {
                System.out.println(user);
            }

        }
        return result;
    }
}
