package com.sirding.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 模板的读取类
 *
 * @author dzc
 */
public class MyExcelDataListener extends AnalysisEventListener<ExcelData> {

    private List<ExcelData> list = new ArrayList<>();

    @Override
    public void invoke(ExcelData data, AnalysisContext context) {
//        System.out.println("解析到一条数据:" + JSON.toJSONString(data));
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("所有数据解析完成！");
    }

    /**
     * 读sheet0数据
     * @param fileName 文件名称
     * @param consumer 消费者
     */
    public void read(String fileName, Consumer<List<ExcelData>> consumer) {
        EasyExcel.read(fileName, ExcelData.class, this).sheet().doRead();
        System.out.println("需要解析的数据条数:" + this.list.size());
        consumer.accept(this.list);
    }

    /**
     * 读指定sheet
     * @param fileName 文件名称
     * @param consumer 消费者
     * @param sheetIndex sheet索引
     */
    public void read(String fileName, int sheetIndex, Consumer<List<ExcelData>> consumer) {
        ExcelReader excelReader = EasyExcel.read(fileName).build();
        excelReader.read(EasyExcel.readSheet(sheetIndex).head(ExcelData.class).registerReadListener(this).build());
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
        System.out.println("需要解析的数据条数:" + this.list.size());
        consumer.accept(this.list);
    }

    /**
     * 保存excel
     * @param fileName 文件名称
     * @param sheetName sheet名称
     * @param excludeSet 排除的列
     * @param list 排除的列
     */
    public static void writeExclude(String fileName, String sheetName, Set<String> excludeSet, List<ExcelData> list) {
        System.out.println("需要保存的数据条数:" + list.size());
        EasyExcel.write(fileName, ExcelData.class).excludeColumnFiledNames(excludeSet).sheet(sheetName).doWrite(list);
    }

    /**
     * 保存excel
     * @param fileName 文件名称
     * @param sheetName sheet名称
     * @param includeSet 包含的列
     * @param list 数据
     */
    public static void writeInclude(String fileName, String sheetName, Set<String> includeSet, List<ExcelData> list) {
        System.out.println("需要保存的数据条数:" + list.size());
        EasyExcel.write(fileName, ExcelData.class).includeColumnFiledNames(includeSet).sheet(sheetName).doWrite(list);
    }
}
