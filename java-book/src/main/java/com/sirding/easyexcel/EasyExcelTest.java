package com.sirding.easyexcel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

public class EasyExcelTest {

//    private static final String FILE_PREFIX = "D:\\项目需求\\企业中心2.0(配置中心)\\数据清洗\\";
    private static final String FILE_PREFIX = "D:\\项目需求\\企业中心2.0(配置中心)\\二期工作\\上线数据\\";
    private static final String XS_FILE_PREFIX = "D:\\项目需求\\京营平台-企业中心2.0-配置中心\\数据同步\\14号同步\\异常处理方案\\";
    public static void main(String[] args) throws Exception {
//        getAuth();
//        getSystem();
//        getProduct();
//        getClientProduct();
//        getProductFromCfe();
//        compareProduct();
//        getDic();

//        获得用户编码去重结果
//        deWeight();

        // 获得新通路企业信息更新脚本
        getXinTongLuList();
        System.out.println("okok");


    }

    public static void deWeight(){
        File file = new File(FILE_PREFIX + "审核信息-临时.xlsx");
        File dstFile = new File(FILE_PREFIX + "审核信息-临时0902.sql");
        readFile(file, (List<String> l) -> {
            try {
                FileOutputStream fos = new FileOutputStream(dstFile, true);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                StringBuilder sb = new StringBuilder();
                sb.append("INSERT INTO user_product(user_code, product_code, status) VALUE('");
                sb.append(l.get(0)).append("','','");
                String status = l.get(1);
                if ("审核不通过".equals(status) || "审核驳回".equals(status)) {
                    status = "20";
                } else if ("审核通过".equals(status) || "正常".equals(status)) {
                    status = "10";
                }else {
                    status = "30";
                }
                sb.append(status).append("');\n");
                osw.write(sb.toString());
                osw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public static void compareProduct() {
        File cefFile = new File(FILE_PREFIX + "导出企业中心产品信息2019-08-01 16_55_46.xlsx");
        File pFile = new File(FILE_PREFIX + "产品提供-产品列表.xlsx");
        List<List<String>> cfeList = new ArrayList<>();
        readFile(cefFile, cfeList);
        List<List<String>> pList = new ArrayList<>();
        readFile(pFile, pList);
        Map<String, String> cfeMap = new HashMap<>();
        cfeList.forEach(l -> {
            String productCode = l.get(0);
            if (Integer.parseInt(productCode) > 50){
                cfeMap.put(productCode, l.get(1));
            }
        });
        System.out.println("=================");
        pList.forEach(l -> {
            String productCode = l.get(0).length() == 4 ? "0" + l.get(0) : l.get(0);
            if (Integer.parseInt(productCode) > 50 && !cfeMap.containsKey(productCode)){
                System.out.println(productCode + "   " + l.get(1));
            }
        });
    }

    /**
     * 解析从企业中心到过来的数据产品数据
     */
    public static void getProductFromCfe() {
        File file = new File(FILE_PREFIX + "导出企业中心产品信息2019-08-01 16_55_46.xlsx");
        List<List<String>> list = new ArrayList<>();
        readFile(file, list);
        StringBuilder sb = new StringBuilder();
        StringBuilder busiSb = new StringBuilder();
        list.forEach(l -> {
            String productCode = l.get(0);
            if (Integer.parseInt(productCode) < 50) {
                busiSb.append("INSERT INTO cfg_busi_info(busi_code, busi_name, msg, created_date, modified_date, yn, status, flow_status) VALUE");
                // 业务code
                busiSb.append("('").append(l.get(0)).append("', '");
                // 业务名称
                busiSb.append(l.get(1)).append("','");
                // 备注
                busiSb.append("null".equals(l.get(3)) || l.get(3) == null ? "" : l.get(3)).append("','");
                // 创建日期
                busiSb.append(l.get(4)).append("','");
                // 更新日期
                busiSb.append(l.get(5)).append("',");
                // yn
                busiSb.append(l.get(6)).append(", 1, 2);\n");
            }else{
                sb.append("INSERT INTO cfg_product_info(product_code, product_name, busi_codes, msg, created_date, modified_date, yn, status, flow_status) VALUE");
                // 产品编码
                sb.append("('").append(l.get(0)).append("', '");
                // 产品名称
                sb.append(l.get(1)).append("','");
                // 产品业务类型
                sb.append("0".equals(l.get(2)) ? "" : l.get(2)).append("','");
                // 备注
                sb.append("null".equals(l.get(3)) || l.get(3) == null ? "" : l.get(3)).append("','");
                // 创建日期
                sb.append(l.get(4)).append("','");
                // 更新日期
                sb.append(l.get(5)).append("',");
                // yn
                sb.append(l.get(6)).append(", 1, 2);\n");
            }
        });
        System.out.println(busiSb.toString());
        System.out.println(sb.toString());
    }

    public static void getProduct() {
        File file = new File(FILE_PREFIX + "产品分类.xlsx");
        List<List<String>> list = new ArrayList<>();
        readFile(file, list);
        StringBuilder sb = new StringBuilder();
        System.out.println(list);
        list.forEach(l -> {
            String systemCode = l.get(0);
            String productCode = l.get(1);
            String productName = l.get(2);
            String busiName = l.get(3);
            String status =  l.get(4);
            String msg = l.get(2);
            sb.append("('").append(productCode).append("', '").append(productName).append("','").append(busiName).append("','").append(msg).append("',");
            if ("下线".equals(status)) {
                sb.append("0,2");
            }else if("未上线".equals(status)){
                sb.append("0, 1");
            }else {
                sb.append("1, 2");
            }
            sb.append("),").append("\n");
        });
        System.out.println(sb.toString());
    }

    /**
     * 解析客户产品信息
     */
    public static void getClientProduct() {
        File file = new File(FILE_PREFIX + "导出企业中心客户产品关系2019-08-08 20_53_09.xlsx");
        File dstFile = new File(FILE_PREFIX + "client_product_190808.sql");
//        StringBuilder sb = new StringBuilder("INSERT INTO cfg_client_product(product_code, " +
//                "user_code, user_name, type, msg, created_date, modified_date, yn, user_type, status, flow_status) VALUE");
        readFile(file, (List<String> l) -> {
            try {
                FileOutputStream fos = new FileOutputStream(dstFile, true);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                StringBuilder sb = new StringBuilder();
                sb.append("INSERT INTO cfg_client_product(app_code, product_code, product_name, user_code, user_name," +
                        " type, msg, created_date, modified_date, yn, user_type, status, flow_status) VALUE('");
                sb.append(l.get(0) + l.get(1)).append("','");
                // 产品编码
                sb.append(l.get(0)).append("','");
                // 产品名称
                sb.append(l.get(8)).append("','");
                // 用户编码
                sb.append(l.get(1)).append("','");
                // 用户名称
                sb.append(l.get(2)).append("'," + " '");
                // 开通状态
                sb.append(l.get(3)).append("', '");
                // 备注信息
                sb.append("null".equals(l.get(5)) || l.get(5) == null ? "" : l.get(5)).append("',");
                // 创建时间
                sb.append(l.get(6) == null ? "NOW()" : "'" + l.get(6) + "'").append("," +  "'");
                // 更新时间
                sb.append(l.get(7)).append("',");
                // yn
                sb.append(l.get(4));
                // 用户类型， 状态， 流程状态
                sb.append(", 2, 1, 2);\n");
                osw.write(sb.toString());
                osw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public static void getAuth() {
//        File file = new File(FILE_PREFIX + "前置系统访问权限.xlsx");
        File file = new File(FILE_PREFIX + "前置系统访问权限190806.xlsx");
        List<List<String>> list = new ArrayList<>();
        readFile(file, list);
        StringBuilder sb = new StringBuilder();
        String prifix = "INSERT INTO cfg_system_auth ( system_code, system_name, " +
                "auth_system_code, auth_system_name, auth_token, msg, STATUS, flow_status ) VALUE";
        list.forEach(l -> {
            String fromSystemCode = l.get(1);
            String fromSystemName = l.get(2);
            String fromToken = l.get(3);
            String msg = Optional.ofNullable(l.get(8)).orElse("");
            sb.append(prifix);
            sb.append("('GYL-TZQZ','供应链-台账前置','")
                    .append(fromSystemCode).append("', '").append(fromSystemName).append("', '").append(fromToken).append("','")
                    .append(msg).append("', 1, 2").append(");\n");
            sb.append(prifix);
            sb.append("('GYL-HXTZ','供应链-核心台账','")
                    .append(fromSystemCode).append("', '").append(fromSystemName).append("', '").append(fromToken).append("','")
                    .append(msg).append("', 1, 2").append(");\n");
            sb.append(prifix);
            sb.append("('ZJWG','资金网关','")
                    .append(fromSystemCode).append("', '").append(fromSystemName).append("', '").append(fromToken).append("','")
                    .append(msg).append("', 1, 2").append(");\n");
        });
        System.out.println(sb.toString());
    }

    public static void getDic() {
        File file = new File(FILE_PREFIX + "导出企业中心字典表.xlsx");
        File dstFile = new File(FILE_PREFIX + "dict.sql");
        readFile(file, (List<String> l) -> {
            try {
                FileOutputStream fos = new FileOutputStream(dstFile, true);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                StringBuilder sb = new StringBuilder();
                sb.append("INSERT INTO dict (code_no,code_name,code_key,code_desc,remark,parent_no,edit_mode,sort_no,yn) value('");
                sb.append(l.get(0)).append("','").append(l.get(1)).append("','").append(l.get(2)).append("','");
                sb.append(l.get(3)).append("','");
                sb.append(Objects.isNull(l.get(4)) || l.get(4).length()<=0 || "null".equals(l.get(4))? "" : l.get(4)).append("','");
                sb.append(Objects.isNull(l.get(5)) || l.get(5).length()<=0 || "null".equals(l.get(5))? "" : l.get(5)).append("','");
                sb.append(l.get(6)).append("',").append(l.get(7)).append(",");
                sb.append(Objects.equals("true", l.get(8)) ? 1: 0).append(");\n");
                osw.write(sb.toString());
                osw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void getSystem(){
        Map<String, String> map = new HashMap<>();
//        File file = new File(FILE_PREFIX + "资金路由系统信息.xlsx");
        File file = new File(FILE_PREFIX + "资金路由系统190806.xlsx");
        List<List<String>> list = new ArrayList<>();
        readFile(file, list);
        list.forEach(l -> {
            StringBuilder sb = new StringBuilder("INSERT INTO cfg_system_info ( system_code, system_name, token, " +
                    "type, msg, STATUS, flow_status ) VALUE");
            // 系统编码
            sb.append("('").append(l.get(1)).append("', '");
            // 系统名称
            sb.append(l.get(2)).append("', '");
            // token和系统类型
            sb.append(l.get(3)).append("', 1").append(",'");
            // 备注
            sb.append("null".equals(l.get(4)) || l.get(4) == null ? "" : l.get(4));
            // 状态，流程状态
            sb.append("', 1, 2").append(");\n");
            map.put(l.get(1), sb.toString());
        });

//        file = new File(FILE_PREFIX + "台账系统列表.xlsx");
        file = new File(FILE_PREFIX + "台账系统列表190806.xlsx");
        list = new ArrayList<>();
        readFile(file, list);
        list.forEach(l -> {
            StringBuilder sb = new StringBuilder("INSERT INTO cfg_system_info ( system_code, system_name, token, " +
                    "type, msg, STATUS, flow_status ) VALUE");
            // 系统编码
            sb.append("('").append(l.get(1)).append("', '");
            // 系统名称
            sb.append(l.get(3)).append("', '");
            // token
            sb.append(l.get(5)).append("',");
            // 系统类型
            sb.append(l.get(4)).append(",'");
            // 备注
            sb.append("null".equals(l.get(6)) || l.get(6) == null ? "" : l.get(6));
            // 状态，流程状态
            sb.append("', 1, 2").append(");\n");
            map.put(l.get(1), sb.toString());
        });
        StringBuilder sb = new StringBuilder();
        map.forEach((k,v) -> sb.append(v));
        System.out.println(sb.toString());
    }

    public static void readFile(File file, List<List<String>> list) {
        try(InputStream inputStream = new FileInputStream(file)) {
            ExcelListener excelListener = new ExcelListener(null);
            new ExcelReader(inputStream, file.getName().endsWith("xlsx") ? ExcelTypeEnum.XLSX : ExcelTypeEnum.XLS, null, excelListener).read();
            if (list != null) {
                list.addAll(excelListener.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFile(File file, Consumer<List<String>> consumer) {
        try(InputStream inputStream = new FileInputStream(file)) {
            ExcelListener excelListener = new ExcelListener(consumer);
            new ExcelReader(inputStream, file.getName().endsWith("xlsx") ? ExcelTypeEnum.XLSX : ExcelTypeEnum.XLS,null
                    , excelListener).read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 需要写入的Excel，有模型映射关系
     *
     * @param file  需要写入的Excel，格式为xlsx
     * @param list 写入Excel中的所有数据，继承于BaseRowModel
     */
    public static void writeExcel(final File file, List<? extends BaseRowModel> list) throws Exception {
        OutputStream out = new FileOutputStream(file);
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet,  有模型映射关系
            Class t = list.get(0).getClass();
            Sheet sheet = new Sheet(1, 0, t);
            writer.write(list, sheet);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ExcelListener extends AnalysisEventListener<List<String>> {
        private List<List<String>> data = new ArrayList<>();
        private Consumer<List<String>> consumer;
        public ExcelListener() {}

        public ExcelListener(Consumer<List<String>> consumer) {
            this.consumer = consumer;
        }
        @Override
        public void invoke(List<String> object, AnalysisContext context) {
            if (context.getCurrentRowNum() == 0) {
                // 过滤掉标题行(第一行)
                return;
            }
            if (consumer != null) {
                // 实时消费防止内存溢出
                consumer.accept(object);
            }else{
                data.add(object);
            }
        }
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {}
        public List<List<String>> getData() {
            return data;
        }
    }

    public static void getXinTongLuList() throws Exception {
//        File file = new File("D:\\项目需求\\京营平台-企业中心2.0-配置中心\\数据同步\\1202-1203同步\\cms-audit-record20191205111200.xls");
//        File file = new File("D:\\项目需求\\京营平台-企业中心2.0-配置中心\\数据同步\\1202-1203同步\\新通路-企业中心1.0-历史数据.xlsx");
//        File file = new File("D:\\项目需求\\京营平台-企业中心2.0-配置中心\\数据同步\\1202-1203同步\\京营平台-导出同步失败的信息2019-12-09 17_21_35.xlsx");
//        File file = new File("D:\\项目需求\\京营平台-企业中心2.0-配置中心\\存量异常数据同步\\新通路\\确定数据.xlsx");
//        File file = new File("D:\\项目需求\\京营平台-企业中心2.0-配置中心\\存量异常数据同步\\新通路\\京营平台-导出同步失败的信息2019-12-24 11_02_22.xlsx");
//        File file = new File("D:\\项目需求\\京营平台-企业中心2.0-配置中心\\存量异常数据同步\\新通路\\新通路低频用户.xlsx");
//        File file = new File("D:\\test\\system\\system-auth.xlsx");
//        File file = new File("D:\\项目需求\\京营平台-企业中心2.0-配置中心\\存量异常数据同步\\SMB-万家\\SMB失败名单1226.xlsx");
        File file = new File("D:\\项目需求\\京营平台-企业中心2.0-配置中心\\存量异常数据同步\\SMB-万家\\smb-jypt-export.xlsx");
        File dstFile = new File("D:\\项目需求\\京营平台-企业中心2.0-配置中心\\数据同步\\1202-1203同步\\xtl_update_rollback.sql");
        File dfile = new File("D:\\项目需求\\京营平台-企业中心2.0-配置中心\\存量异常数据同步\\SMB-万家\\结果.xlsx");
        StringBuffer userCodeList = new StringBuffer();

        List<ExcelData> list = new ArrayList<>();
        readFile(file, (List<String> l) -> {
            try {
                FileOutputStream fos = new FileOutputStream(dstFile, true);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                StringBuilder sb = new StringBuilder();
                sb.append("update cfe_company_info set lic_no='");
                String licNo = l.get(0);
                sb.append(licNo);
                sb.append("' where company_code = '");
                sb.append(l.get(0));
                sb.append("';\n");
//                osw.write(sb.toString());
//                osw.flush();

//                userCodeList.append("('" + l.get(0) + "','" + l.get(1) +  "','" + l.get(2) + "','" + l.get(3) +
//                        "','"+l.get(4)+"','"+l.get(5)+"',1,2,1,1),");

                userCodeList.append("'" + l.get(0) + "',");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

//        writeExcel(dfile, list);
        System.out.println("用户编码================");
        System.out.println(userCodeList.toString());
    }

    @Test
    public void startPrint() throws Exception{
        getXinTongLuList();
    }
}
