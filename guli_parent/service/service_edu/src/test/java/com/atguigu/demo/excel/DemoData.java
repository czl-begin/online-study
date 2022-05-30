package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Description TODO
 * Vsrsion 1.0
 *
 * @Author czl0502
 * 学号：3187102130
 * Date 2021/7/12 18:08
 */
@Data
public class DemoData {
    @ExcelProperty(value="学生编号",index=0)
    private Integer sno;
    @ExcelProperty(value="学生姓名",index=1)
    private String sname;
}
