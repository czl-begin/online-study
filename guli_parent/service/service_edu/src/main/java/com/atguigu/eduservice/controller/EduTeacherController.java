package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliExcepption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-03
 */
//访问地址：http://localhost:8001+@RequestMapping+子地址 如@GetMapping
@Api(tags="讲师管理")
//@RestController的主要作用有两个
// 1.@Controller 交给spring进行管理
// 2.@RequestMapping 返回一个json对象
@RestController
//访问路径
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    //把所需service注入
    @Autowired
    private EduTeacherService eduTeacherService;
    @ApiOperation(value = "所有讲师列表")
    //rest风格（添加post 修改put 删除delete 查询get）
    //1 查询讲师表所有数据
    @GetMapping("findAll")
    public R findAllTeacher(){
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    //2 逻辑删除讲师的方法
    @ApiOperation(value = "根据ID逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
            boolean flag = eduTeacherService.removeById(id);
            if (flag){
                return R.ok();
            }else{
                return R.error();
        }
        }

    //3 分页查询讲师表所有数据
    @ApiOperation(value = "分页查询讲师表所有数据")
    //current 当前页
    //limit   每页记录数
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
      /*  异常处理
      try{
            int i=10/0;
        } catch (Exception e){
            throw new GuliExcepption(20001,"执行了自定义异常处理");
        }*/
        //调用此方法实现分页
        //调用方法的时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        eduTeacherService.page(pageTeacher,null);
        //总记录数
        long total = pageTeacher.getTotal();
        //数据list集合
        List<EduTeacher> records = pageTeacher.getRecords();
      /*  //1.map
        Map map=new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);*/
       //2.直接返回
       return R.ok().data("total",total).data("rows",records);
    }

    //4 条件查询带分页
    //使用@RequestBody接收前端数据，必须使用@PostMapping请求方式，如果所传数据有的参数可以为空那么还需要写上(required=false)
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        //构建条件
        QueryWrapper wrapper=new QueryWrapper();

        //多条件组合查询
        //mybatis学过 动态sql
        String name=teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如何不为空拼接条件
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
            wrapper.orderByDesc("id");

        //调用方法实现条件查询分页
        eduTeacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        Map map=new HashMap();
        map.put("total",total);
        map.put("records",records);
        return R.ok().data(map);
    }
    //5 添加讲师接口的方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody(required = false) EduTeacher eduTeacher){
        boolean save=eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }
        else{
            return R.error();
        }
    }
    //6 根据id查询讲师信息
    //如果通过地址传递参数，那么@PathVariable的属性名称要和地址参数名称一致如：
    // "getTeacher/{id}"对应@PathVariable String id，而不能对应@PathVariable String ID
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }
    //7 讲师修改功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody(required = false) EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }
        else{
            return R.error();
        }

    }
}

