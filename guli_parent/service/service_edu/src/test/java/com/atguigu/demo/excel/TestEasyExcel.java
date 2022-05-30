package com.atguigu.demo.excel;


import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Description TODO
 * Vsrsion 1.0
 *
 * @Author czl0502
 * 学号：3187102130
 * Date 2021/7/12 18:10
 */
public class TestEasyExcel {
    public static void main(String[] args) {
        //实现excel写的操作
        //1.设置写入文件夹地址和excel文件名称
        //路径中1.使用\来转义\符号2.使用/
        String fileName = "D:\\guli_excel\\write.xlsx";


        //2.调用easyexcel里面的方法实现写操作
        //write方法两个参数：第一个参数文件路径名称，第二个参数实体类class
        EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getData());

    }

    //创建一个方法返回list集合
    private static List<DemoData> getData() {
        List<DemoData> demoData = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("czl" + i);
            demoData.add(data);
        }
        return demoData;
    }
    @Test
    public void run(){
        String fileName = "D:\\guli_excel\\write.xlsx";
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }
}
