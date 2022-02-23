package com.teleplay.hanju.front.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author f
 * @desc  响应结果结构
 * @create 2022-02-19 9:40
 */
@Data
public class Result {

    private Boolean success;

    private Integer code;

    private String message;

    private Map<String,Object> data = new HashMap<String, Object>();

    //构造方法私有化
    private Result(){}

    //留两个静态方法给调用者使用
    public static Result ok(){
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }
    public static Result error(){
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    /*
    以下方法是为了方便链式调用，因为静态方法会返回一个R对象，这些方法也返回一个对象
    所以可以采用这样的调用方式：
     R.ok().success().message()....
     */
    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
