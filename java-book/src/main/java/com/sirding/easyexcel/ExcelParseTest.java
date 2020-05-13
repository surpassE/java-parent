package com.sirding.easyexcel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;

/**
 * excel转换工具
 * @author dingzhichao3
 */
public class ExcelParseTest {

    private String nullFlag = "null";



    @Test
    public void createInItem() {
//        String fileName = "D:\\项目需求\\京营平台\\业务数据同步\\0318\\确认数据20200318.xlsx";
//        String fileName = "D:\\项目需求\\京营平台\\业务数据同步\\0327\\新通路一个QY对应多个C数据.xlsx";
//        String fileName = "D:\\项目需求\\京营平台\\业务数据同步\\0426\\万家-无用户ID.xlsx";
        String fileName = "D:\\项目需求\\京营平台\\同步客户产品关系至ESU\\0426\\已同步用户.xlsx";
        new MyExcelDataListener().read(fileName, (list)-> getInItem(list, ExcelData::getColumn2));
    }

    @Test
    public void createBatchUpdateSql() {
//        String fileName = "D:\\项目需求\\京营平台\\业务数据同步\\0318\\确认数据20200318.xlsx";
        String fileName = "D:\\项目需求\\京营平台\\业务数据同步\\0410\\一个QY对应多个C.xlsx";
        new MyExcelDataListener().read(fileName, (list)-> getBatchUpdateSQL(list));
    }

    @Test
    public void createInItemBySheetIndex() {
        String fileName = "D:\\项目需求\\京营平台\\业务数据同步\\0401\\KA切换超级账户第五批数据清洗名单-0331-发布版-update.xlsx";
        new MyExcelDataListener().read(fileName, 0, (list)->this.getInItem(list, ExcelData::getColumn0));
    }

