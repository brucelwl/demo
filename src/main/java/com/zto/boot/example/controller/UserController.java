package com.zto.boot.example.controller;

import com.zto.boot.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bruce on 2019/7/1 10:37
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @PostConstruct
    public void init() {
        log.info("" + userService.toString());
    }

    @GetMapping("/exce")
    public String exception() {
        int a = 10 / 0;

        return "......";
    }

    private List<String> getClasspath() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader instanceof URLClassLoader) {
            URL[] urLs = ((URLClassLoader) classLoader).getURLs();
            if (urLs == null || urLs.length == 0) {
                ClassLoader parent = classLoader.getParent();
                if (parent instanceof URLClassLoader) {
                    urLs = ((URLClassLoader) parent).getURLs();
                }
            }
            if (urLs != null && urLs.length > 0) {
                ArrayList<String> jarUrLs = new ArrayList<>();
                for (URL urL : urLs) {
                    jarUrLs.add(urL.toExternalForm());
                }
                return jarUrLs;
            }
        }
        return Collections.emptyList();
    }

    @GetMapping("/jar/{html}")
    public Object dependentJar(@PathVariable String html) {
        List<String> classpath = getClasspath();
        if ("html".equalsIgnoreCase(html)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < classpath.size(); i++) {
                stringBuilder.append(i).append("-->").append(classpath.get(i)).append("<br/>");
            }
            return stringBuilder.toString();
        }
        return classpath;
    }


}
