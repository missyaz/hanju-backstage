package com.teleplay.hanju.spider.parse;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.teleplay.hanju.common.entity.*;
import com.teleplay.hanju.spider.utils.VideoUrlUtil;
import com.teleplay.hanju.spider.vo.IndexVo;
import com.teleplay.hanju.spider.vo.Teleplay;
import com.teleplay.hanju.spider.vo.TeleplayDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.xsoup.Xsoup;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author f
 * @desc
 *         Parse解析类
 *             存放解析方法
 * @create 2022-02-12 13:39
 */
@Slf4j
public class Parse {

    // 解析首页所需数据
    public static void parseIndex(Page page){
        List<String> pageHtml = page.getHtml().xpath("//div[@class='listbox']//ul").all();

        // List<Teleplay> teleplayList = new ArrayList<>(12*6);
        List<IndexVo> indexVoList = new ArrayList<>(12*6);

        for (int i = 0;i < pageHtml.size();i++){
            Document document = Jsoup.parse(pageHtml.get(i));
            boolean isHot;
            String dn = Xsoup.compile("//ul[@class='dn']").evaluate(document).get();
            if (dn != null && !"".equals(dn)){
                log.info("正在热播");
                isHot = true;
            }else {
                log.info("最近更新");
                isHot = false;
            }

            List<String> liList = Xsoup.compile("//li").evaluate(document).list();
            liList.forEach(item->{
                String name = Xsoup.compile("//a/@title").evaluate(Jsoup.parse(item)).get();
                String href = Xsoup.compile("//a/@href").evaluate(Jsoup.parse(item)).get();
                String coverUrl = Xsoup.compile("//img/@data-echo").evaluate(Jsoup.parse(item)).get();
                String count =  Xsoup.compile("//span[@class='listbox-remarks']/text()").evaluate(Jsoup.parse(item)).get();
                String score =  Xsoup.compile("//span[@class='listbox-score']/text()").evaluate(Jsoup.parse(item)).get();
                String alias =  Xsoup.compile("//p[@class='otherinfo']/text()").evaluate(Jsoup.parse(item)).get();

                String category = href.split("/")[1];
                Integer cat ;
                if (category.equals("hanju")){
                    cat=0;
                }else if (category.equals("dianshiju")){
                    cat=1;
                }else {
                    cat=2;
                }

                IndexVo indexVo = new IndexVo();

                indexVo.setTeleplayName(name);
                indexVo.setIsHot(isHot);
                indexVo.setAlias(alias);
                indexVo.setCover(coverUrl);
                indexVo.setCount(count);
                indexVo.setScore(score);
                indexVo.setCategory(cat);

                indexVoList.add(indexVo);

            });
        }
        page.putField("indexVoList",indexVoList);
    }

    /**
     * 解析全部电视剧目
     * @param page Process组件的page对象，包含Downloader下载器下载的页面等
     */
    public static void parseTeleplay(Page page){
        List<String> pageHtml = page.getHtml().xpath("//ul[@class='listbox']").all();

        Document document = Jsoup.parse(pageHtml.get(0));

        ArrayList<Teleplay> teleplayList = new ArrayList<>();
        Xsoup.compile("//li").evaluate(document).list().forEach(item ->{
            String name = Xsoup.compile("//a/@title").evaluate(Jsoup.parse(item)).get();
            String videoPath = Xsoup.compile("//a/@href").evaluate(Jsoup.parse(item)).get();
            String cover = "https:"+Xsoup.compile("//img/@data-echo").evaluate(Jsoup.parse(item)).get();
            String count =  Xsoup.compile("//span[@class='listbox-remarks']/text()").evaluate(Jsoup.parse(item)).get();
            String score =  Xsoup.compile("//span[@class='listbox-score']/text()").evaluate(Jsoup.parse(item)).get();
            String actor =  Xsoup.compile("//p[@class='otherinfo']/text()").evaluate(Jsoup.parse(item)).get();
            String category = videoPath.split("/")[1];
            String pathName = videoPath.split("/")[2];

            Teleplay teleplay = new Teleplay();
            if (category.equals("hanju")){
                teleplay.setCategory(category);
            }else if(category.equals("dianshiju")){
                teleplay.setCategory(category);
            }else {
                teleplay.setCategory(category);
            }
            teleplay.setName(name);
            teleplay.setCover(cover);
            teleplay.setActor(actor);
            teleplay.setCount(count);
            teleplay.setScore(score);
            teleplay.setVideoPath(videoPath);
            teleplay.setPathName(pathName);

            teleplayList.add(teleplay);
        });

        page.putField("teleplayList",teleplayList);

        List<String> pageBox = page.getHtml().xpath("//div[@class='pagebox']//li").all();

        String nextPage = Xsoup.compile("//a/@href").evaluate(Jsoup.parse(pageBox.get(pageBox.size() - 1))).get();
        if (nextPage != null && !"".equals(nextPage)){
            page.addTargetRequest(new Request("https://www.tvn.cc"+nextPage));
        }

    }

