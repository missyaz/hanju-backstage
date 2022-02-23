package com.teleplay.hanju.spider.processor;

import cn.hutool.core.util.IdUtil;
import com.teleplay.hanju.spider.parse.Parse;
import com.teleplay.hanju.spider.webmagic.HttpsClientDownloader;
import com.teleplay.hanju.spider.webmagic.RedisScheduler;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.xsoup.Xsoup;

import java.util.List;

/**
 * @author f
 * @desc
 * @create 2022-02-10 21:19
 */
@Slf4j
@Component
public class TvnIndexProcess implements PageProcessor {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36";

    private static Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUserAgent(USER_AGENT);


    public void process(Page page) {
        Parse.parseIndex(page);
    }

    public Site getSite() {
        return site;
    }

}
