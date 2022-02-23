package com.teleplay.hanju.common.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 电视剧表，包含电视剧所有信息 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-02-15
 */
@RestController
@RequestMapping("/common/hanju")
public class HanjuController {

    @GetMapping("/1")
    public String test(){
        return "OK";
    }
}

