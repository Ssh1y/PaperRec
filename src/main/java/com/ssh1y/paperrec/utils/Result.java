package com.ssh1y.paperrec.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 全局统一返回结果类
 *
 * @author chenweihong
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class Result<T> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回信息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        return new Result(200, "success");
    }

    public static Result success(Object data) {
        return new Result(200, "success", data);
    }

    public static Result error() {
        return new Result(500, "error");
    }

    public static Result error(String msg) {
        return new Result(500, msg);
    }

    public static Result error(Integer code, String msg) {
        return new Result(code, msg);
    }
}
