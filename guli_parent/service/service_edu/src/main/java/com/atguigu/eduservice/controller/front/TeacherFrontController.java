package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Description TODO
 * Vsrsion 1.0
 *
 * @Author czl0502
 * 学号：3187102130
 * Date 2021/8/14 12:30
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    //1 分页查询讲师的方法
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> pageTeacher=new Page<>(page,limit);
        Map<String,Object> map=eduTeacherService.getTeacherFrontList(pageTeacher);
        return R.ok().data(map);
    }

    //2 讲师详情的功能
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        //1 根据讲师id查询讲师基本信息
        EduTeacher eduTeacher= eduTeacherService.getById(teacherId);
        //2 根据讲师id查询所讲课程
        QueryWrapper wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList= eduCourseService.list(wrapper);
        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }
}
