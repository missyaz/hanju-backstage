package com.teleplay.hanju.common.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("t_index")
public class Index implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("teleplay_id")
    private String teleplayId;

    @TableField("category")
    private Integer category;

    /**
     * 0表示最近更新，1表示大家爱看
     */
    @TableField("is_hot")
    private Boolean isHot;

    @TableField(value = "crate_time",fill = FieldFill.INSERT)
    private LocalDateTime crateTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
