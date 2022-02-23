package com.teleplay.hanju.spider.service.impl;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teleplay.hanju.common.entity.Hanju;
import com.teleplay.hanju.common.entity.Riju;
import com.teleplay.hanju.common.entity.Taiju;
import com.teleplay.hanju.common.mapper.*;
import com.teleplay.hanju.spider.pipeline.CustomPipeline;
import com.teleplay.hanju.spider.processor.TvnTelePlayProcess;
import com.teleplay.hanju.spider.processor.TvnVideoDetailProcess;
import com.teleplay.hanju.spider.service.SpiderService;
import com.teleplay.hanju.spider.utils.VideoUrlUtil;
import com.teleplay.hanju.spider.bean.Processor;
import com.teleplay.hanju.spider.webmagic.HttpsClientDownloader;
import com.teleplay.hanju.spider.webmagic.RedisScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author f
 * @desc
 * @create 2022-02-13 10:50
 */
@Service("crawlService")
@RequiredArgsConstructor
@Slf4j
public class SpiderServiceImpl implements SpiderService {

    private final IndexMapper indexMapper;

    private final HanjuMapper hanjuMapper;

    private final RijuMapper rijuMapper;

    private final TaijuMapper taijuMapper;

    private final VideoMapper videoMapper;

    private final DetailMapper detailMapper;

    static {
        FileReader ipProxyReader = new FileReader("IpProxy.txt");
        HttpsClientDownloader httpsClientDownloader = new HttpsClientDownloader();
        ipProxyReader.readLines().forEach((item)-> {
            String host = item.split(":")[0];
            int port = Integer.valueOf(item.split(":")[1]).intValue();
            httpsClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy(host,port)));
        });
    }

    @Override
    public  void startSpider(Processor processor) {
        QueryWrapper wrapper = new QueryWrapper<>().select("id", "name", "video_path");
        List<Hanju> hanjus = hanjuMapper.selectList(wrapper);
        List<Riju> rijus = rijuMapper.selectList(wrapper);
        List<Taiju> taijus = taijuMapper.selectList(wrapper);

        List<Request> requests = VideoUrlUtil.getUrls(hanjus,rijus,taijus);

        Spider.create(processor.getProcessor())
                .addPipeline(new CustomPipeline(indexMapper,hanjuMapper,rijuMapper,taijuMapper,videoMapper,detailMapper))
                .setUUID(processor.getUuid())
                .setDownloader(new HttpsClientDownloader())
                .startRequest(requests)
                .thread(5)
                // .setScheduler(new RedisScheduler(new JedisPool()))
                .run();
    }

    @Override
    public void startSpider(Processor processor,String crawlUrl) {
        Spider.create(processor.getProcessor())
                .addPipeline(new CustomPipeline(indexMapper,hanjuMapper,rijuMapper,taijuMapper,videoMapper,detailMapper))
                .setUUID(processor.getUuid())
                .setDownloader(new HttpsClientDownloader())
                .addUrl(crawlUrl)
                .thread(5)
                // .setScheduler(new RedisScheduler(new JedisPool()))
                .run();
    }

    @Override
    public  void startSpider(Processor processor, List<Request> requests) {
        Spider.create(processor.getProcessor())
                .addPipeline(new CustomPipeline(indexMapper,hanjuMapper,rijuMapper,taijuMapper,videoMapper,detailMapper))
                .setUUID(processor.getUuid())
                .setDownloader(new HttpsClientDownloader())
                .startRequest(requests)
                .thread(5)
                // .setScheduler(new RedisScheduler(new JedisPool()))
                .run();
    }

    @Override
    public void distinctVideo() {

    }

    @Override
    public void getTeleplayDetail() {
        // 查询电视剧详情页
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.select("video_path");
        List<Hanju> hanjuVideoPathList = hanjuMapper.selectList(wrapper);
        List<Riju> rijuVideoPathList = rijuMapper.selectList(wrapper);
        List<Taiju> taijuVideoPathList = taijuMapper.selectList(wrapper);

        // 构造请求队列
        List<Request> detailList= new ArrayList<>(hanjuVideoPathList.size()+rijuVideoPathList.size()+taijuVideoPathList.size());
        hanjuVideoPathList.forEach(hanju->{
            String detailUrl = hanju.getVideoPath();
            detailList.add(VideoUrlUtil.getRequest(detailUrl));
        });
        rijuVideoPathList.forEach(riju->{
            String detailUrl = riju.getVideoPath();
            detailList.add(VideoUrlUtil.getRequest(detailUrl));
        });
        hanjuVideoPathList.forEach(taiju->{
            String detailUrl = taiju.getVideoPath();
            detailList.add(VideoUrlUtil.getRequest(detailUrl));
        });
        // detailList.stream().forEach(System.out::println);
        Processor processor = new Processor(IdUtil.simpleUUID(), new TvnVideoDetailProcess());
        this.startSpider(processor,detailList);
    }
}
