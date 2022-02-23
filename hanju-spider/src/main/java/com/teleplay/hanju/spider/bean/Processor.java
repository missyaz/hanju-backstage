package com.teleplay.hanju.spider.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author f
 * @desc 自定义processor类，包含url的uuid和对应的process类
 * @create 2022-02-12 13:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Processor {

    private String uuid;

    private PageProcessor processor;

}
