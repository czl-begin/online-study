package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Description TODO
 * Vsrsion 1.0
 *
 * @Author czl0502
 * 学号：3187102130
 * Date 2021/7/12 11:43
 */
public interface OssService {
    //上传头像到oss
    String uploadFileAvatar(MultipartFile file);

}
