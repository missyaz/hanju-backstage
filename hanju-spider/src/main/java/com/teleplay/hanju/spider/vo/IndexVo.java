package com.teleplay.hanju.spider.vo;

import com.teleplay.hanju.common.entity.Index;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 * @author f
 * @desc
 * @create 2022-02-17 12:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IndexVo extends Index {

    private String alias;

    private String teleplayName;

    private String name;

    private String actor;

    private String cover;

    private Boolean isIndex;

    private String count;

    private String score;
}
