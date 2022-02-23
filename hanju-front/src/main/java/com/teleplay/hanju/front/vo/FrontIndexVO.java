package com.teleplay.hanju.front.vo;

import com.teleplay.hanju.common.entity.Index;
import lombok.Data;

/**
 * @author f
 * @desc
 * @create 2022-02-19 9:54
 */
@Data
public class FrontIndexVO extends Index{

    private String alias;

    private String teleplayName;

    private String name;

    private String actor;

    private String cover;

    private Boolean isIndex;

    private String count;

    private String score;
}
