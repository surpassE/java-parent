package com.sirding.easyexcel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

/**
 * excel转换工具
 * @author dingzhichao3
 */
public class ExcelParseTest {

    private String nullFlag = "null";
    int suffix = 34;



    @Test
    public void createInItem() {
//        String fileName = "D:\\项目需求\\京营平台\\业务数据同步\\0318\\确认数据20200318.xlsx";
//        String fileName = "D:\\项目需求\\京营平台\\业务数据同步\\0327\\新通路一个QY对应多个C数据.xlsx";
//        String fileName = "D:\\项目需求\\京营平台\\业务数据同步\\0426\\万家-无用户ID.xlsx";
        String fileName = "D:\\项目迭代\\交易管理系统\\地址码值-国标.xlsx";
//        new MyExcelDataListener().read(fileName, (list)-> getInItem(list, ExcelData::getColumn1));
//        new MyExcelDataListener().read(fileName, this::getReqGateway);
    }



    @Test
    public void createBatchUpdateSql() {
//        String fileName = "D:\\项目需求\\京营平台\\业务数据同步\\0318\\确认数据20200318.xlsx";
        String fileName = "D:\\项目迭代\\交易管理系统\\国标\\地址码值-国标-3.xlsx";
        new MyExcelDataListener().read(fileName, this::getGbItem);
    }

    /**
     * 获得IN的查询条件
     */
    private void getGbItem(List<ExcelData> list) {
        StringBuilder sb = new StringBuilder("insert into tf_city_codes(city_code, city_name, type, operate_user) " +
                "values");
        list.forEach(row -> {
            sb.append("('").append(row.getColumn1()).append("','").append(row.getColumn2()).append("',1,'init'),").append("\n");
        });
        sb.replace(sb.length() - 1, sb.length(), "");
        System.out.println("执行国标数据插入");
        System.out.println(sb.toString());
    }

    @Test
    public void createInItemBySheetIndex() {
        String fileName = "D:\\项目需求\\洗数的模板\\保理\\副本需要清洗数据的客户名单-1104-1_back.xlsx";
        new MyExcelDataListener().read(fileName, 1, (list)->this.getInItem(list, ExcelData::getColumn2));
    }

    @Test
    public void insertSQL() {
        String fileName = "D:\\项目需求\\京营平台-企业中心2.0-配置中心\\存量异常数据同步\\SMB-万家\\smb-jypt-export.xlsx";
        new MyExcelDataListener().read(fileName, (list)->getInsertSQL("INSERT INTO tmp(a, b, c) VALUES", list, (excelData) -> String.format("('%s','%s'),", excelData.getColumn0(), excelData.getColumn1())));
        new MyExcelDataListener().read(fileName, (list)->getInsertSQL("INSERT INTO tmp_dd(a, b, c) VALUES", list,
                (excelData) -> String.format("('%s','%s'),", excelData.getColumn0(), excelData.getColumn1())));
    }

    @Test
    public void insertDictSQL() {
        String fileName = "C:\\Users\\dingzhichao3\\Downloads\\京营平台-导出共用配置-字典表.xlsx";
        String sql = "INSERT INTO dict(code_no, code_name, code_key, code_desc) VALUES";
        new MyExcelDataListener().read(fileName, (list)->getInsertSQL(sql, list,
                (excelData) -> String.format("('%s','%s', '%s', '%s'),",
                        excelData.getColumn0(), excelData.getColumn1(), excelData.getColumn2(), excelData.getColumn3())));
    }

    @Test
    public void writeInclude() {
//        String fileName = "D:\\项目需求\\京营平台-企业中心2.0-配置中心\\存量异常数据同步\\SMB-万家\\test-include.xlsx";
        String fileName = "D:\\项目需求\\京营平台\\业务数据同步\\TMP.xlsx";
        Set<String> set = new HashSet<>();
        set.add("column0");
        set.add("column15");
        MyExcelDataListener.writeInclude(fileName, "test", set, getTestData());
    }

