package com.teleplay.hanju.front.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author f
 * @desc 自定义异常
 * @create 2022-02-19 9:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomException {

    private Integer code;

    private String msg;
}
