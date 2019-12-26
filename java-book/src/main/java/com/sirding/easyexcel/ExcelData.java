package com.sirding.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 信息
 * @author dingzhichao3
 */
@Data
@Accessors(chain = true)
public class ExcelData {

    @ExcelProperty(value="第1例", index = 0)
    private String column0;
    @ExcelProperty(value="第2例", index = 1)
    private String column1;
    @ExcelProperty(value="第3例", index = 2)
    private String column2;
    @ExcelProperty(value="第4例", index = 3)
    private String column3;
    @ExcelProperty(value="第5例", index = 4)
    private String column4;
    @ExcelProperty(value="第6例", index = 5)
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
