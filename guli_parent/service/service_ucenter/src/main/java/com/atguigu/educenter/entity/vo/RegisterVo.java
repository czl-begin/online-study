package com.atguigu.educenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description TODO
 * Vsrsion 1.0
 *
 * @Author czl0502
 * 学号：3187102130
 * Date 2021/8/7 10:59
 */
@Data
@ApiModel(value="注册对象", description="注册对象")
public class RegisterVo {


        @ApiModelProperty(value = "昵称")
        private String nickname;

        @ApiModelProperty(value = "手机号")
        private String mobile;

        @ApiModelProperty(value = "密码")
        private String password;

     /*   @ApiModelProperty(value = "验证码")
        private String code;*/
}
