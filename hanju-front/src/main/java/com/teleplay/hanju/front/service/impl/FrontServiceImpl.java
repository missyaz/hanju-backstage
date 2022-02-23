package com.teleplay.hanju.front.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teleplay.hanju.common.entity.*;
import com.teleplay.hanju.common.mapper.*;
import com.teleplay.hanju.front.service.FrontService;
import com.teleplay.hanju.front.vo.DetailVo;
import com.teleplay.hanju.front.vo.FrontIndexVO;
import com.teleplay.hanju.front.vo.VideoVo;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author f
 * @desc
 * @create 2022-02-19 10:06
 */
@Service
public class FrontServiceImpl implements FrontService {

    @Resource
    private IndexMapper indexMapper;

    @Resource
    private HanjuMapper hanjuMapper;

    @Resource
    private RijuMapper rijuMapper;

    @Resource
    private TaijuMapper taijuMapper;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private DetailMapper detailMapper;

    public List<FrontIndexVO> findIndex() {
        // 查询首页
        QueryWrapper<Index> indexQueryWrapper = new QueryWrapper<>();
        indexQueryWrapper.select("id,teleplay_id,category,is_hot");
        List<Index> indexList = indexMapper.selectList(indexQueryWrapper);
        ArrayList<FrontIndexVO> indexVOS = new ArrayList<>(indexList.size());

        indexList.forEach(index->{
            FrontIndexVO indexVO = new FrontIndexVO();
            BeanUtil.copyProperties(index,indexVO);
            if (index.getCategory() == 0){
                QueryWrapper<Hanju> queryWrapper = new QueryWrapper<>();
                queryWrapper.select("id, name, alias, actor, cover, path_name, video_path, count, score");
                queryWrapper.eq("id",index.getTeleplayId());
                Hanju hanju = hanjuMapper.selectOne(queryWrapper);
                BeanUtil.copyProperties(hanju,indexVO,new CopyOptions(null,false,"id"));
                indexVOS.add(indexVO);
            }else if (index.getCategory() == 1){
                QueryWrapper<Riju> queryWrapper = new QueryWrapper<>();
                queryWrapper.select("id, name, alias, actor, cover, path_name, video_path, count, score");
                queryWrapper.eq("id",index.getTeleplayId());
                Riju riju = rijuMapper.selectOne(queryWrapper);
                BeanUtil.copyProperties(riju,indexVO,new CopyOptions(null,false,"id"));
                indexVOS.add(indexVO);
            }else {
                QueryWrapper<Taiju> queryWrapper = new QueryWrapper<>();
                queryWrapper.select("id, name, alias, actor, cover, path_name, video_path, count, score");
                queryWrapper.eq("id",index.getTeleplayId());
                Taiju taiju = taijuMapper.selectOne(queryWrapper);
                BeanUtil.copyProperties(taiju,indexVO,new CopyOptions(null,false,"id"));
                indexVOS.add(indexVO);
            }
        });

        return indexVOS;
    }

    @Override
    public VideoVo findVideoByTeleplayName(String teleplayName, int no) {
        // 获得playUrl
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.select("DISTINCT teleplay_name,category,play_url,no");
        wrapper.eq("teleplay_name",teleplayName);
        wrapper.eq("no",no);
        Video video = videoMapper.selectOne(wrapper);
        VideoVo videoVo = new VideoVo();
        videoVo.setVideoCategory(video.getCategory());
        BeanUtil.copyProperties(video,videoVo);

        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("teleplay_name",teleplayName);
        Integer videoCount = videoMapper.selectCount(videoQueryWrapper).intValue();
        videoVo.setVideoCount(videoCount);

        // 获得电视信息
        QueryWrapper queryWrapper = new QueryWrapper<>();
        if (videoVo.getVideoCategory() == 0){
            queryWrapper.select("name,count,video_path");
            queryWrapper.eq("path_name",videoVo.getTeleplayName());
            Hanju hanju = hanjuMapper.selectOne(queryWrapper);
            videoVo.setCount(hanju.getCount());
            videoVo.setName(hanju.getName());
            videoVo.setVideoPath(hanju.getVideoPath());
        }else if (videoVo.getVideoCategory() == 1){
            queryWrapper.select("name,count,video_path");
            queryWrapper.eq("path_name",videoVo.getTeleplayName());
            Riju riju = rijuMapper.selectOne(queryWrapper);
            videoVo.setCount(riju.getCount());
            videoVo.setName(riju.getName());
            videoVo.setVideoPath(riju.getVideoPath());
        }else {
            queryWrapper.select("name,count,video_path");
            queryWrapper.eq("path_name",videoVo.getTeleplayName());
            Taiju taiju = taijuMapper.selectOne(queryWrapper);
            videoVo.setCount(taiju.getCount());
            videoVo.setName(taiju.getName());
            videoVo.setVideoPath(taiju.getVideoPath());
        }

        return videoVo;
    }

