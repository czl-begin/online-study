package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Description TODO
 * Vsrsion 1.0
 *
 * @Author czl0502
 * 学号：3187102130
 * Date 2021/7/15 12:44
 */
@Data
public class ChapterVo {
    private String id;
    private String title;
    //课程小结
    private List<VideoVo> children=new ArrayList<>();
}