    @Test
    public void insertSQL() {
        String fileName = "D:\\项目需求\\京营平台-企业中心2.0-配置中心\\存量异常数据同步\\SMB-万家\\smb-jypt-export.xlsx";
        new MyExcelDataListener().read(fileName, (list)->getInsertSQL("INSERT INTO tmp(a, b, c) VALUES", list, (excelData) -> String.format("('%s','%s'),", excelData.getColumn0(), excelData.getColumn1())));
        new MyExcelDataListener().read(fileName, (list)->getInsertSQL("INSERT INTO tmp_dd(a, b, c) VALUES", list,
                (excelData) -> String.format("('%s','%s'),", excelData.getColumn0(), excelData.getColumn1())));
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
        String moreStr = "('C1003766165','QY10013870','QY10018344','QY10046130','QY10021857','QY10015730','QY10019692','QY10021684','C1003583069','QY10137673','QY10018003','QY10009871','C1004088314','QY10009978','QY10018330','C1003471263','C1004901737','QY10018678','QY10021783','QY10018056','C1003473207','C1004023720','QY10014035','C1004675219','C1004318625','QY10020241','C1003325072','QY10021128','QY10015940','QY10014064','QY10013801','C1004092815','QY10015186','QY10020475','QY10014008','C1003389200','C1003549537','C1003476833','QY10016216','C1003583326','C1003707308','C1004054589','QY10013656','QY10014995','QY10022216','C1003501374','C1003853552','QY10021425','C1003410915','C1003387623','QY10021191','QY10014075','QY10022222','C1003351754','QY10015800','C1003884860','QY10012910','QY10009731','C1003553983','C1004260959','QY10017219','QY10009869','QY10011613','QY10022047','QY10017068','C1004236881','C1004910956','QY10019956','C1003408485','QY10011271','C1003955482','C1004362859','C1003936354','C1004839924','QY10020725','C1004053622','C1003963612','QY10019434','QY10018949','QY10012148','C1004300372','QY10014577','QY10011334','QY10018274','C1003338022','C1004257013','QY10015644','QY10018119','QY10020340','QY10021963','C1003882474','C1004730670','C1003332312','QY10028015','C1003488585','C1003355813','C1003560987','QY10017035','QY10009541','C1003411821','C1003991694','C1004582445','C1003948920','C1003409145','QY10021145','C1003423765','QY10014845','QY10011031','QY10019015','QY10015677','QY10009870','C1003878009','QY10015877','QY10014697','QY10014076','QY10019517','QY10020237','QY10016433','C1004382124','C1003966246','QY10016796','QY10017008','QY10017561','C1003942349','C1003544857','QY10012428','C1004637498','QY10014536','QY10010279','C1004914639','C1003497603','C1003369580','QY10011056','C1003561044','C1003977226','QY10009687','C1004396316','QY10009842','QY10016533','QY10016847','C1004785235','QY10010942','C1003583172','QY10020650','C1004611565','QY10021652','QY10010497','C1004355789','QY10011992','QY10019720','QY10023746','C1004855765','C1004021579','QY10017790','QY10010391','C1004847774','C1003987372','QY10011034','QY10017328','QY10020333','C1005082763','QY10010596','C1003582560','QY10016804','QY10052403','QY10042322','QY10028561','QY10028481','QY10017294','QY10017106','QY10016807','QY10016762','QY10013225','QY10012978','QY10012968','QY10011980','QY10011867','QY10011668','QY10011598','QY10011130','QY10010871','QY10010552','QY10010166','QY10009982','QY10009416','QY10008309','C1004947766','C1004944617','C1004927398','C1004907514','C1004902045','C1004468254','C1004447127','C1004401832','C1004398868','C1004396922','C1004380387','C1004379883','C1004351473','C1004337361','C1004289331','C1004265618','C1004256924','C1004254642','C1004247623','C1004116009','C1003559816','C1003549038','C1003526397','C1003523566','C1003504839','C1003499379','C1003488383','C1003453088','C1003424470','C1003402444','C1003386217','C1003368683','C1003361029','C1003342018')";
        String lessStr = "('QY10008309','QY10009416','QY10009541','QY10009687','QY10009731','QY10009871','QY10009869','QY10009870','QY10009978','QY10009982','QY10009842','QY10010166','QY10010279','QY10010391','QY10010552','QY10010596','QY10010497','QY10010871','QY10011056','QY10011031','QY10011034','QY10011271','QY10011334','QY10011130','QY10010942','QY10011668','QY10011613','QY10011598','QY10011867','QY10011992','QY10011980','QY10012148','QY10012428','QY10012910','QY10012968','QY10012978','QY10013225','QY10013656','QY10013801','QY10013870','QY10014008','QY10014075','QY10014035','QY10014076','QY10014064','QY10014577','QY10014845','QY10014697','QY10014536','QY10014995','QY10015186','QY10015644','QY10015730','QY10015677','QY10015940','QY10016216','QY10015877','QY10015800','QY10016433','QY10016533','QY10016804','QY10016807','QY10017035','QY10017008','QY10016847','QY10016796','QY10016762','QY10017106','QY10017068','QY10017219','QY10017294','QY10017328','QY10017561','QY10017790','QY10018056','QY10018119','QY10018003','QY10018344','QY10018330','QY10018274','QY10018678','QY10019015','QY10018949','QY10019434','QY10019517','QY10019720','QY10019692','QY10019956','QY10020340','QY10020241','QY10020333','QY10020475','QY10020237','QY10020650','QY10020725','QY10021145','QY10021128','QY10021191','QY10021783','QY10021684','QY10021652','QY10021425','QY10022047','QY10021857','QY10022222','QY10022216','QY10021963','QY10023746','QY10028015','QY10028561','QY10028481','QY10042322','QY10046130','QY10052403','QY10137673','C1003325072','C1003332312','C1003338022','C1003342018','C1003355813','C1003351754','C1003361029','C1003386217','C1003369580','C1003368683','C1003389200','C1003402444','C1003387623','C1003408485','C1003409145','C1003411821','C1003410915','C1003424470','C1003423765','C1003471263','C1003476833','C1003473207','C1003488383','C1003488585','C1003497603','C1003501374','C1003499379','C1003504839','C1003453088','C1003526397','C1003523566','C1003544857','C1003559816','C1003549038','C1003549537','C1003553983','C1003560987','C1003561044','C1003583326','C1003582560','C1003583069','C1003583172','C1003707308','C1003766165','C1003853552','C1003878009','C1003882474','C1003884860','C1003942349','C1003936354','C1003948920','C1003955482','C1003963612','C1003966246','C1003977226','C1003991694','C1003987372','C1004021579','C1004023720','C1004054589','C1004053622','C1004088314','C1004092815','C1004116009','C1004236881','C1004300372','C1004257013','C1004289331','C1004247623','C1004318625','C1004265618','C1004260959','C1004254642','C1004256924','C1004337361','C1004362859','C1004382124','C1004351473','C1004379883','C1004396922','C1004380387','C1004355789','C1004396316','C1004398868','C1004447127','C1004401832','C1004468254')";

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
        String more = "('workFlow','workFlowTempForm','workFlowDef','workFlowIns','workFlowLog','workFlowTempFormConfig','workFlowDefConfig','workFlowInsAudit','workFlowInsRecord','workFlowAuditLog','wordFlowOperLog','getGfsTempForm','pageGfsTempForm','postGfsTempForm','patchGfsTempForm','deleteGfsTempForm','getGfsFlowDef','pageGfsFlowDef','postGfsFlowDef','patchGfsFlowDef','deleteGfsFlowDef','getGfsFlowNodeDef','pageGfsFlowNodeDef','postGfsFlowNodeDef','patchGfsFlowNodeDef','deleteGfsFlowNodeDef','getGfsFlowInsAudit','pageGfsFlowInsAudit','postGfsFlowInsAudit','patchGfsFlowInsAudit','deleteGfsFlowInsAudit','getGfsFlowInsRecord','pageGfsFlowInsRecord','postGfsFlowInsRecord','patchGfsFlowInsRecord','deleteGfsFlowInsRecord','getGfsFlowAuditLog','pageGfsFlowAuditLog','postGfsFlowAuditLog','patchGfsFlowAuditLog','deleteGfsFlowAuditLog','getGfsFlowOperLog','pageGfsFlowOperLog','postGfsFlowOperLog','patchGfsFlowOperLog','deleteGfsFlowOperLog','getGfsAuthUser','pageGfsAuthUser','postGfsAuthUser','patchGfsAuthUser','deleteGfsAuthUser')";
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
        list.forEach(row -> sb.append(function.apply(row)));
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

}
