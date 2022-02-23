package com.teleplay.hanju.front.vo;

import com.teleplay.hanju.common.vo.Teleplay;
import lombok.Data;

import java.util.Date;

/**
 * @author f
 * @desc
 * @create 2022-02-23 9:58
 */
@Data
public class DetailVo extends Teleplay {
    String director;

    String type;

    String area;

    String language;

    String cat;

    String time;

    String year;

    String desc;

    String actors;
}
