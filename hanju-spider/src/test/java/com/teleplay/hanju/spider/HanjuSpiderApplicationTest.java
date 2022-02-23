package com.teleplay.hanju.spider;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.resource.ClassPathResource;
import com.teleplay.hanju.common.entity.Hanju;
import com.teleplay.hanju.common.mapper.HanjuMapper;
import com.teleplay.hanju.common.mapper.IndexMapper;
import com.teleplay.hanju.common.mapper.RijuMapper;
import com.teleplay.hanju.common.mapper.TaijuMapper;
import com.teleplay.hanju.spider.pipeline.CustomPipeline;
import com.teleplay.hanju.spider.processor.TvnIndexProcess;
import com.teleplay.hanju.spider.webmagic.HttpsClientDownloader;
import com.teleplay.hanju.spider.webmagic.RedisScheduler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author f
 * @desc
 * @create 2022-02-12 13:46
 */
public class HanjuSpiderApplicationTest {

    @Test
    public void testFileRead() throws IOException {
        // String fileName = "IpProxy.txt";
        ClassPathResource classPathResource = new ClassPathResource("IpProxy.txt");
        InputStream stream = classPathResource.getStream();
        // byte[] bytes = new byte[5];
        // int read = stream.read();
        // while (read > -1){
        //     System.out.println((char)read);
        //     read=stream.read();
        // }
        // System.out.println(IoUtil.read(stream, Charset.defaultCharset()));
        FileReader fileReader = new FileReader("IpProxy.txt");
        fileReader.readLines().forEach((item) ->{
            String[] split= item.split(":");
            System.out.println(split[0]+"************"+split[1]);
            System.out.println(item instanceof String);
            new Proxy(split[0],Integer.valueOf(split[1]).intValue());
        });
        System.out.println(fileReader.readLines().size());
        // System.out.println(stream.read(bytes));
    }

    @Autowired
    private IndexMapper indexMapper;
    @Autowired
    private HanjuMapper hanjuMapper;
    @Autowired
    private RijuMapper rijuMapper;
    @Autowired
    private TaijuMapper taijuMapper;

    @Test
    public void testParse(){
        FileReader ipProxyReader = new FileReader("IpProxy.txt");
        HttpsClientDownloader httpsClientDownloader = new HttpsClientDownloader();
        ipProxyReader.readLines().forEach((item)-> {
            String host = item.split(":")[0];
            int port = Integer.valueOf(item.split(":")[1]).intValue();
            httpsClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy(host,port)));
        });
        ArrayList<Request> requests = new ArrayList<>();
        Request hanjuRequest = new Request("https://www.tvn.cc/hanju/");
        Request rijuRequest = new Request("https://www.tvn.cc/dianshiju/");
        Request taijuRequest = new Request("https://www.tvn.cc/taiju/");

        requests.add(hanjuRequest);
        requests.add(rijuRequest);
        requests.add(taijuRequest);
        Spider.create(new TvnIndexProcess())
                // .setUUID(processor.getUuid())
                // .addPipeline(new CustomPipeline(indexMapper,hanjuMapper,rijuMapper,taijuMapper,))
                .setDownloader(new HttpsClientDownloader())
                .addUrl("https://www.tvn.cc/")
                .thread(5)
                // .startRequest(requests)
                // .setScheduler(new RedisScheduler(new JedisPool()))
                .run();
    }

    @Test
    public void testFather(){
        Hanju hanju = new Hanju();
    }

    @Test
    public void test_Stream(){
        Arrays.asList("主演:郑多爱","导演:瓜皮").stream().map(li->li.split(":")[1]).forEach(System.out::println);
    }
}