    @Test
    public void readAndWriteInclude() {
        Set<String> set = new HashSet<>();
        set.add("column0");
        set.add("column1");
        set.add("column2");
        set.add("column3");
        set.add("column4");
        set.add("column5");
        List<ExcelData> result = new ArrayList<>();
        String readFileName = "C:\\Users\\dingzhichao3\\Desktop\\excel\\SMB-新通路用户关系.xlsx";
        new MyExcelDataListener().read(readFileName, result::addAll);

        result.forEach(item -> {
            System.out.println(item.getColumn4());
            JSONObject json = JSON.parseObject(item.getColumn4());
            item.setColumn2(json.getString("adminTel"));
            item.setColumn3(json.getString("contactName"));
            item.setColumn4(json.getString("contactTel"));
            item.setColumn5(json.getString("contactType"));
        });
        String dstFileName = "C:\\Users\\dingzhichao3\\Desktop\\excel\\SMB-XTL-用户关系.xlsx";
        MyExcelDataListener.writeInclude(dstFileName, "test", set, result);
    }

    @Test
    public void writeExclude() {
        String fileName = "D:\\项目需求\\京营平台-企业中心2.0-配置中心\\存量异常数据同步\\SMB-万家\\test-exclude.xlsx";
        Set<String> set = new HashSet<>();
        set.add("column0");
        MyExcelDataListener.writeExclude(fileName, "我也是测试", set, getTestData());
    }

    @Test
    public void findMissingData() {

        String moreStr = "";
        String lessStr = "";
        String more = moreStr.replaceAll("\\(", "").replace(")", "").replaceAll(" +", "").replaceAll("'","");
        String less = lessStr.replaceAll("\\(", "").replace(")", "").replaceAll(" +", "").replaceAll("'", "");
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>();
        Arrays.stream(more.split(",")).filter(item -> !(Arrays.asList(less.split(",")).contains(item))).forEach(i -> {
            System.out.println("user_code:" + i);
//            sb.append(i.replace("'", "")).append(",");
            sb.append("'" + i + "'").append(",");
            list.add(i);
        });
        System.out.println("数量: " + list.size());
        System.out.println(sb.toString());
    }

    @Test
    public void findRepeat() {
        String more = "";
        Map<String, Integer> map = new HashMap<>(1024);
        System.out.println(more.split(",").length);
        Arrays.stream(more.split(",")).forEach(item -> {
            item = item.replace("'", "").trim();
            Integer count = map.get(item);
            if (count != null) {
                count++;
                map.put(item, count);
            }else {
                map.put(item, 1);
            }
        });
        System.out.println(map.size());
        map.forEach((k, v) -> {
            if (v != null && v > 1) {
                System.out.println("重复的key: " + k);
            }
        });
    }

    /**
     * 获得IN的查询条件
     */
    private void getInItem(List<ExcelData> list, Function<ExcelData, String> function) {
        StringBuilder sb = new StringBuilder("(");
        list.forEach(row -> {
            sb.append("'");
            String col = function.apply(row);
            if (StringUtils.isEmpty(col) || nullFlag.equals(col.toLowerCase())) {
                col = "";
            }
            sb.append(col);
            sb.append("',");
//            sb.append(",");
        });
        sb.replace(sb.length() - 1, sb.length(), "");
        sb.append(")");
        System.out.println("查询条件IN的集合");
        System.out.println(sb.toString());
        System.out.println("查询条件IN的集合");
    }

    /**
     * 拼接插入的SQL
     */
    private void getInsertSQL(String sql, List<ExcelData> list, Function<ExcelData, String> function) {
        StringBuilder sb = new StringBuilder(sql);
        list.forEach(row -> sb.append(function.apply(row)).append("\n"));
        sb.replace(sb.length() - 1, sb.length(), "");
        sb.append(";");
        System.out.println("查询条件IN的集合");
        System.out.println(sb.toString());
        System.out.println("查询条件IN的集合");
    }


    private List<ExcelData> getTestData() {
        List<ExcelData> list = new ArrayList<>();
        String data = "'C1004901737','C1004675219','C1004910956','C1004839924','C1004730670','C1004582445','C1004637498','C1004914639','C1004785235','C1004611565','C1004855765','C1004847774','C1005082763','C1004947766','C1004944617','C1004927398','C1004907514','C1004902045'";
        Arrays.stream(data.split(",")).forEach(item -> {
            if (!StringUtils.isEmpty(item.trim())) {
                ExcelData excelData = new ExcelData();
                excelData.setColumn0(item.trim().replace("'", ""));
                list.add(excelData);
            }
        });
        return list;
    }



