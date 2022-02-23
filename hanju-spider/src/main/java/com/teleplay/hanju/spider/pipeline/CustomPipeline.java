package com.teleplay.hanju.spider.pipeline;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teleplay.hanju.common.entity.*;
import com.teleplay.hanju.common.mapper.*;
import com.teleplay.hanju.spider.vo.IndexVo;
import com.teleplay.hanju.spider.vo.Teleplay;
import com.teleplay.hanju.spider.vo.TeleplayDetailVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author f
 * @desc 存入mysql数据库中
 * @create 2022-02-14 10:32
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomPipeline implements Pipeline {

    private final IndexMapper indexMapper;

    private final HanjuMapper hanjuMapper;

    private final RijuMapper rijuMapper;

    private final TaijuMapper taijuMapper;

    private final VideoMapper videoMapper;

    private final DetailMapper detailMapper;

    // private final TeleplayMapper teleplayMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<Teleplay> teleplayList = resultItems.get("teleplayList");
        List<IndexVo> indexVoList = resultItems.get("indexVoList");
        Video video = resultItems.get("video");
        TeleplayDetailVo detailVo = resultItems.get("detailVo");


        if (teleplayList !=null && teleplayList.size() > 0){
            teleplayList.forEach(item -> {
                if (item.getCategory().equals("hanju")){
                    QueryWrapper<Hanju> wrapper = new QueryWrapper<>();
                    wrapper.select("id","name","video_path");
                    wrapper.eq("name",item.getName());
                    Hanju hanju = hanjuMapper.selectOne(wrapper);
                    if (hanju != null){
                        BeanUtil.copyProperties(item,hanju);
                        hanjuMapper.updateById(hanju);
                    }else {
                        hanju = new Hanju();
                        hanju.setId(IdUtil.simpleUUID());
                        BeanUtil.copyProperties(item,hanju);
                        hanjuMapper.insert(hanju);
                    }
                }else if(item.getCategory().equals("dianshiju")){
                    QueryWrapper<Riju> wrapper = new QueryWrapper<>();
                    wrapper.select("id","name","video_path");
                    wrapper.eq("name",item.getName());
                    Riju riju = rijuMapper.selectOne(wrapper);
                    if (riju != null){
                        BeanUtil.copyProperties(item,riju);
                        rijuMapper.updateById(riju);
                    }else {
                        riju = new Riju();
                        riju.setId(IdUtil.simpleUUID());
                        BeanUtil.copyProperties(item,riju);
                        rijuMapper.insert(riju);
                    }
                }else {
                    QueryWrapper<Taiju> wrapper = new QueryWrapper<>();
                    wrapper.select("id","name","video_path");
                    wrapper.eq("name",item.getName());
                    Taiju taiju = taijuMapper.selectOne(wrapper);
                    if (taiju != null){
                        BeanUtil.copyProperties(item,taiju);
                        taijuMapper.updateById(taiju);
                    }else {
                        taiju = new Taiju();
                        taiju.setId(IdUtil.simpleUUID());
                        BeanUtil.copyProperties(item,taiju);
                        taijuMapper.insert(taiju);
                    }
                }
            });
        }

        if (indexVoList != null && indexVoList.size() > 0){
            indexVoList.forEach(item->{
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.select("id","name");
                String teleplayId;
                if (item.getCategory() == 0){
                    wrapper.eq("name",item.getTeleplayName());
                    Hanju hanju = hanjuMapper.selectOne(wrapper);
                    teleplayId = hanju.getId();
                    if (StrUtil.isEmpty(hanju.getId())){
                        // 片源中无影片
                        hanju = new Hanju();
                        hanju.setId(IdUtil.simpleUUID());
                        teleplayId = hanju.getId();
                        BeanUtil.copyProperties(item,hanju);
                        hanjuMapper.insert(hanju);
                    }
                    //有影片
                    hanju.setAlias(item.getAlias());
                    hanjuMapper.updateById(hanju);
                }else if (item.getCategory() == 1){
                    wrapper.eq("name",item.getTeleplayName());
                    Riju riju = rijuMapper.selectOne(wrapper);
                    teleplayId = riju.getId();
                    if (StrUtil.isEmpty(riju.getId())){
                        // 片源中无影片
                        riju = new Riju();
                        riju.setId(IdUtil.simpleUUID());
                        teleplayId = riju.getId();
                        BeanUtil.copyProperties(item,riju);
                        rijuMapper.insert(riju);
                    }
                    //有影片
                    riju.setAlias(item.getAlias());
                    rijuMapper.updateById(riju);
                }else {
                    wrapper.eq("name",item.getTeleplayName());
                    Taiju taiju = taijuMapper.selectOne(wrapper);
                    teleplayId = taiju.getId();
                    if (StrUtil.isEmpty(taiju.getId())){
                        // 片源中无影片
                        taiju = new Taiju();
                        taiju.setId(IdUtil.simpleUUID());
                        teleplayId = taiju.getId();
                        BeanUtil.copyProperties(item,taiju);
                        taijuMapper.insert(taiju);
                    }
                    //有影片
                    taiju.setAlias(item.getAlias());
                    taijuMapper.updateById(taiju);
                }

                //第一次爬取，直接插入mysql
                Index index = new Index();
                index.setTeleplayId(teleplayId);
                index.setIsHot(item.getIsHot());
                index.setCategory(item.getCategory());
                indexMapper.insert(index);
            });
        }

        if (video != null){
            int insert = videoMapper.insert(video);
            if (insert>0){
                log.info("***********插入视频成功**********");
            }else {
                log.info("***********插入视频失败**********");
            }
        }

        if (detailVo != null){
            // 给电视剧表插入别名
            if (detailVo.getCategory().equals("hanju")){
                hanjuMapper.updateByName(detailVo.getName(),detailVo.getAlias());
            }else if (detailVo.getCategory().equals("dianshiju")){
                rijuMapper.updateByName(detailVo.getName(),detailVo.getAlias());
            }else {
                taijuMapper.updateByName(detailVo.getName(),detailVo.getAlias());
            }

            Detail detail = new Detail();
            detail.setTeleplayName(detailVo.getName());
            detail.setDirector(detailVo.getDirector());
            detail.setType(detailVo.getType());
            detail.setYear(detailVo.getYear());
            detail.setDescription(detailVo.getDesc());
            detail.setTeleplayUpdateTime(detailVo.getTime());
            detailMapper.insert(detail);
        }
    }

}
