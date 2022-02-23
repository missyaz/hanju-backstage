package com.teleplay.hanju.spider.utils;

import com.teleplay.hanju.common.entity.Hanju;
import com.teleplay.hanju.common.entity.Riju;
import com.teleplay.hanju.common.entity.Taiju;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * @author f
 * @desc 视频播放页url工具lei
 * @create 2022-02-17 15:24
 */
@Slf4j
public class VideoUrlUtil {

    private final static String BASE_URL = "https://www.tvn.cc";

    private final static String SUFFIX = "1-1.html";

    public static List<Request> getUrls(List<Hanju> hanjus,
                                        List<Riju> rijus,
                                        List<Taiju> taijus){
        ArrayList<String> urls = new ArrayList<>(hanjus.size() + rijus.size() + taijus.size());

        hanjus.forEach(hanju -> urls.add(hanju.getVideoPath()));
        rijus.forEach(riju -> urls.add(riju.getVideoPath()));
        taijus.forEach(taiju -> urls.add(taiju.getVideoPath()));

        ArrayList<Request> requests = new ArrayList<>(urls.size());
        urls.forEach(url->{
            String reqUrl = BASE_URL + url + SUFFIX;
            Request request = new Request(reqUrl);
            requests.add(request);
        });

        return requests;
    }

    public static String getUrl(String url){
        return BASE_URL + url;
    }

    public static Request getRequest(String url){
        Request request = new Request();
        request.setUrl(BASE_URL+url);
        request.setCharset("utf-8");
        request.setMethod("GET");
        return request;
    }
}
