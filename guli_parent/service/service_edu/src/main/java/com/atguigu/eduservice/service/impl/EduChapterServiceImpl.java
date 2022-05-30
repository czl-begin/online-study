package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliExcepption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-14
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;


    //课程大纲列表，根据课程id进行查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1根据课程id查询课程里面所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = this.list(wrapperChapter);
        //2根据课程id查询课程里面所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        //创建list集合，用来最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();
        //3遍历查询章节list集合进行封装
        //遍历查询章节的list集合
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //eduChapter对象值复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            //把chapterVo放到最终的list集合
            //结果返回的是finalist集合，后面，会对chapterVo进行添加信息
            //为什么不需要把finalList.add(chapterVo)写到最后也能运行？
            //(存在疑惑，待解决)
            finalList.add(chapterVo);


            //创建集合，用于封装章节的小节
            List<VideoVo> videoVoList = new ArrayList<>();
            //4遍历查询小节list集合进行封装
            for (int j = 0; j < eduVideoList.size(); j++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(j);
                //判断:小节里面chapter_id和章节里的id是否一样
                if (eduVideo.getChapterId().equals(chapterVo.getId())) {
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    //放到小节封装集合
                    videoVoList.add(videoVo);
                }
            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoVoList);
        }
        return finalList;


    }
    //删除章节(目录下有小节时先删除小节的方法)
    @Override
    public boolean deleteChapter(String chapterId) {
     //根据chapterId这个章节id查询小节表是否有该章节的小节
     //如果有，就不进行删除
     QueryWrapper wrapper=new QueryWrapper();
     wrapper.eq("chapter_id",chapterId);
     int count=eduVideoService.count(wrapper);
     //判断
        if (count>0){//查询出了小节，不进行删除
            throw new GuliExcepption(20001,"该章节存在小节，无法删除");
        }else{//查询出没有小节。进行删除
            boolean flag = this.removeById(chapterId);
            return flag;
        }

    }
    //2 根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper=new QueryWrapper();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
