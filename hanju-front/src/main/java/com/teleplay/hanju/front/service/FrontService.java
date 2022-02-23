package com.teleplay.hanju.front.service;

import com.teleplay.hanju.front.bean.Result;
import com.teleplay.hanju.front.vo.DetailVo;
import com.teleplay.hanju.front.vo.FrontIndexVO;
import com.teleplay.hanju.front.vo.VideoVo;

import java.util.List;

/**
 * @author f
 * @desc
 * @create 2022-02-19 9:46
 */
public interface FrontService {

    /**
     * 获得首页数据
     * @return 首页数据
     */
    List<FrontIndexVO> findIndex();

    /**
     * 获得视频源
     * @return 视频对象
     */
    VideoVo findVideoByTeleplayName(String teleplayName,int no);

    /**
     * 获得猜你喜欢的视频
     * @param category
     * @return
     */
    List<VideoVo> findFavorite(String category,Integer limit);

    /**
     * 获得今日值得看
     * @param category
     * @param limit
     * @return
     */
    List<VideoVo> findToday(String category, Integer limit);

    /**
     * 获得电视剧详情信息
     * @param name 电视剧名
     * @param category 电视剧分类
     * @return DetailVo 封装前端所需数据的对象
     */
    DetailVo findDetail(String name, String category);
}
