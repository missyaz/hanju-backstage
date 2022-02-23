package com.teleplay.hanju.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 电视剧表，包含电视剧所有信息
 * </p>
 *
 * @author 
 * @since 2022-02-15
 */
@Data
@TableName("t_detail")
public class Detail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    @TableField("teleplay_name")
    private String teleplayName;

    @TableField("director")
    private String director;

    @TableField("type")
    private String type;

    @TableField("year")
    private String year;

    @TableField("description")
    private String description;

    @TableField("teleplay_update_time")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date teleplayUpdateTime;

}
