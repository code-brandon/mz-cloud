package com.mz.common.core.entity;

import com.mz.common.core.constants.Constant;
import com.mz.common.core.exception.MzCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel("api通用返回数据")
@Data
@AllArgsConstructor
public class R<T> {

    /**
     * 标识代码，0表示成功，非0表示出错
     */
    @ApiModelProperty("标识代码,0表示成功，非0表示出错")
    private Integer code;

    /**
     * 提示信息，通常供报错时使用
     */
    @ApiModelProperty("提示信息,供报错时使用")
    private String message;

    /**
     * 正常返回时返回的数据
     */
    @ApiModelProperty("返回的数据")
    private T data;

    public R() {
    }

    public R(int code, String message, T data) {
        setCode(code);
        setMessage(message);
        data(data);
    }

    public static R error() {
        R r = new R();
        r.setData(false);
        r.setCode(Constant.ERROR);
        r.setMessage("未知异常，请联系管理员");
        return r;
    }

    public static R error(String message) {
        R r = error();
        r.setMessage(message);
        return r;
    }

    public static R error(int code, String message) {
        R r = error();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static R error(MzCodeEnum mzCodeEnum) {
        R r = error();
        r.setCode(mzCodeEnum.getCode());
        r.setMessage(mzCodeEnum.getMsg());
        return r;
    }

    public static R ok(String message) {
        R r = ok();
        r.setMessage(message);
        return r;
    }

    public static R ok(int code, String message) {
        R r = ok();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static R ok(MzCodeEnum mzCodeEnum) {
        R r = ok();
        r.setCode(mzCodeEnum.getCode());
        r.setMessage(mzCodeEnum.getMsg());
        return r;
    }

    public static R ok() {
        R r = new R();
        r.setData(true);
        r.setCode(Constant.SUCCESS);
        r.setMessage("操作成功！");
        return r;
    }

    public R data(T data) {
        this.setData(data);
        return this;
    }

    public static R fail() {
        R r = new R();
        r.setCode(Constant.FAIL);
        r.setMessage("操作失败");
        r.setData(false);
        return r;
    }

    public static R fail(String message) {
        R r = fail();
        r.setMessage(message);
        return r;
    }

    public static R fail(int code, String message) {
        R r = fail();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static R fail(MzCodeEnum mzCodeEnum) {
        R r = fail();
        r.setCode(mzCodeEnum.getCode());
        r.setMessage(mzCodeEnum.getMsg());
        return r;
    }
}