    /**
     * 解析视频
     * @param page Process组件的page对象，包含Downloader下载器下载的页面等
     */
    public static void parseVideo(Page page){
        Video video = new Video();
        String url = page.getUrl().get();
        String category = url.split("/")[3];
        String name =  url.split("/")[4];
        String no = url.substring(url.indexOf("-")+1,url.lastIndexOf("."));
        int cat;
        if ("hanju".equals(category)){
             cat = 0;
        }else if ("dianshiju".equals(category)){
            cat = 1;
        }else {
            cat = 2;
        }
        video.setNo(no);
        video.setCategory(cat);
        video.setTeleplayName(name);


        List<String> scripts = page.getHtml().xpath("//script").all();
        scripts.forEach(script->{
            int cms_player = script.indexOf("cms_player");
            if (cms_player > 0){
                String json = script.substring(cms_player+12,script.indexOf(";"));
                JSONObject jsonObject = JSONUtil.parseObj(json);
                String playUrl = jsonObject.getStr("url");
                video.setPlayUrl(playUrl);
            }
        });
        page.putField("video",video);

        List<String> liList = page.getHtml().xpath("//ul[@class='choosepisodes-list']//li").all();

        if (liList.size()>2){
            for (int i = 1; i < liList.size();i++){
                Document document = Jsoup.parse(liList.get(i));
                String nextUrl = Xsoup.compile("//a/@href").evaluate(document).get();
                String fullUrl = VideoUrlUtil.getUrl(nextUrl);
                page.addTargetRequest(fullUrl);
            }
        }
    }

    /**
     * 解析详情页
     * @param page
     */
    public static void parseTeleplayDetail(Page page){
        String category = page.getUrl().get().split("/")[3];

        String detailHtml = page.getHtml().xpath("//div[@class='albumDetailMedia']").get();
        String name = Xsoup.compile("//div[@class='mediaTitle']/h3/text()").evaluate(Jsoup.parse(detailHtml)).get();
        List<String> mediaDesc =
                Xsoup.compile("//div[@class='mediaDesc']//li").evaluate(Jsoup.parse(detailHtml)).list();
        TeleplayDetailVo detailVo = new TeleplayDetailVo();
        detailVo.setName(name);
        detailVo.setCategory(category);
        for (int i = 0 ; i < mediaDesc.size();i++){
            if (i == 0){
                String alias = Xsoup.compile("//li/text()").evaluate(Jsoup.parse(mediaDesc.get(i))).get().split("：")[1];
                detailVo.setAlias(alias);
            }
            if (i == 1){
                String director = Xsoup.compile("//li/text()").evaluate(Jsoup.parse(mediaDesc.get(i))).get().split("：")[1];
                detailVo.setDirector(director);
            }
            if (i == 4){
                String type = Xsoup.compile("//li/label[2]/text()").evaluate(Jsoup.parse(mediaDesc.get(i))).get().split("：")[1];
                detailVo.setType(type);
            }
            if (i == 5){
                Date time = DateUtil.parse(
                        Xsoup.compile("//li/label[1]/text()")
                                .evaluate(Jsoup.parse(mediaDesc.get(i)))
                                .get()
                                .split("：")[1],"yyyy-MM-dd HH:mm:ss");
                String year = Xsoup.compile("//li/label[2]/text()").evaluate(Jsoup.parse(mediaDesc.get(i))).get().split("：")[1];
                detailVo.setTime(time);
                detailVo.setYear(year);
            }
            if (i == 6){
                String desc = Xsoup.compile("//li//span/text()").evaluate(Jsoup.parse(mediaDesc.get(i))).get();
                detailVo.setDesc(desc);
            }
        }
       page.putField("detailVo",detailVo);
    }

}
