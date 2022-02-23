package com.teleplay.hanju.spider.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author f
 * @desc
 * @create 2022-02-22 20:23
 */
@Data
public class TeleplayDetailVo extends Teleplay{

    String director;

    String type;

    Date time;

    String year;

    String desc;
}
