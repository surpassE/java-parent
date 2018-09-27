package com.sirding.javaeight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类jdk8+
 *
 * @author zc.ding
 * @create 2018/9/13
 */
public class DateUtils {
    public final static int DAY_TIME = 86400;
    public final static String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss SSS";
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String HH_MM_SS = "HH:mm:ss";
    public final static String HH_MM_SS_SSS = "HH:mm:ss SSS";
    
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        LocalDate now = LocalDate.now();
        System.out.println(localDate.until(now).getDays());
        String up = "上海自来水来自海上";
        String down = "山西运煤车煤运西山";
        System.out.println("========分隔符================");
        System.out.println(format());
        System.out.println(format(YYYY_MM_DD));
        System.out.println(format(new Date(), YYYY_MM_DD_HH_MM_SS_SSS));
        
    }
    
    /**
    *  当前时间默认格式  yyyy-MM-dd HH:mm:ss
    *  @return java.lang.String
    *  @Creation Date           ：2018/9/21
    *  @Author                  ：zc.ding@foxmail.com
    */
    public static String format(){
        return format(YYYY_MM_DD_HH_MM_SS);
    }
    
    /**
    *  获取当前时间指定格式字符串
    *  @param pattern
    *  @return java.lang.String
    *  @Creation Date           ：2018/9/21
    *  @Author                  ：zc.ding@foxmail.com
    */
    public static String format(String pattern){
        return DateTimeFormatter.ofPattern(pattern).format(LocalDateTime.now());
    }
    
    /**
    *  获取指定时间指定格式字符串
    *  @param date
    *  @param pattern
    *  @return java.lang.String
    *  @Creation Date           ：2018/9/21
    *  @Author                  ：zc.ding@foxmail.com
    */
    public static String format(Date date, String pattern){
        return DateTimeFormatter.ofPattern(pattern).format(date.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime());
    }
    
    /**
    *  将2000-01-01 00:00:00解析为Date类型
    *  @param date
    *  @return java.util.Date
    *  @Creation Date           ：2018/9/21
    *  @Author                  ：zc.ding@foxmail.com
    */
    public static Date parse(String date){
        return parse(date, YYYY_MM_DD_HH_MM_SS);
    }
    
    /**
    *  将时间转为指定pattern格式的Date数据
    *  @param date
    *  @param pattern
    *  @return java.util.Date
    *  @Creation Date           ：2018/9/21
    *  @Author                  ：zc.ding@foxmail.com
    */
    public static Date parse(String date, String pattern){
        return Date.from(LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern)).toInstant(ZoneOffset.UTC));
    }
    
    
    
    
}
