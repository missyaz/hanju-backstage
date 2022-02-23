package com.teleplay.hanju.front.controller;

import com.teleplay.hanju.front.bean.Result;
import com.teleplay.hanju.front.service.FrontService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author f
 * @desc
 * @create 2022-02-19 9:38
 */
@RestController
public class FrontController {

    @Resource
    private FrontService frontService;

    @GetMapping("/index")
    public Result getIndex(){
        return Result.ok().data("data",frontService.findIndex());
    }


    @GetMapping("/play/{teleplayName}/{no}")
    public Result getVideo(@PathVariable("teleplayName") String teleplayName,
                           @PathVariable(value = "no") int no){
        return Result.ok().data("data",frontService.findVideoByTeleplayName(teleplayName,no));
    }

    @GetMapping("/favorite/{category}/{limit}")
    public Result getFavorite(@PathVariable("category")String category,
                              @PathVariable("limit")Integer limit){
        return Result.ok().data("data",frontService.findFavorite(category,limit));
    }

    @GetMapping("/today/{category}/{limit}")
    public Result getToday(@PathVariable("category")String category,
                           @PathVariable("limit")Integer limit){
        return Result.ok().data("data",frontService.findToday(category,limit));
    }

    @GetMapping("/detail/{category}/{name}")
    public Result getDetail(@PathVariable("name")String name,
                            @PathVariable("category")String category){
        return Result.ok().data("data",frontService.findDetail(name,category));
    }
}
