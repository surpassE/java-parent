package com.sirding.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 信息
 * 通过excel导出数据时，此处一定不能使用Accessors属性，否则将服务解析Listener中的invoke数据
 * @author dingzhichao3
 */
@Data
public class ExcelData {

    @ExcelProperty(index = 0)
    private String column0;
    @ExcelProperty(index = 1)
    private String column1;
    @ExcelProperty(index = 2)
    private String column2;
    @ExcelProperty(index = 3)
    private String column3;
    @ExcelProperty(index = 4)
    private String column4;
    @ExcelProperty(index = 5)
    private String column5;
    @ExcelProperty(index = 6)
    private String column6;
    @ExcelProperty(index = 7)
    private String column7;
    @ExcelProperty(index = 8)
    private String column8;
    @ExcelProperty(index = 9)
    private String column9;
    @ExcelProperty(index = 10)
    private String column10;
    @ExcelProperty(index = 11)
    private String column11;
    @ExcelProperty(index = 12)
    private String column12;
    @ExcelProperty(index = 13)
    private String column13;
    @ExcelProperty(index = 14)
    private String column14;
    @ExcelProperty(index = 15)
    private String column15;
}
