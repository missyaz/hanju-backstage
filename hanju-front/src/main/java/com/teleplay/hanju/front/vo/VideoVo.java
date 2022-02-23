package com.teleplay.hanju.front.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.teleplay.hanju.common.entity.Video;
import com.teleplay.hanju.common.vo.Teleplay;
import lombok.Data;

/**
 * @author f
 * @desc
 * @create 2022-02-20 9:30
 */
@Data
public class VideoVo extends Teleplay {

    private String teleplayName;

    private Integer videoCategory;

    private String playUrl;

    private String no;

    private Integer videoCount;
}
