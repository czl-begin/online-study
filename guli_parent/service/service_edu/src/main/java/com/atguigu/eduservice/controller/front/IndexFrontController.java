package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description TODO
 * Vsrsion 1.0
 *
 * @Author czl0502
 * 学号：3187102130
 * Date 2021/7/28 11:45
 */
@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService eduTeacherService;

    //查询前8条热门课程，查询前4条名师
    @GetMapping("index")
    public R index(){
        //查询前8条热门课程
        QueryWrapper<EduCourse> eduCourseQueryWrapper=new QueryWrapper<>();
            eduCourseQueryWrapper.orderByDesc("id");
            eduCourseQueryWrapper.last("limit 8");
        List<EduCourse> eduCourseList = eduCourseService.list(eduCourseQueryWrapper);

        //查询前4条名师
        QueryWrapper<EduTeacher> eduTeacherQueryWrapper=new QueryWrapper<>();
        eduTeacherQueryWrapper.orderByDesc("id");
        eduTeacherQueryWrapper.last("limit 4");
        List<EduTeacher> eduTeacherList = eduTeacherService.list(eduTeacherQueryWrapper);

        return R.ok().data("eduCourseList",eduCourseList).data("eduTeacherList",eduTeacherList);
    }
}
