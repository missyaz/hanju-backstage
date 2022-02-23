package com.teleplay.hanju.spider.service;

import com.teleplay.hanju.spider.bean.Processor;
import us.codecraft.webmagic.Request;

import java.util.List;

/**
 * @author f
 * @desc
 * @create 2022-02-13 10:49
 */
public interface SpiderService{
    /*
      开启爬虫
     */
    public void startSpider(Processor processor);

    public  void startSpider(Processor processor,String crawlUrl);

    public  void startSpider(Processor processor, List<Request> requests);

    /*
        对video表去重
     */
    void distinctVideo();

    /**
     * 获得电视剧的详情信息
     */
    void getTeleplayDetail();
}
