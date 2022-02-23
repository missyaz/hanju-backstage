package com.teleplay.hanju.spider.processor;

import com.teleplay.hanju.spider.parse.Parse;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author f
 * @desc
 * @create 2022-02-17 15:14
 */
public class TvnVideoProcess implements PageProcessor {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36";

    private static Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setUserAgent(USER_AGENT);
    @Override
    public void process(Page page) {
        Parse.parseVideo(page);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
