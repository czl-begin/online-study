package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Description TODO
 * Vsrsion 1.0
 *
 * @Author czl0502
 * 学号：3187102130
 * Date 2021/7/21 15:34
 */
public interface VodService {
    String uploadVideoAly(MultipartFile file);
    //删除多个阿里云视频的方法
    void removeMoreAlyVideo(List videoIdList);
}