    /**
     * 获得IN的查询条件
     */
    private void getBatchUpdateSQL(List<ExcelData> list) {
        StringBuilder userSql = new StringBuilder("update user set yn = 0 where id in (");
        StringBuilder userCompanySql = new StringBuilder("update user_company set yn = 0 where id in (");
        StringBuilder userCompanyLegalSql = new StringBuilder("update user_company_legal set yn = 0 where id in (");
        StringBuilder userNaturalSql = new StringBuilder("update user_natural_person set yn = 0 where id in (");
        StringBuilder userLoginRelSql = new StringBuilder("update user_login_rel set yn = 0 where userId in (");

        List<String> noRealNameList = new ArrayList<>();
        List<String> hasRealNameEntList = new ArrayList<>();
        list.forEach(row -> {
            String userCode = row.getColumn1();
            String authStatus = row.getColumn4();
            String userType = row.getColumn7();
            if ((Objects.equals(userType, "1") || Objects.equals(userType, "0")) &&
                    !Objects.equals(authStatus, "20") &&
                    !noRealNameList.contains(userCode)) {
                noRealNameList.add(userCode);
            }

            if (Objects.equals(userType, "2") && Objects.equals(authStatus, "20") && !noRealNameList.contains(userCode)) {
                hasRealNameEntList.add("'" + userCode + "'");
            }
        });

        System.out.println("未实名的用户列表,  数量: " + noRealNameList.size());
        System.out.println(String.join(",", noRealNameList));
        System.out.println("已实名的企业用户列表,  数量: " + hasRealNameEntList.size());
        System.out.println(String.join(",", hasRealNameEntList));

        list.forEach(row -> {
            String uId = row.getColumn0();
            String userCode = row.getColumn1();
            String authStatus = row.getColumn4();
            String companyId = row.getColumn5();
            String companyLegalId = row.getColumn6();
            String userType = row.getColumn7();
            String nid = row.getColumn8();
            if(Objects.equals(userType, "2") && Objects.equals(authStatus, "20") && !noRealNameList.contains(userCode)){
                userSql.append(uId).append(",");
                userCompanySql.append(companyId).append(",");
                userCompanyLegalSql.append(companyLegalId).append(",");
                userNaturalSql.append(nid).append(",");
            }
        });
        userSql.replace(userSql.length() - 1, userSql.length(), "").append(")");
        userCompanySql.replace(userCompanySql.length() - 1, userCompanySql.length(), "").append(")");
        userCompanyLegalSql.replace(userCompanyLegalSql.length() - 1, userCompanyLegalSql.length(), "").append(")");
        userNaturalSql.replace(userNaturalSql.length() - 1, userNaturalSql.length(), "").append(")");


        System.out.println("更新用户信息的SQL：");
        System.out.println(userSql.toString());
        System.out.println("更新企业的SQL：");
        System.out.println(userCompanySql.toString());
        System.out.println("更新法人关系的SQL：");
        System.out.println(userCompanyLegalSql.toString());
        System.out.println("自然人表信息的SQL：");
        System.out.println(userNaturalSql.toString());


    }


