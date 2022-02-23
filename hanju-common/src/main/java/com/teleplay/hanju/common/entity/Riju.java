package com.teleplay.hanju.common.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 电视剧表，包含电视剧所有信息
 * </p>
 *
 * @author 
 * @since 2022-02-15
 */
@Data
@TableName("t_riju")
public class Riju implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("name")
    private String name;

    @TableField("alias")
    private String alias;

    @TableField("cover")
    private String cover;

    @TableField("actor")
    private String actor;

    @TableField("path_name")
    private String pathName;

    @TableField("video_path")
    private String videoPath;

    @TableField("count")
    private String count;

    @TableField("score")
    private String score;

    @TableField(value = "crate_time",fill = FieldFill.INSERT)
    private LocalDateTime crateTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
