package com.sirding.easyexcel.enums;

/**
 * 企业规模枚举
 * https://cf.jd.com/pages/viewpage.action?pageId=231477977
 * @author zhangjun486
 * @version 1.0
 * @date 2020/1/6 16:12
 */
public enum CompanyScale {

    /**
     * 大型企业
     */
    LARGE("大型企业", "large"),
    /**
     * 中型企业
     */
    MEDIUM("中型企业", "medium"),
    /**
     * 微型企业
     */
    MICRO("微型企业", "micro"),
    /**
     * 小型企业
     */
    SMALL("小型企业","small");


    private String name;
    private String type;


    private CompanyScale(String name, String type) {
        this.name = name;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }


    public static CompanyScale getByType(String type){
        for(CompanyScale companyScale:values()){
            if(type.equals(companyScale.getType())) {
                return companyScale;
            }
        }
        return  null;
    }

}
