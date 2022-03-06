package com.njtech.common;

import lombok.Data;

@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public Result(T data) {
        this.data = data;
    }

    public Result() {
    }

    public static Result success() {
        Result tResult = new Result<>();
        tResult.setCode(ResultCode.SUCCESS.code);
        tResult.setMsg(ResultCode.SUCCESS.msg);
        return tResult;
    }

    public static <T> Result<T> success(T data) {
        Result<T> tResult = new Result<>(data);
        tResult.setCode(ResultCode.SUCCESS.code);
        tResult.setMsg(ResultCode.SUCCESS.msg);
        return tResult;
    }

    public static Result error() {
        Result tResult = new Result<>();
        tResult.setCode(ResultCode.ERROR.code);
        tResult.setMsg(ResultCode.ERROR.msg);
        return tResult;
    }

    public static Result error(String code, String msg) {
        Result tResult = new Result<>();
        tResult.setCode(code);
        tResult.setMsg(msg);
        return tResult;
    }

}
