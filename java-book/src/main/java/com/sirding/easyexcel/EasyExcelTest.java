package com.sirding.easyexcel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

public class EasyExcelTest {

    private static final String FILE_PREFIX = "D:\\项目需求\\配置中心\\数据清洗\\";
    public static void main(String[] args) {
//        getAuth();
//        getSystem();
//        getProduct();
        getClientProduct();
//        getProductFromCfe();
//        compareProduct();
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
        File file = new File(FILE_PREFIX + "导出企业中心客户产品关系2019-08-02 10_13_48.xlsx");
        File dstFile = new File(FILE_PREFIX + "client_product_190802.sql");
//        StringBuilder sb = new StringBuilder("INSERT INTO cfg_client_product(product_code, " +
//                "user_code, user_name, type, msg, created_date, modified_date, yn, user_type, status, flow_status) VALUE");
        readFile(file, (List<String> l) -> {
            try {
                FileOutputStream fos = new FileOutputStream(dstFile, true);
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                StringBuilder sb = new StringBuilder();
                sb.append("('");
                // 产品编码
                sb.append(l.get(0)).append("','");
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
                sb.append(", 2, 1, 2),\n");
                osw.write(sb.toString());
                osw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // 数据量大 导致 无法全量输出
//        List<List<String>> list = new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
//        list.forEach(l -> {
//            sb.append("('").append(l.get(0)).append("','").append(l.get(1)).append("','").append(l.get(2)).append("'," +
//                    " '").append(l.get(3)).append("', '").append("null".equals(l.get(5)) ? "" : l.get(5)).append("','").append(l.get(6)).append("'," +
//                    "'").append(l.get(7)).append("',").append(l.get(4)).append(", 2, 1, 2),\n");
//        });
//        System.out.println(sb.toString());
    }


    public static void getAuth() {
        File file = new File(FILE_PREFIX + "前置系统访问权限.xlsx");
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

    public static void getSystem(){
        Map<String, String> map = new HashMap<>();
        File file = new File(FILE_PREFIX + "资金路由系统信息.xlsx");
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

        file = new File(FILE_PREFIX + "台账系统列表.xlsx");
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
}
