package com.atguigu.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description TODO
 * Vsrsion 1.0
 *
 * @Author czl0502
 * 学号：3187102130
 * Date 2021/7/5 16:26
 */
@Data//生成属性的get、set方法
@AllArgsConstructor//生成有参数构造方法
@NoArgsConstructor//生成无参数构造方法
public class GuliExcepption extends RuntimeException{
    private Integer code;//状态码
    private String msg;//异常信息
    @Override
    public String toString() {
        return "GuliException{" +
                "message=" + this.getMsg() +
                ", code=" + code +
                '}';
    }
}
