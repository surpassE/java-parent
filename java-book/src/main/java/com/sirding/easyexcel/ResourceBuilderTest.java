package com.sirding.easyexcel;

import com.sirding.easyexcel.enums.BusinessType;
import com.sirding.easyexcel.enums.CompanyScale;
import com.sirding.easyexcel.enums.CredentialsType;
import com.sirding.easyexcel.enums.Nation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResourceBuilderTest {

    @Test
    public void createResourceInfoSQL() {
        List<String> list = new ArrayList<>();
        list.add("查询产品概要信息#com.jd.jr.esu.config.center.jsf.ProductRpcServiceImpl#getProductOutline");
        list.add("查询客户产品概要信息#com.jd.jr.esu.config.center.jsf.ProductRpcServiceImpl#getClientProductOutline");
        list.add("添加客户产品信息#com.jd.jr.esu.config.center.jsf.ProductRpcServiceImpl#addClientProduct");

        list.add("查询应用配置#com.jd.jr.esu.config.center.jsf.AppConfigRpcServiceImpl#getAppConfig");
        list.add("查询应用配置集合#com.jd.jr.esu.config.center.jsf.AppConfigRpcServiceImpl#getAppConfigList");
        list.add("分页查询应用配置#com.jd.jr.esu.config.center.jsf.AppConfigRpcServiceImpl#getAppConfigPage");
        list.add("更新应用配置#com.jd.jr.esu.config.center.jsf.AppConfigRpcServiceImpl#updateAppConfig");
        list.add("查询客户产品信息#com.jd.jr.esu.config.center.jsf.AppConfigRpcServiceImpl#getClientInfoPage");
        list.add("添加或更新客户产品应用配置#com.jd.jr.esu.config.center.jsf.AppConfigRpcServiceImpl#insertOrUpdateClientInfo");
        list.add("查询产品应用配置#com.jd.jr.esu.config.center.jsf.AppConfigRpcServiceImpl#getProductInfoList");
        list.add("查询应用配置(条件列)#com.jd.jr.esu.config.center.jsf.AppConfigRpcServiceImpl#findAppConfigPage");
        list.add("查询客户产品应用配置(条件列)#com.jd.jr.esu.config.center.jsf.AppConfigRpcServiceImpl#findClientInfoPage");

        list.add("查询PIN用户关系#com.jd.jr.esu.config.center.jsf.UserRpcServiceImpl#queryUserLoginRel");
        list.add("PIN绑定用户#com.jd.jr.esu.config.center.jsf.UserRpcServiceImpl#bindUserLogin");
        list.add("PIN解绑用户#com.jd.jr.esu.config.center.jsf.UserRpcServiceImpl#unbindUserLogin");
        list.add("查询用户信息#com.jd.jr.esu.config.center.jsf.UserRpcServiceImpl#queryUserDetail");
        list.add("更新用户信息#com.jd.jr.esu.config.center.jsf.UserRpcServiceImpl#updateUserDetail");
        list.add("创建场景化用户#com.jd.jr.esu.config.center.jsf.UserRpcServiceImpl#createCjhUser");


        list.add("实名申请(KA-10)#com.jd.jr.esu.config.center.jsf.UserRpcServiceImpl#applyUserAuth");
        list.add("个人实名认证#com.jd.jr.esu.config.center.jsf.UserRpcServiceImpl#authenticatePersonUser");

        list.add("企业法人关系校验验证#com.jd.jr.esu.config.center.jsf.VerificationRpcServiceImpl#verifyEntLegalPerson");
        list.add("个人四要素验证#com.jd.jr.esu.config.center.jsf.VerificationRpcServiceImpl#verifyPerson4Element");
        list.add("查询企业法人关系#com.jd.jr.esu.config.center.jsf.VerificationRpcServiceImpl#queryEntLegalPersonRelation");

        StringBuilder sql = new StringBuilder("INSERT INTO inter_resource_info(resource_code,resource_name,method_name,clazz_name,clazz_alias,system_code) values ");
        for (String item : list) {
            String[] arr = item.split("#");
            String value = "('" + UUID.randomUUID().toString() + "',";
            value += "'" + arr[0] + "',";
            value += "'" + arr[2] + "',";
            value += "'" + arr[1] + "',";
            value += "'test','JYPT'),";
            sql.append(value);
        }

        System.out.println(sql);

    }

    @Test
    public void builderDict() {

        StringBuilder sql = new StringBuilder("INSERT INTO dict(code_no, code_name, code_key, code_desc, sort_no) VALUES");
        int i = 1;
        for (Nation value : Nation.values()) {
            sql.append("('").append(value.getType()).append("','");
            sql.append(value.getName()).append("','");
            sql.append("200150','民族', ").append(i++).append("),");
        }
        i = 1;
        for (BusinessType value : BusinessType.values()) {
            sql.append("('").append(value.getType()).append("','");
            sql.append(value.getName()).append("','");
            sql.append("200160','行业代码', ").append(i++).append("),");
        }

        i = 1;
        for (CompanyScale value : CompanyScale.values()) {
            sql.append("('").append(value.getType()).append("','");
            sql.append(value.getName()).append("','");
            sql.append("200170','企业规模', ").append(i++).append("),");
        }

        i = 1;
        for (CredentialsType value : CredentialsType.values()) {
            sql.append("('").append(value.getType()).append("','");
            sql.append(value.getName()).append("','");
            sql.append("200180','证件类型', ").append(i++).append("),");
        }

        System.out.println(sql.toString());

    }


}
