package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-13
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
   @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的文件，把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubeject(MultipartFile file){
        //上传过来execl文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }
    //课程分类列表(树形)
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //list集合泛型是一级分类，因为一级分类中有他本身还有二级分类
        List<OneSubject> list=eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}


