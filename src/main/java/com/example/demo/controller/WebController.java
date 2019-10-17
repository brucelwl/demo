package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by bruce on 2019/9/11 14:23
 */
@RestController
public class WebController {


    @GetMapping("/index")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // int a = 10 / 0;

        response.sendError(401, "this is error info");

    }


    //@RequestMapping("/error")
    //public void error(Exception e){
    //
    //    System.out.println(e.getMessage());
    //
    //}

    //@Override
    //public String getErrorPath() {
    //    return "/error";
    //}
}
