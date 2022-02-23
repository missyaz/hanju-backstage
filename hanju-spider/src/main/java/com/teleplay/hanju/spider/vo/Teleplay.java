package com.teleplay.hanju.spider.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author f
 * @desc
 * @create 2022-02-15 12:58
 */
@Data
public class Teleplay {
    private String name;

    private String category;

    private String alias;

    private String actor;

    private String cover;

    private String videoPath;

    private String count;

    private String score;

    private String pathName;
}