    @Override
    public List<VideoVo> findFavorite(String category,Integer limit) {
        ArrayList<VideoVo> videoVos = new ArrayList<>();
        if (category.equals("hanju")){
            QueryWrapper<Hanju> wrapper = new QueryWrapper<>();
            wrapper.select("id,name,alias,cover,actor,count,score,video_path");
            wrapper.orderByDesc("score");
            wrapper.last("limit " + limit);
            List<Hanju> hanjus = hanjuMapper.selectList(wrapper);
            hanjus.forEach(hanju -> {
                VideoVo videoVo = new VideoVo();
                BeanUtil.copyProperties(hanju,videoVo);
                videoVos.add(videoVo);
            });
        }else if (category.equals("riju")){
            QueryWrapper<Riju> wrapper = new QueryWrapper<>();
            wrapper.select("id,name,alias,cover,actor,count,score,video_path");
            wrapper.orderByDesc("score");
            wrapper.last("limit " + limit);
            List<Riju> rijus = rijuMapper.selectList(wrapper);
            rijus.forEach(riju -> {
                VideoVo videoVo = new VideoVo();
                BeanUtil.copyProperties(riju,videoVo);
                videoVos.add(videoVo);
            });
        }else {
            QueryWrapper<Taiju> wrapper = new QueryWrapper<>();
            wrapper.select("id,name,alias,cover,actor,count,score,video_path");
            wrapper.orderByDesc("score");
            wrapper.last("limit " + limit);
            List<Taiju> taijus = taijuMapper.selectList(wrapper);
            taijus.forEach(taiju -> {
                VideoVo videoVo = new VideoVo();
                BeanUtil.copyProperties(taiju,videoVo);
                videoVos.add(videoVo);
            });
        }
        return videoVos;
    }

    @Override
    public List<VideoVo> findToday(String category, Integer limit) {
        ArrayList<VideoVo> videoVos = new ArrayList<>();
        QueryWrapper<Index> wrapper = new QueryWrapper<>();
        if (category.equals("hanju")){
            wrapper.eq("category",0);
            wrapper.eq("is_hot",1);
            wrapper.last("limit " + limit);
            indexMapper.selectList(wrapper).forEach(index -> {
                VideoVo videoVo = new VideoVo();
                Hanju hanju = hanjuMapper.selectById(index.getTeleplayId());
                videoVo.setName(hanju.getName());
                videoVo.setCount(hanju.getCount());
                videoVo.setVideoPath(hanju.getVideoPath());
                videoVos.add(videoVo);
            });
        }else if (category.equals("riju")){
            wrapper.eq("category",1);
            wrapper.eq("is_hot",1);
            wrapper.last("limit " + limit);
            indexMapper.selectList(wrapper).forEach(index -> {
                VideoVo videoVo = new VideoVo();
                Riju riju = rijuMapper.selectById(index.getTeleplayId());
                videoVo.setName(riju.getName());
                videoVo.setCount(riju.getCount());
                videoVo.setVideoPath(riju.getVideoPath());
                videoVos.add(videoVo);
            });
        }else {
            wrapper.eq("category",2);
            wrapper.eq("is_hot",1);
            wrapper.last("limit " + limit);
            indexMapper.selectList(wrapper).forEach(index -> {
                VideoVo videoVo = new VideoVo();
                Taiju taiju = taijuMapper.selectById(index.getTeleplayId());
                videoVo.setName(taiju.getName());
                videoVo.setCount(taiju.getCount());
                videoVo.setVideoPath(taiju.getVideoPath());
                videoVos.add(videoVo);
            });
        }
        return videoVos;
    }

    @Override
    public DetailVo findDetail(String name, String category) {
        DetailVo detailVo = new DetailVo();
        if (category.equals("hanju")){
            Hanju hanju = hanjuMapper.selectByName(name);
            String cat = "韩剧";
            String area = "韩国";
            String language = "韩语";
            BeanUtil.copyProperties(hanju,detailVo);
            detailVo.setCat(cat);
            detailVo.setArea(area);
            detailVo.setLanguage(language);
        }else if (category.equals("riju")){
            Riju riju = rijuMapper.selectByName(name);
            String cat = "日剧";
            String area = "日本";
            String language = "日语";
            BeanUtil.copyProperties(riju,detailVo);
            detailVo.setCat(cat);
            detailVo.setArea(area);
            detailVo.setLanguage(language);
        }else {
            Taiju taiju = taijuMapper.selectByName(name);
            String cat = "泰剧";
            String area = "泰国";
            String language = "泰语";
            BeanUtil.copyProperties(taiju,detailVo);
            detailVo.setCat(cat);
            detailVo.setArea(area);
            detailVo.setLanguage(language);
        }
        QueryWrapper<Detail> wrapper = new QueryWrapper<>();
        wrapper.eq("teleplay_name",detailVo.getName());
        Detail detail = detailMapper.selectOne(wrapper);
        detailVo.setDirector(detail.getDirector());
        detailVo.setDesc(detail.getDescription());
        detailVo.setTime(DateUtil.formatDateTime(detail.getTeleplayUpdateTime()));
        detailVo.setType(detail.getType());
        detailVo.setYear(detail.getYear());
        String actors = detailVo.getActor().replace(",", " ");
        detailVo.setActors(actors);
        return detailVo;
    }
}
