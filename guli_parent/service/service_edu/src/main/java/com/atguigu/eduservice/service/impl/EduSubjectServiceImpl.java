package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-13
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
      try{
          //文件输入流（直接用file也可以）
          InputStream in=file.getInputStream();
          //调用方法进行读取
          EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
      }
      catch(Exception e){
         e.printStackTrace();
      }
    }
    //查询
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1.查询所有的一级分类 parent_id
        QueryWrapper wrapperOne=new QueryWrapper();
        wrapperOne.eq("parent_id","0");
        //1 已经自动为我们封装了baseMapper，使用此方法是为了尝试
        List<EduSubject> oneSubject=baseMapper.selectList(wrapperOne);
        /*System.out.println(oneSubject);*/
        //2
        //this.list(wrapperOne);
        //2.查询所有的二级分类，
        QueryWrapper wrapperTwo=new QueryWrapper();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubject = this.list(wrapperTwo);


        //创建list集合，用于存储最终封装数据
        List<OneSubject> finalSubjectList=new ArrayList<>();
        //3.封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值
        //封装到要求的list集合里面List<OneSubject> finalSubjectList
        for (int i = 0; i < oneSubject.size(); i++) {
            EduSubject eduSubject=oneSubject.get(i);
            OneSubject onesubjectList=new OneSubject();
            //eduSubject的值复制到onesubjectList中去
            BeanUtils.copyProperties(eduSubject,onesubjectList);


            //在一级分类中循环查询所有二级分类
            //创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twofinalSubjectList=new ArrayList<>();
            //4.封装二级分类
            for (int  j= 0; j < twoSubject.size(); j++) {
                EduSubject twoeduSubject=twoSubject.get(j);
                if (eduSubject.getId().equals(twoeduSubject.getParentId())){
                    TwoSubject twoSubjectList=new TwoSubject();
                    BeanUtils.copyProperties(twoeduSubject,twoSubjectList);
                    twofinalSubjectList.add(twoSubjectList);
                    /*System.out.println(twofinalSubjectList);*/
            }




        }
            //把一级分类下面所有二级分类放到一级分类中
            //得到children
            onesubjectList.setChildren(twofinalSubjectList);
            //得到id和title
            finalSubjectList.add(onesubjectList);


        }

        return finalSubjectList;
    }
}