    /**
     * 获得IN的查询条件
     */
    private void getSizeData(List<ExcelData> list, Function<ExcelData, String> function) {
        StringBuilder sb = new StringBuilder("");
        int count = 0;

        for (int i = 0; i < list.size(); i++) {
            ExcelData row = list.get(i);
            String col = function.apply(row);
            if (StringUtils.isEmpty(col) || nullFlag.equals(col.toLowerCase())) {
                col = "";
            }
            sb.append(col);
            sb.append(",");
            count++;
            if (count == 2000) {
                sb.replace(sb.length() - 1, sb.length(), "");
                System.out.println("查询条件IN的集合");
                System.out.println(sb.toString());
                System.out.println("查询条件IN的集合");
                try {
                    Files.write(Paths.get("D:\\test\\预热用户\\user-" + suffix + ".txt"), sb.toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                count = 0;
                suffix++;
                sb = new StringBuilder();
            }
        }
        if (sb.toString().endsWith(",")) {
            sb.replace(sb.length() - 1, sb.length(), "");
        }
        System.out.println("查询条件IN的集合");
        System.out.println(sb.toString());
        System.out.println("查询条件IN的集合");
        this.saveData(suffix, sb.toString());
    }

    public void saveData(int suffix, String sb ) {
        try {
            Files.write(Paths.get("D:\\test\\预热用户\\user-" + suffix + ".txt"), sb.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获得IN的查询条件
     */
    private void getReqGateway(List<ExcelData> list) {
        StringBuilder sb = new StringBuilder("INSERT inter_req_gateway(req_gateway_code, system_code, resource_code, " +
                "resource_name, resource_system_code, clazz_name, method_name, apply_user) VALUES");
        List<String> systemCodeList = Arrays.asList("YIDAI", "NPN", "LOANPLATFORM", "QUICKLOAN", "LEDGERERP",
                "LOAN_DIST", "JINGBAOBEI", "PLEDGE", "JXD", "LOAN-POLICY", "MEDIA_AD", "GYL-ABS", "JINCAI","RISK_LOAN",
                "JINCAI_YC", "zcgl", "MSF_LOAN", "ISVFACTORING", "GOLDEN_EYE_MERCHANT_SERVER", "PLEDGE-WHALE",
                "CCS_ACCESS", "JRISK_CORPORATE_PLATFORM", "WRFS-LOAN", "CBP", "YCSYSTEM");

        list.forEach(item -> {
            systemCodeList.forEach(systemCode -> {
                sb.append("('").append(UUID.randomUUID().toString()).append("',");
                sb.append("'").append(systemCode).append("',");
                sb.append("'").append(item.getColumn1()).append("',");
                sb.append("'").append(item.getColumn2()).append("',");
                sb.append("'").append(item.getColumn6()).append("',");
                sb.append("'").append(item.getColumn4()).append("',");
                sb.append("'").append(item.getColumn3()).append("',");
                sb.append("'").append("dingzhichao3").append("'),");
            });
        });

        System.out.println("输出结果=================");
        System.out.println(sb.toString());
    }


    @Test
    public void createMq() {
//        String fileName = "D:\\test\\ledger_mq\\还款流水2021-12-30 12_51_47-企业主贷-过滤后.xlsx";
        String fileName = "";
//        fileName = "D:\\test\\ledger_mq\\还款流水22022-01-06 14_03_55-账扣.xlsx";
        fileName = "D:\\test\\ledger_mq\\还款流水2022-01-06 14_00_43-账扣.xlsx";
        new MyExcelDataListener().read(fileName, this::getMqList);
    }

    /**
     * 获得IN的查询条件
     */
    private void getMqList(List<ExcelData> list) {
        ArrayList<JSONObject> data = new ArrayList<>();
        list.forEach(row -> {
            JSONObject json = new JSONObject();
            json.put("class", "com.jd.jr.gyl.trade.fund.domain.mq.LedgerRepayNoticeMq");
            json.put("code", 200);
            json.put("applyNo", row.getColumn6());
            json.put("bizSystem", row.getColumn1());
            json.put("custNo", row.getColumn5());
            json.put("companyCode", row.getColumn7());
            json.put("investorNo", StringUtils.hasLength(row.getColumn4()) ? row.getColumn4() : "CQSJ");
            json.put("productCode", row.getColumn9());
            JSONObject detail = new JSONObject();
            detail.put("class", "com.jd.jr.gyl.trade.fund.domain.mq.LedgerRepayFeeDetail");
            detail.put("rnp", new BigDecimal(row.getColumn3()).divide(BigDecimal.valueOf(100)));
            detail.put("rgp", new BigDecimal(row.getColumn8()).divide(BigDecimal.valueOf(100)));
            detail.put("rrp", new BigDecimal(row.getColumn0()).divide(BigDecimal.valueOf(100)));
            detail.put("rop", new BigDecimal(row.getColumn2()).divide(BigDecimal.valueOf(100)));
            json.put("repayFeeDetail", detail);
            data.add(json);
        });
        System.out.println(JSON.toJSONString(data));
    }


}
