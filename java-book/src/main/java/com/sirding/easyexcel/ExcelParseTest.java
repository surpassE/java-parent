package com.sirding.easyexcel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.IOException;
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
        String fileName = "D:\\项目需求\\洗数的模板\\保理\\副本需要清洗数据的客户名单-1104-1_back.xlsx";
        new MyExcelDataListener().read(fileName, (list)-> getInItem(list, ExcelData::getColumn2));
//        new MyExcelDataListener().read(fileName, this::getReqGateway);
    }



    @Test
    public void createBatchUpdateSql() {
//        String fileName = "D:\\项目需求\\京营平台\\业务数据同步\\0318\\确认数据20200318.xlsx";
        String fileName = "D:\\项目需求\\京营平台\\业务数据同步\\0515\\校验同步完成的用户2020-05-15 12_10_55.xlsx";
        new MyExcelDataListener().read(fileName, (list)-> getBatchUpdateSQL(list));
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
//        String moreStr = "('QY10024598','QY10104335','C1003323377','C1003330466','C1003352911','C1003356222','C1003358957','C1003366172','C1003369546','C1003376318','C1002123664','C1003395635','C1003401963','C1003405683','C1003415591','C1003424385','C1003452609','C1003455327','C1003458420','C1003473724','C1002173255','C1003468677','C1003259050','C1003492088','C1001531581','C1003499191','C1003501336','C1003359387','C1003512381','C1003478537','C1003390064','C1003515765','C1003516419','C1003384820','C1003517871','C1003468481','C1003379712','C1003529032','C1003421950','C1003538212','C1003382922','C1003518063','C1003514725','C1003544217','C1003546406','C1003546950','C1003547734','C1003549356','C1003545732','C1003550177','C1003545902','C1003552665','C1003549747','C1003553715','C1003483320','C1003557538','C1003561052','C1002847887','C1003466360','C1003563442','C1003462215','C1003541427','C1003565816','C1003565851','C1003570031','C1003577111','C1003582217','C1003724056','QY10093484','C1003363240','C1003743662','C1003745014','C1003538174','C1003761612','C1002417524','C1003803259','C1003806733','C1003826193','C1003826165','C1001789515','C1001439406','C1003497003','C1003880637','C1002800629','C1003549551','QY10085650','C1003538382','C1003537584','C1003546120','C1003566763','C1000525158','C1003559957','C1003891879','C1003938069','C1000912517','C1003408092','C1003961041','C1003536966','C1003967041','C1003542097','C1003971403','C1003565793','C1003738075','C1003982719','C1003557914','C1003984829','C1003383220','C1003989184','C1004000620','C1004002126','C1003393274','C1004004168','C1004005735','C1003526369','C1003403989','C1003993202','C1004008123','C1003948503','C1002760386','C1004008382','C1003466997','C1003553645','C1003998507','C1004006736','C1002297081','C1003565597','C1004011135','C1004014569','C1004014225','C1004015184','C1004014758','C1004021388','C1004021894','C1003992443','C1003392864','C1003885368','C1004027223','C1004011312','C1004028603','C1003724092','C1003423632','C1003555300','C1003545811','C1002804297','C1002266956','C1004003974','C1003409619','C1003466908','C1004000580','C1003462781','C1004034486','C1004034922','C1002801679','C1004011716','C1003540640','C1003996020','C1003421921','C1004033473','C1003553264','C1004049112','C1004052968','C1004026004','C1002689765','C1004055911','C1004056094','C1003534216','C1003998968','C1004057844','C1004058406','C1004033661','C1004058828','C1003763363','C1003558031','C1004061053','C1004061466','C1004008055','C1002795353','C1004061900','C1003989387','C1004066004','C1004066149','C1003362182','C1004071339','C1002805882','C1003996314','C1003537524','C1003513609','C1004006328','C1004076511','C1003350497','C1004079578','C1004084113','C1004086634','C1002804261','C1004064661','C1004091044','C1004092412','C1004028740','C1002374009','C1004093908','C1004094944','C1004095827','QY10019018','C1004006928','C1004097066','C1004098104','C1004050065','C1003818621','C1004000926','C1004046514','C1001887423','C1003802238','C1003574581','C1004110174','C1003398348','C1001426927','C1000948779','C1004232696','C1004240956','C1002235031','C1004243656','C1004229752','C1004004170','C1004250281','C1004059857','C1004244716','C1002750352','C1004286314','C1004008261','C1004005195','C1003505766','C1003725945','C1004304962','C1003493041','C1004308563','C1003859982','C1004007468','C1003333970','C1002646782','C1004316107','C1003771998','C1004274392','C1003994971','C1003659880','C1003554534','C1004315126','C1001583196','C1004279656','C1003996778','C1003954898','C1004352107','C1004354830','C1002301260','C1004369893','C1004370897','C1004372919','C1004382300','C1004382956','C1004333340','C1004384322','C1004384494','C1004011418','C1004385367','C1003499774','C1004387489','C1004387843','C1003322225','C1004010179','C1003455076','C1004051193','C1004402912','C1004409598','QY10095210','C1004414119','C1004414753','C1004415641','C1004415762','C1003992394','C1004041659','C1004056355','C1004032418','C1004430396','C1004435355','C1002169045','C1003513997','C1004453544','C1004031320','C1003538666','C1003534409','C1002740104','C1003992967','C1003959977','C1003564143','C1004478990','C1003585814','C1003935716','C1004481810','C1004482479','C1004483045','C1004476697','C1004093274','C1004006262','C1003374315','C1004488122','C1004489647','C1004492612','C1004492918','C1004490538','C1004499198','C1002738508','C1004007626','C1004505178','C1003978560','C1003995141','C1003565485','C1004479700','C1003991114','C1004377219','C1004031898','C1004002478','C1003729083','C1004026471','C1004413511','C1003562210','C1003794922','C1003975648','C1004429366','C1003817092','C1003401643','C1004538333','C1004295621','C1002697069','C1004315774','C1004278141','C1004011847','C1004484259','C1003364770','C1004364598','C1004236872','C1004073292','C1004067190','C1003794663','C1003509845','C1004008520','C1003822544','C1002848208','C1004005364','C1003997795','C1003993638','C1003988974','C1004018058','C1004062477','C1002614127','C1003982103','C1004004055','C1002807336','C1004255890','C1004552321','C1004006647','C1004484632','C1004600198','C1004289850','C1004438253','C1004004784','C1004476901','C1004060465','C1004060585','C1004225476','C1004385003','C1002317293','C1004006699','C1003891701','C1003978502','C1003352323','C1003923229','C1004007896','C1002840570','C1001484631','C1003537722','C1003558225','C1003992111','C1001513172','C1004020799','C1004416019','C1004000252','C1004002496','C1003574186','C1004370627','C1003984253','C1004853712','C1004119359','C1001717662','C1003570070','C1004353586','C1002652095','C1004104644','C1004073116','C1003587072','C1004414300','C1003539086','C1003505527','C1002239964','C1003959773','C1003570104','C1003484954','C1004944507','C1004413043','C1004092650','C1003412032','C1004387665','C1002768123','C1005000314','C1003572973','C1004094807','C1002660126','C1004072170','C1004073982','C1004051162','C1004029956','C1004033736','C1003545290','C1003913417','C1002286329','C1003660173','C1002126273','C1003550558','C1003378227','C1003545715','C1003477676','C1003384279','C1004388771','C1004414647','C1004380619','C1002157275','C1004420931','C1004005382','C1004500407','C1004382970','C1003822761','C1003540910','C1004497934','C1003563976','C1004406393','C1003996655','C1004479273','C1004114463','C1004837038','C1003541485','C1004031742','C1004264805','C1004071090','C1004058971','C1003944924','C1001768781','C1003822362','C1004269394','C1004096112','C1003514659','C1003689404','C1004426286','C1004241792','C1004016043','C1004230066','C1004381583','C1004372813','C1004937195','C1003516113','C1004006813','C1004063877','C1004416763','QY10091654','QY10158088','QY10132220','QY10164081','QY10106845','QY10085980','QY10110897','QY10188421','QY10174832','QY10125656','QY10138570','C1004667865','QY10090053','QY10107610','QY10062929','QY10090548','QY10095124','C1004400983','C1000892390','QY10071430','C1004433378','C1003940831','QY10109593','C1003588873','C1004102631','C1004002026','QY10184825','C1003757589','C1004053222','C1002851772','C1003996076','C1003811482','C1004008138','C1003458470','C1003986543','C1002730769','C1002463463'";
        String moreStr = "('C1003532099','C1003546241','C1003547484','QY10008309','QY10028561','QY10052403','C1003582560','QY10013831','QY10017833','QY10012636','C1003479728','QY10015483','QY10013207','QY10014399','QY10022218','QY10022039','QY10011591','QY10019258','QY10015062','C1003423555','QY10016700','QY10021153','QY10021843','QY10017247','QY10017959','QY10017864','QY10016797','QY10010161','QY10020572','C1003502661','C1004060649','QY10028015','C1003549537','C1004088314','QY10137673','QY10046130','QY10016449','QY10019332','C1004103781','QY10018724','QY10034889','QY10018879','QY10033897','QY10054707','QY10021779','QY10020307','QY10075492','QY10046728','QY10036169','QY10151834','QY10023084','QY10127561','QY10016878','QY10026018','QY10015386','QY10049232','QY10173596','QY10066813','QY10021236','QY10153804','QY10082685','QY10048265','QY10025196','QY10061457','C1003529875','QY10008318','QY10027788','QY10015411','QY10082086')";
        String lessStr = "('C1003423555','C1003479728','C1003502661','C1003529875','C1003532099','C1003546241','C1003547484','C1003549537','C1003582560','C1004060649','C1004088314','C1004088314','C1004103781','QY10008309','QY10008318','QY10010161','QY10011591','QY10012636','QY10013207','QY10013831','QY10014399','QY10015062','QY10015386','QY10015411','QY10015483','QY10016449','QY10016700','QY10016797','QY10016878','QY10017247','QY10017833','QY10017864','QY10017959','QY10018724','QY10018879','QY10019258','QY10019332','QY10020307','QY10020572','QY10021153','QY10021236','QY10021779','QY10021843','QY10022039','QY10022218','QY10023084','QY10025196','QY10026018','QY10027788','QY10028015','QY10028561','QY10033897','QY10034889','QY10036169','QY10046130','QY10046728','QY10048265','QY10049232','QY10052403','QY10054707','QY10061457','QY10066813','QY10075492','QY10082086','QY10082685','QY10127561','QY10137673','QY10151834','QY10153804','QY10173596')";

        String more = moreStr.replaceAll("\\(", "").replace(")", "").replaceAll(" +", "").replaceAll("'", "");
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
        String more = "'C1007091578','C1007137026','C1007140913','C1007148732','C1007151729','C1007154580','C1007165620','C1007175886','C1007176117','C1007177112','C1007201636','C1007209009','C1007209227','C1007209899','C1007211841','C1007211957','C1007220028','C1007221148','C1007222406','C1007231602','C1007233759','C1007280948','C1007282693','C1007283917','C1007288933','C1007290061','C1007292068','C1007299524','C1007303542','C1007305256','C1007310740','C1007314847','C1007326141','C1007327813','C1007328164','C1007328663','C1007328721','C1007329480','C1007329740','C1007332916','C1007333273','C1007334616','C1007337848','C1007342762','C1007342789','C1007351389','C1007352616','C1007356154','C1007358108','C1007358849','C1007363400','C1007365591','C1007368214','C1007369440','C1007371156','C1007374431','C1007381704','C1007381895','C1007383653','C1007388834','C1007390626','C1007402756','C1007403614','C1007405014','C1007406182','C1007413166','C1007413322','C1007413696','C1007413865','C1007414046','C1007414078','C1007414193','C1007414247','C1007414547','C1007414729','C1007415221','C1007415711','C1007416572','C1007416613','C1007416832','C1007418328','C1007418532','C1007420682','C1007422110','C1007425435','C1007425994','C1007426004','C1007427550','C1007428051','C1007431992','C1007437144','C1007438647','C1007445695','C1007452056','C1007452993','C1007453074','C1007454270','C1007456671','C1007459061','C1007463312','C1007463931','C1007479855','C1007483128','C1007486068','C1007489583','C1007490616','C1007496433','C1007498165','C1007518272','C1007518326','C1007519393','C1007522277','C1007523475','C1007530173','C1007531340','C1007535825','C1007559520','C1007579828','C1007586783','C1007588675','C1007591693','C1007603824','C1007604621','C1007605475','C1007610482','C1007611838','C1007612286','C1007612680','C1007618286','C1007623483','C1007623622','C1007623874','C1007648281','C1007654701','C1007655623','C1007656633','C1007661872','C1007667007','C1007673531','C1007675099','C1007675342','C1007679145','C1007679728','C1007681055','C1007682216','C1007693808','C1007694541','C1007700960','C1007705943','C1007711286','C1007742606','C1007742802','C1007743102','C1007751023','C1007762363','C1007762891','C1007772128','C1007772376','C1007773586','C1007793133','C1007796211','C1007796849','C1007799123','C1007816396','C1007817986','C1007822755','C1007823254','C1007827730','C1007952021','C1007953513','C1007967618','C1007967951','C1007970729','C1007971326','C1007978762','C1007979156','C1007983118','C1007988137','C1007989030','C1007993664','C1007999223','C1008010896','C1008016185','C1008021396','C1008021459','C1008023115','C1008026235','C1008055226','C1008056088','C1008102528','C1008108548','C1008124772','C1008145037','C1008149656','C1008154376','C1008160730','C1008183621','C1008184826','C1008185941','C1008198809','C1008200939','C1008201712','C1008224677','C1008228037','C1008240107','C1008270325','C1008272826','C1008277632','C1008281632','C1008281839','C1008284489','C1008287954','C1008291667','C1008295431','C1008304190','C1008308304','C1008321705','C1008328442','C1008335313','C1008349774','C1008357027','C1008359890','C1008360330','C1008366692','C1008367797','C1008372281','C1008379238','C1008380735','C1008381287'";
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

}
