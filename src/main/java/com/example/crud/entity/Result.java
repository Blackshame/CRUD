package com.example.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//统一响应结果
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private String status;//业务状态
    private String message;//提示信息
    private T data;//响应数据

    //快速返回操作成功响应结果(带响应数据)
    public static <E> Result<E> success(String message_0,E data){
        return new Result<>("success",message_0,data);
    }
    //快速返回操作成功响应结果
    public static Result success(String message_0){
        return new Result("success",message_0,null);
    }

    public static Result error(String message_0) {
        return new Result("error",message_0,null);
    }
}
