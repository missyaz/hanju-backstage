package com.teleplay.hanju.spider.controller;

import cn.hutool.core.util.IdUtil;
import com.teleplay.hanju.spider.processor.TvnIndexProcess;
import com.teleplay.hanju.spider.processor.TvnTelePlayProcess;
import com.teleplay.hanju.spider.processor.TvnVideoProcess;
import com.teleplay.hanju.spider.service.SpiderService;
import com.teleplay.hanju.spider.bean.Processor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Request;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author f
 * @desc
 * @create 2022-02-13 10:48
 */
@Controller
public class SpiderController {

    @Resource(name = "crawlService")
    private SpiderService spiderService;

    @GetMapping("/index")
    @ResponseBody
    public String getIndex(){
        // Processor processor = new Processor(IdUtil.simpleUUID(),new TvnProcess(),"https://www.tvn.cc");
        Processor processor = new Processor(IdUtil.simpleUUID(),new TvnIndexProcess());
        spiderService.startSpider(processor,"https://www.tvn.cc/");
        return "OK";
    }

    @GetMapping("/teleplay")
    @ResponseBody
    public String getTeleplay(){
        ArrayList<Request> requests = new ArrayList<>();
        Request hanjuRequest = new Request("https://www.tvn.cc/hanju/");
        Request rijuRequest = new Request("https://www.tvn.cc/dianshiju/");
        Request taijuRequest = new Request("https://www.tvn.cc/taiju/");

        requests.add(hanjuRequest);
        requests.add(rijuRequest);
        requests.add(taijuRequest);

        Processor processor = new Processor(IdUtil.simpleUUID(),new TvnTelePlayProcess());
        spiderService.startSpider(processor,requests);
        return "OK";
    }

    @GetMapping("/video")
    @ResponseBody
    public String getVideo(){
        Processor processor = new Processor(IdUtil.simpleUUID(),new TvnVideoProcess());
        spiderService.startSpider(processor);
        return "OK";
    }

    @GetMapping("/detail")
    @ResponseBody
    public String getDetail(){
        spiderService.getTeleplayDetail();
        return "OK";
    }

}
