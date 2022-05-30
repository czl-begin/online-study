package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliExcepption;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * Description TODO
 * Vsrsion 1.0
 *
 * @Author czl0502
 * 学号：3187102130
 * Date 2021/7/13 13:12
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListener不能交给spring进行管理，需要直接new，不能注入其他对象
    public EduSubjectService edusubjectService;
    public SubjectExcelListener() {
    }
    public SubjectExcelListener(EduSubjectService edusubjectService) {
        this.edusubjectService = edusubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData ==null){
            throw new GuliExcepption(20001,"文件数据为空");
        }
        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        EduSubject existOneSubject=this.existOneSubject(edusubjectService,subjectData.getOneSubjectName());
        //在数据库中没有找到相同名字的一级分类，那就添加新的一级分类
        if (existOneSubject==null){
              existOneSubject=new EduSubject();
              existOneSubject.setTitle(subjectData.getOneSubjectName());
              existOneSubject.setParentId("0");
              edusubjectService.save(existOneSubject);
        }

        //获取一级分类的id值
        String pid=existOneSubject.getId();
        EduSubject existTwoSubject=this.existTwoSubject(edusubjectService,subjectData.getTwoSubjectName(),pid);
        //在数据库中没有找到相同名字的二级分类，那就添加新的二级分类
        if (existTwoSubject==null){
            existTwoSubject=new EduSubject();
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            edusubjectService.save(existTwoSubject);
        }


    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject=eduSubjectService.getOne(wrapper);
        return oneSubject;
    }
    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject=eduSubjectService.getOne(wrapper);
        return twoSubject;
    }


}
