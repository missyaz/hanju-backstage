package com.teleplay.hanju.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2022-02-15
 */
@Data
@TableName("t_video")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("teleplay_name")
    private String teleplayName;

    @TableField("category")
    private Integer category;

    @TableField("play_url")
    private String playUrl;

    @TableField("no")
    private String no;


}
