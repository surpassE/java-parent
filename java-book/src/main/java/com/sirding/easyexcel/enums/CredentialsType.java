package com.sirding.easyexcel.enums;

/**
 * 证件类型
 * @author zhangjun486
 * @version 1.0
 * @date 2020/1/6 17:05
 */
public enum CredentialsType {


    /**
     * 身份证
     */
    IDCARD("身份证(中华人民共和国居民身份证)","1"),
    /**
     * 护照
     */
    PASSPORT("护照","3"),
    /**
     * 回乡证(港澳居民来往内地通行证)
     */
    HXZ("港澳居民来往内地通行证","4"),
    /**
     * 台胞证(台湾居民来往大陆通行证)
     */
    TBZ("台湾居民来往大陆通行证","5");


    private String name;
    private String type;


    private CredentialsType(String name, String type) {
        this.name = name;
        this.type = type;
    }


    public static CredentialsType getByType(String type){
        for(CredentialsType credentialsType:values()){
            if(type.equals(credentialsType.getType())) {
                return credentialsType;
            }
        }
        return  null;
    }
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
