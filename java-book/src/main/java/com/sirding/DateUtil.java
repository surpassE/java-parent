package com.sirding;

/******
 * @author liusong
 * @Date 2014-10-5
 */
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.zip.CRC32;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Date Utility Class This is used to convert Strings to Dates and Timestamps
 * 
 * <p>
 * <a href="DateUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler </a> to correct time
 *         pattern. Minutes should be mm not MM (MM is month).
 * @version $Revision: 1.15 $ $Date: 2011/12/20 00:23:00 $
 */
public class DateUtil
{
    // ~ Static fields/initializers
    // =============================================

    private static Log    log                       = LogFactory.getLog( DateUtil.class );

    private static String defaultDatePattern        = null;

    private static String timePattern               = "HH:mm";

    private static String timePatterns              = "HH:mm:ss";

    private static String timePatterns2             = "HHmmss";
    private static String datePattern               = "yyyy-MM-dd";

    private static String datePatterns              = "yyyy-MM-dd HH:mm:ss";

    private static String dateTimePatterns          = "yyyy-MM-dd HH:mm:ss.SSS";
    // private static String dateTimePatterns =
    // ConstantsWhy.DATABASE_TYPE.trim()
    // .equalsIgnoreCase("mssql") ? "yyyy-MM-dd HH:mm:ss.SSS"
    // : (ConstantsWhy.DATABASE_TYPE.trim()
    // .equalsIgnoreCase("db2")?"yyyy-MM-dd HH:mm:ss.SSSSSS"
    // : "yyyy-MM-dd HH:mm:ss.SSSSSS");
    private static String keyDatePatter             = "yyyyMMddHHmmssSSS";

    private static String timeHourPattern           = "HH";

    private static String timeMinutePattern         = "mm";

    private static String timeSecondPattern         = "ss";

    private static String dateSqlPattern            = "yyyyMMdd";

    private static String dateNotDaySqlPattern      = "yyyyMM";

    private static String dateAndTimeSqlPattern     = "yyyyMMddHHmmss";

    private static String dateAndTimeHourSqlPattern = "yyyyMMddHH";

    private static String mondayDatePattern         = "EEE";

    private static String oracleDatePattern         = "yyyy-MM-dd hh24:mi:ss";

    public static String  betweenType_date          = "day";
    public static String  betweenType_hours         = "hour";
    public static String  betweenType_mintus        = "min";
    public static String  betweenType_second        = "second";

    // ~ Methods
    // ================================================================

    /**
     * Return default databaseDatePattern (yyyy-MM-dd || yyyy-MM-dd HH:MM:ss ||
     * yyyy-MM-dd hh24:mi:ss) if "isEndDate" is true,then dateStr is end date;
     * if "isEndDate" is false,then dateStr is not end date
     * 
     * @return a string representing the date pattern on the UI
     */
    public static String getDatabaseDatePattern(
            Object date, boolean isEndDate)
    {
        String reValue = "";
        DateFormat df = new SimpleDateFormat( datePatterns );
        if (date instanceof Timestamp)
        {
            reValue = df.format( date );
        } else if (date instanceof Date)
        {
            reValue = df.format( date );
        } else if (date instanceof java.sql.Date)
        {
            reValue = df.format( date );
        } else if (date instanceof String)
        {
            if (date != null)
            {
                reValue = String.valueOf( date );
            }
            if (reValue != null && (reValue.indexOf( ":" ) == -1))
            {
                if (isEndDate)
                {
                    reValue += " 23:59:59";
                } else
                {
                    reValue += " 00:00:00";
                }
            }
        }

        reValue = getDateAndTimeFromTimestamp( getTimeStampFromDate( reValue ) );
        // if(ConstantsWhy.DATABASE_TYPE.trim().equalsIgnoreCase("oracle")){
        // reValue=" to_Date(substr('" + reValue + "',0,19),'" +
        // oracleDatePattern + "') ";
        // }else {
        reValue = " '" + reValue + "' ";
        // }
        return reValue;
    }

    /**
     * Return default datePattern (yyyy/MM/dd)
     * 
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern()
    {
        Locale locale = LocaleContextHolder.getLocale();
        // try {
        // defaultDatePattern = ResourceBundle.getBundle(Constants.BUNDLE_KEY,
        // locale).getString("date.format");
        // } catch (MissingResourceException mse) {
        defaultDatePattern = "yyyy-MM-dd";
        // }

        return defaultDatePattern;
    }

    public static String getDateTimePattern()
    {
        return DateUtil.getDatePattern() + " HH:mm:ss.S";
    }

    /**
     * This method attempts to convert an Oracle-formatted date in the form
     * dd-MMM-yyyy to yyyy-MM-dd.
     * 
     * @param aDate
     *            date from database as a string
     * @return formatted string for the ui
     */
    public static final String getDate(
            Date aDate)
    {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null)
        {
            df = new SimpleDateFormat( getDatePattern() );
            returnValue = df.format( aDate );
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date/time in the
     * format you specify on input
     * 
     * @param aMask
     *            the date pattern the string is in
     * @param strDate
     *            a string representation of a date
     * @return a converted Date object
     * @see java.text.SimpleDateFormat
     * @throws ParseException
     */
    public static final Date convertStringToDate(
            String aMask, String strDate)
            throws ParseException
    {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat( aMask );

        if (log.isDebugEnabled())
        {
            log.debug( "converting '" + strDate + "' to date with mask '" + aMask + "'" );
        }

        try
        {
            date = df.parse( strDate );
        } catch (ParseException pe)
        {
            // log.error("ParseException: " + pe);
            throw new ParseException( pe.getMessage(), pe.getErrorOffset() );
        }

        return (date);
    }

    /**
     * This method generates a string representation of a date/time in the
     * format you specify on input
     * 
     * @param aMask
     *            the date pattern the string is in
     * @param strDate
     *            a string representation of a date
     * @return a converted Date object
     * @see java.text.SimpleDateFormat
     * @throws ParseException
     */
    public static final Date getDateFromString(
            String strDate)
            throws ParseException
    {
        SimpleDateFormat df = null;
        Date date = null;
        String aMask = "yyyy-MM-dd HH:mm:ss";
        df = new SimpleDateFormat( aMask );

        if (log.isDebugEnabled())
        {
            log.debug( "converting '" + strDate + "' to date with mask '" + aMask + "'" );
        }

        try
        {
            date = df.parse( strDate );
        } catch (ParseException pe)
        {
            // log.error("ParseException: " + pe);
            throw new ParseException( pe.getMessage(), pe.getErrorOffset() );
        }

        return (date);
    }

    public static String getLongStrFromDate(
            Date date)
    {

        return getDateTime( keyDatePatter, date );
    }

    /**
     * This method returns the current date time in the format: yyyy-MM-dd HH:MM
     * a
     * 
     * @param theTime
     *            the current time
     * @return the current date/time
     */
    public static String getTimeNow(
            Date theTime)
    {
        return getDateTime( timePattern, theTime );
    }

    /**
     * This method returns the current date in the format: yyyy-MM-dd
     * 
     * @return the current date
     * @throws ParseException
     */
    public static Calendar getToday()
            throws ParseException
    {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat( getDatePattern() );

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format( today );
        Calendar cal = new GregorianCalendar();
        cal.setTime( convertStringToDate( todayAsString ) );

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     * 
     * @param aMask
     *            the date pattern the string is in
     * @param aDate
     *            a date object
     * @return a formatted string representation of the date
     * 
     * @see java.text.SimpleDateFormat
     */
    public static final String getDateTime(
            String aMask, Date aDate)
    {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null)
        {
            if (log.isDebugEnabled())
            {
                log.error( "aDate is null!" );
            }
        } else
        {
            df = new SimpleDateFormat( aMask );
            returnValue = df.format( aDate );
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date based on the
     * System Property 'dateFormat' in the format you specify on input
     * 
     * @param aDate
     *            A date to convert
     * @return a string representation of the date
     */
    public static final String convertDateToString(
            Date aDate)
    {
        return getDateTime( getDatePattern(), aDate );
    }

    public static final String convertDateToStr(
            Date aDate)
    {
        return getDateTime( datePatterns, aDate );
    }

    /**
     * 
     * @方法名: getDaysBetween
     * @功能描述：
     * @param（参数）：
     * @param startDate
     * @param endDate
     * @return
     * @返回值类型: int
     * @参考方法 计算开始日期和结束日期之间的间隔天数 日期格式为yyyy-mm-dd
     * @exception 异常处理类
     * @开发人：hx
     * @开发日期：Dec 3, 200911:04:54 AM
     */
    public static String invertDate(
            Date date)
    {
        SimpleDateFormat smf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String udate = smf.format( date ).replace( ".000", "" );
        return udate;
    }

    /**
     * This method converts a String to a date using the datePattern
     * 
     * @param strDate
     *            the date to convert (in format yyyy-MM-dd)
     * @return a date object
     * 
     * @throws ParseException
     */
    public static Date convertStringToDate(
            String strDate)
            throws ParseException
    {
        Date aDate = null;

        try
        {
            if (log.isDebugEnabled())
            {
                log.debug( "converting date with pattern: " + getDatePattern() );
            }

            aDate = convertStringToDate( getDatePattern(), strDate );
        } catch (ParseException pe)
        {
            log.error( "Could not convert '" + strDate + "' to a date, throwing exception" );
            pe.printStackTrace();
            throw new ParseException( pe.getMessage(), pe.getErrorOffset() );

        }

        return aDate;
    }

    /**
     * 
     * 方法名: getYearToString 功能描述：取得当前年 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9上午11:36:07
     */
    public static String getYearToString()
    {
        Date date = new Date();
        String year = "1900";
        SimpleDateFormat format = new SimpleDateFormat( "yyyy" );
        setLoaclTimeZone( format );
        year = format.format( date );
        return year;
    }

    public static int getYear()
    {
        Date date = new Date();
        int year = 1900;
        SimpleDateFormat format = new SimpleDateFormat( "yyyy" );
        setLoaclTimeZone( format );
        year = Integer.valueOf( format.format( date ) );
        return year;
    }

    /**
     * 
     * 方法名: getMonthToString 功能描述：取得当前月 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9上午11:37:50
     */
    public static String getMonthToString()
    {
        Date date = new Date();
        String month = "01";
        SimpleDateFormat format = new SimpleDateFormat( "MM" );
        setLoaclTimeZone( format );
        month = format.format( date );
        return month;
    }

    public static int getMonth()
    {
        Date date = new Date();
        int month = 1;
        SimpleDateFormat format = new SimpleDateFormat( "MM" );
        setLoaclTimeZone( format );
        month = Integer.valueOf( format.format( date ) );
        return month;
    }

    /**
     * 
     * 方法名: getDayToString 功能描述：取得当前天 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9上午11:39:05
     */
    public static String getDayToString()
    {
        Date date = new Date();
        String day = "01";
        SimpleDateFormat format = new SimpleDateFormat( "dd" );
        setLoaclTimeZone( format );
        day = format.format( date );
        return day;
    }

    public static int getDay()
    {
        Date date = new Date();
        int day = 01;
        SimpleDateFormat format = new SimpleDateFormat( "dd" );
        setLoaclTimeZone( format );
        day = Integer.valueOf( format.format( date ) );
        return day;
    }

    /**
     * 
     * 方法名: getHourToString 功能描述：取得小时 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午01:17:04
     */
    public static String getHourToString()
    {
        Date date = new Date();
        String hour = "8";
        SimpleDateFormat format = new SimpleDateFormat( timeHourPattern );
        setLoaclTimeZone( format );
        hour = format.format( date );
        return hour;
    }

    /**
     * 
     * 方法名: getMinuteToString 功能描述：取得分钟 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午01:18:57
     */
    public static String getMinuteToString()
    {
        Date date = new Date();
        String minute = "8";
        SimpleDateFormat format = new SimpleDateFormat( timeMinutePattern );
        setLoaclTimeZone( format );
        minute = format.format( date );
        return minute;
    }

    /**
     * 
     * 方法名: getSecondToString 功能描述：取得秒 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午01:19:44
     */
    public static String getSecondToString()
    {
        Date date = new Date();
        String second = "8";
        SimpleDateFormat format = new SimpleDateFormat( timeSecondPattern );
        setLoaclTimeZone( format );
        second = format.format( date );
        return second;
    }

    /**
     * 
     * 方法名: getSysTime 功能描述：取得系统时间,精确到分 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9上午11:49:36
     */
    public static String getSysTime()
    {
        Date date = new Date();
        return getDateTime( timePattern, date );

    }

    /**
     * 
     * 方法名: getKeySysTime 功能描述：获取系统时间精确到毫秒,主键生成用 参数: 返回值类型: String 返回值意义:
     * 开发人：zhl 开发日期：2007-5-16上午11:46:44
     */
    public static String getKeySysTime()
    {
        Date date = new Date();
        //		String randomGlobalID = UUID.randomUUID().toString();
        //		log.info("globalID = "+randomGlobalID);
        //		CRC32 crc32 = new CRC32();
        //		crc32.update(randomGlobalID.getBytes());
        //		long globalNumberID=crc32.getValue();
        //		String strGlobalID = String.valueOf(globalNumberID);
        //		log.info("crc32 number ID = "+strGlobalID);
        String order_time = getDateTime( keyDatePatter, date );
        //String order_number = order_time + strGlobalID;
        String order_number = order_time;
        return order_number;

    }

    /**
     * 
     * 方法名: getSysTimes 功能描述：取得系统时间，精确到秒 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9上午11:53:11
     */
    public static String getSysTimes()
    {
        Date date = new Date();
        return getDateTime( timePatterns, date );

    }

    public static int getTime(
            Date date)
    {
        return Integer.parseInt( getDateTime( timePatterns2, date ) );

    }

    /**
     * 
     * 方法名: getSysDate 功能描述：取得系统日期 没有时间 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午12:01:05
     */
    public static String getSysDate()
    {
        Date date = new Date();
        String sysdate = "1900-1-1";
        SimpleDateFormat format = new SimpleDateFormat( datePattern );
        setLoaclTimeZone( format );
        sysdate = format.format( date );
        return sysdate;
    }

    /**
     * 
     * 方法名: getSysDateAndTime 功能描述：取得系统时间 精确到秒 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午01:13:12
     */
    public static String getSysDateAndTime()
    {
        Date date = new Date();
        String sysdatetime = "1900-1-1 8:00:00 ";
        SimpleDateFormat format = new SimpleDateFormat( dateTimePatterns );

        // setDateFormat(format);
        setLoaclTimeZone( format );
        sysdatetime = format.format( date );
        return sysdatetime;
    }

    /**
     * 取得系统当前日期开始时间 精确到秒
     * 
     * @return 格式： yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String getSystemDateToDefTime(
            boolean isEnd)
    {
        Date date = new Date();
        String sysdatetime = "1900-1-1 0:00:00 ";
        SimpleDateFormat format = new SimpleDateFormat( datePattern );
        setLoaclTimeZone( format );
        String tempDatetime = format.format( date );
        if (isEnd)
        {
            sysdatetime = getDateAndTimeFromTimestamp( getTimeStampFromDate( tempDatetime + " 23:59:59.000" ) );
        } else
        {
            sysdatetime = getDateAndTimeFromTimestamp( getTimeStampFromDate( tempDatetime ) );
        }
        return sysdatetime;
    }

    /**
     * 由于JDK升级1.6 字符串转Timestamp类型时，要求字符串必须是两位的月和日，不足两位的需要补位 例如：2012-1-5
     * 必须转换为2012-01-05 才能转为Timestamp类型
     * 
     * 根据传入的日期(格式：yyyy-MM-dd)，返回开始\结束日期和时间 返回格式yyyy-MM-dd HH:mm:ss.SSS 参数 date
     * :格式 yyyy-MM-dd isEnd :格式 true : 结束时间 false: 开始时间
     * 
     * @return 返回格式yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String getDateToDefDateTime(
            String date, boolean isEnd)
    {

        String datetime = "";
        datetime = getDateAndTimeFromTimestamp( getTimeStampFromDate( date ) );
        if (isEnd)
        {
            String endDate = getDateFromTimeStamp( getTimeStampFromDate( date ) );
            datetime = getDateAndTimeFromTimestamp( getTimeStampFromDate( endDate + " 23:59:59.000" ) );
        }
        return datetime;
    }

    // getDateAndTimeFromTimestamp(getTimeStampFromDate(dateStr))

    /**
     * 
     * 方法名: getYearInString 功能描述：从字符串中获取年份 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午01:45:55
     */
    public static String getYearInString(
            String str)
    {
        String year = "1900";
        int i = 0;
        if (str != null)
        {
            i = str.substring( 0, 5 ).indexOf( "-" );
            year = str.substring( 0, i );
        }
        return year;
    }

    public static int getYearInt(
            String str)
    {
        return Integer.parseInt( getYearInString( str ) );
    }

    /**
     * 
     * 方法名: getMonthInString 功能描述：从字符串中获取月份 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午01:48:24
     */
    public static String getMonthInString(
            String str)
    {
        String month = "01";
        int i = 0;
        if (str != null)
        {
            i = str.substring( 5, str.length() ).indexOf( "-" );
            month = str.substring( 5, 5 + i );
        }
        return month;
    }

    public static int getMonthInt(
            String str)
    {
        return Integer.parseInt( getMonthInString( str ) );
    }

    /**
     * 
     * 方法名: getDayInString 功能描述：从字符串中获取天 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午02:13:06
     */
    public static String getDayInString(
            String str)
    {
        String day = "01";
        int i = 0;
        if (str != null)
        {
            i = str.substring( 5, str.length() ).indexOf( "-" );
            if (str.length() > 5 + i + 3)
            {
                day = str.trim().substring( 5 + i + 1, 5 + i + 3 );
            } else
            {
                day = str.trim().substring( 5 + i + 1, str.length() );
            }
        }
        return day.trim();
    }

    public static int getDayInt(
            String str)
    {
        return Integer.parseInt( getDayInString( str ) );
    }

    /**
     * 
     * 方法名: getDateToSql 功能描述：格式化日期 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午02:22:36
     */
    public static String getDateToSql()
    {
        Date date = new Date();
        String datesql = "19000101";
        SimpleDateFormat format = new SimpleDateFormat( dateSqlPattern );
        setLoaclTimeZone( format );
        datesql = format.format( date );
        return datesql;
    }

    /**
     * 
     * 方法名: getDateToSqlHour 功能描述：格式化日期 参数: 返回值类型: String 返回值意义: 开发人：zhoubo
     * 开发日期：2016年8月11日
     */
    public static String getDateToSqlHour()
    {
        Date date = new Date();
        String datesql = "1900010101";
        SimpleDateFormat format = new SimpleDateFormat( dateAndTimeHourSqlPattern );
        setLoaclTimeZone( format );
        datesql = format.format( date );
        return datesql;
    }

    /**
     * 
     * 方法名: getDateToSqlNotDay 功能描述：格式化日期，没有天 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午02:32:36
     */
    public static String getDateToSqlNotDay()
    {
        Date date = new Date();
        String datesql = "190001";
        SimpleDateFormat format = new SimpleDateFormat( dateNotDaySqlPattern );
        setLoaclTimeZone( format );
        datesql = format.format( date );
        return datesql;
    }

    /**
     * 
     * 方法名: getDateAndTimeSql 功能描述：格式化日期时间 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午02:54:53
     */
    public static String getDateAndTimeSql()
    {
        Date date = new Date();
        String datesql = "19000101120000";
        SimpleDateFormat format = new SimpleDateFormat( dateAndTimeSqlPattern );
        setLoaclTimeZone( format );
        datesql = format.format( date );
        return datesql;
    }

    /**
     * 
     * 方法名: getMonday 功能描述：取得系统日期的周几 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午04:22:06
     */
    public static String getMonday()
    {
        Date date = new Date();
        String monday = "星期一";
        SimpleDateFormat format = new SimpleDateFormat( mondayDatePattern );
        setLoaclTimeZone( format );
        monday = format.format( date );
        return monday;
    }

    /**
     * 
     * 方法名: getDayInDate 功能描述：取得指定日期的周几 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午06:55:32
     */
    public static String getDayInDate(
            String strDate)
    {
        Date date;
        int i = 1;
        Calendar dar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat( datePattern );
        try
        {
            date = format.parse( strDate );
            dar.setTime( date );
        } catch (ParseException e)
        {
            date = new Date();
            dar.setTime( date );
        }
        i = dar.get( Calendar.DAY_OF_WEEK ) - 1;
        String[] day =
        { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        return day[ i ];
    }

    /**
     * 
     * 方法名: getMonthEnd 功能描述：取得该月份的天数 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午04:59:33
     */
    public static String getMonthEnd(
            String strDate)
    {
        String day = "1900";
        int year = 1900;
        int month = 1;
        if (strDate != null)
        {
            year = Integer.parseInt( DateUtil.getYearInString( strDate ) );
            month = Integer.parseInt( DateUtil.getMonthInString( strDate ) );
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                day = "31";
            if (month == 4 || month == 6 || month == 9 || month == 11)
                day = "30";
            if (month == 2)
            {
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
                    day = "29";
                else
                    day = "28";
            }
        }
        return day;
    }

    /**
     * 
     * 方法名: getCDatePattern 功能描述：将日期转换成年月日格式 参数: 返回值类型: String 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午06:39:40
     */
    public static String getCDatePattern(
            String strDate)
    {
        String curDate = "1900年1月1日";
        if (strDate != null)
        {
            String year = DateUtil.getYearInString( strDate );
            String month = DateUtil.getMonthInString( strDate );
            String day = DateUtil.getDayInString( strDate );
            curDate = year + "年" + month + "月" + day + "日";
        }
        return curDate;
    }

    /**
     * 
     * 方法名: getCDatePattern 功能描述：将日期转换只取成年月格式 参数: 返回值类型: String 返回值意义: 开发人：wqt
     * 开发日期：2007-5-9下午06:39:40
     */
    public static String getCDatePatternYearAndMonth(
            String strDate)
    {
        String curDate = "1900年1月";
        if (strDate != null)
        {
            String year = DateUtil.getYearInString( strDate );
            String month = DateUtil.getMonthInString( strDate );
            curDate = year + "年" + month + "月";
        }
        return curDate;
    }

    /**
     * 
     * 方法名: isBigDate 功能描述：比较两个时间先后 参数: 返回值类型: boolean 返回值意义: 开发人：zhl
     * 开发日期：2007-5-9下午07:32:07
     */
    public static boolean isBigDate(
            String startDate, String endDate)
    {
        Date first_date = new Date();
        Date second_date = new Date();
        SimpleDateFormat format = new SimpleDateFormat( datePattern );
        try
        {
            first_date = format.parse( startDate );
            second_date = format.parse( endDate );
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return second_date.after( first_date );
    }

    /**
     * 
     * 方法名: getCurrentDate 功能描述： 参数:
     * 
     * @param startDate
     *            目标日期 参数:
     * @param flag
     *            目标（年，月，日）（year,month,day） 参数:
     * @param add
     *            增量 参数:
     * @param forward
     *            true 增加, false 减少 参数:
     * @return 返回值类型: String 返回值意义: 开发人：zhl 开发日期：2007-5-17上午09:21:08
     * @throws ParseException
     */
    public static String getCurrentDate(
            String startDate, String flag, int add, boolean forward)
            throws ParseException
    {
        if (!(flag.equals( "year" ) || flag.equals( "YEAR" ) || flag.equals( "month" ) || flag.equals( "MONTH" ) || flag.equals( "day" ) || flag.equals( "DAY" )))
        {
            return null;
        }
        String s = "";
        SimpleDateFormat format = new SimpleDateFormat( datePattern );
        Calendar dar = Calendar.getInstance();
        dar.setTime( convertStringToDate( datePattern, startDate ) );
        if (forward)
        {
            if (flag.equals( "year" ) || flag.equals( "YEAR" ))
            {
                dar.add( Calendar.YEAR, add );
            } else if (flag.equals( "month" ) || flag.equals( "MONTH" ))
            {
                dar.add( Calendar.MONTH, add );
            } else if (flag.equals( "day" ) || flag.equals( "DAY" ))
            {
                dar.add( Calendar.DATE, add );
            }
        } else
        {
            if (flag.equals( "year" ) || flag.equals( "YEAR" ))
            {
                dar.add( Calendar.YEAR, -add );
            } else if (flag.equals( "month" ) || flag.equals( "MONTH" ))
            {
                dar.add( Calendar.MONTH, -add );
            } else if (flag.equals( "day" ) || flag.equals( "DAY" ))
            {
                dar.add( Calendar.DATE, -add );
            }
        }
        s = format.format( dar.getTime() );
        return s;

    }

    /**
     * 
     * 方法名: getInterval 功能描述：获取两个时间相隔天数 参数: 返回值类型: int 规则：startDate > endDate
     * 返回值为正 返回值意义: 开发人：zhl 开发日期：2007-5-10下午01:43:27
     */
    public static int getInterval(
            String startDate, String endDate)
    {
        Date start = new Date();
        Date end = new Date();
        Calendar dar1 = Calendar.getInstance();
        Calendar dar2 = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat( datePattern );
        int inter = 0;
        if (startDate != null && endDate != null)
        {
            try
            {
                start = format.parse( startDate );
                end = format.parse( endDate );
                dar1.setTime( start );
                dar2.setTime( end );
                while (dar1.after( dar2 ))
                {
                    dar2.add( Calendar.DATE, 1 );
                    inter++;
                }
                while (dar2.after( dar1 ))
                {
                    dar1.add( Calendar.DATE, 1 );
                    inter--;
                }
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return inter;
    }

    /**
     * 
     * 方法名: getInterval 功能描述：获取两个时间相隔天数 参数: 返回值类型: int 规则：startDate > endDate
     * 返回值为正 返回值意义: 开发人：zhl 开发日期：2007-5-10下午01:43:27
     */
    public static int getInterval(
            Date startDate, Date endDate)
    {

        Calendar dar1 = Calendar.getInstance();
        Calendar dar2 = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat( datePattern );
        int inter = 0;
        if (startDate != null && endDate != null)
        {
            try
            {

                dar1.setTime( startDate );
                dar2.setTime( endDate );
                while (dar1.after( dar2 ))
                {
                    dar2.add( Calendar.DATE, 1 );
                    inter++;
                }
                while (dar2.after( dar1 ))
                {
                    dar1.add( Calendar.DATE, 1 );
                    inter--;
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return inter;
    }

    /**
     * 
     * 方法名: getInterval 功能描述：获取两个时间相隔月数 参数: 返回值类型: int 规则：startDate > endDate
     * 返回值为正 返回值意义: 开发人：zhl 开发日期：2007-5-10下午01:43:27
     */
    public static int getMonthQuantity(
            String startDate, String endDate)
    {
        Date start = new Date();
        Date end = new Date();
        Calendar dar1 = Calendar.getInstance();
        Calendar dar2 = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat( datePattern );
        int inter = 0;
        if (startDate != null && endDate != null)
        {
            try
            {
                start = format.parse( startDate );
                end = format.parse( endDate );
                dar1.setTime( start );
                dar2.setTime( end );
                while (dar1.after( dar2 ))
                {
                    dar2.add( Calendar.MONTH, 1 );
                    inter++;
                }
                while (dar2.after( dar1 ))
                {
                    dar1.add( Calendar.MONTH, 1 );
                    inter--;
                }
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return inter;
    }

    /**
     * 
     * 方法名: getDateAddOnMonth 功能描述：在月份上增加一个月 参数:
     * 
     * @param date
     *            参数:
     * @return 返回值类型: String 返回值意义: 开发人：zhl 开发日期：2007-5-24上午11:56:03
     * @throws ParseException
     */
    public static String getDateAddOnMonth(
            String date)
            throws ParseException
    {
        return getCurrentDate( date, "month", 1, true );
    }

    public static String getDateAddThreeMonth(
            String date)
            throws ParseException
    {
        return getCurrentDate( date, "month", 3, true );
    }

    public static String getDateAddMonth(
            String date, int month)
            throws ParseException
    {
        return getCurrentDate( date, "month", month, true );
    }

    /**
     * 
     * 方法名: getDateDownOnMonth 功能描述：在月份上减少一个月 参数:
     * 
     * @param date
     *            参数:
     * @return 返回值类型: String 返回值意义: 开发人：zhl 开发日期：2007-5-24下午12:05:42
     * @throws ParseException
     */
    public static String getDateDownOnMonth(
            String date)
            throws ParseException
    {
        return getCurrentDate( date, "month", 1, false );
    }

    public static String getDateDownOnMonth(
            String date, int month)
            throws ParseException
    {
        return getCurrentDate( date, "month", month, false );
    }

    /**
     * 
     * 方法名: getDateFromTimeStamp 功能描述：将yyyy-MM-dd HH:mm:ss
     * 格式的日期转成yyyy-MM-dd格式的日期字符串 参数:
     * 
     * @param timeStamp
     *            参数:
     * @return 返回值类型: String 返回值意义: 开发人：lei_chi 开发日期：2007-5-28下午06:53:59
     */
    public static String getDateFromTimeStamp(
            Timestamp timeStamp)
    {
        SimpleDateFormat format = new SimpleDateFormat( datePattern );
        String date = "1900-01-01";
        if (timeStamp != null && !timeStamp.equals( "" ))
        {

            date = (format.format( timeStamp ));

        }
        return date;
    }

    public static String getDateFromTimeStamp(
            java.util.Date timeStamp)
    {
        SimpleDateFormat format = new SimpleDateFormat( datePattern );
        String date = "1900-01-01";
        if (timeStamp != null && !timeStamp.equals( "" ))
        {

            date = (format.format( timeStamp ));

        }
        return date;
    }

    /**
     * 
     * 方法名: getTimeStampFromDate 功能描述：将yyyy-MM-dd或yyyy-MM-dd
     * HH:mm:ss.SSSSSS格式的日期字符串转换成yyyy-MM-dd HH:mm:ss格式的日期 参数:
     * 
     * @param date
     *            参数:
     * @return 返回值类型: Timestamp 返回值意义: 开发人：lei_chi 开发日期：2007-5-28下午06:58:45
     */
    public static Timestamp getTimeStampFromDate(
            String sDate)
    {
        Timestamp timestamp = Timestamp.valueOf( "1900-01-01 00:00:00.000" );
        SimpleDateFormat format = null;
        try
        {
            if (sDate != null && !sDate.equals( "" ))
            {
                if (sDate.indexOf( ":" ) < 0)
                {
                    format = new SimpleDateFormat( datePattern );
                } else
                {
                    if (sDate.indexOf( "." ) < 0)
                    {
                        sDate = sDate + ".0";
                    }
                    format = new SimpleDateFormat( dateTimePatterns );
                }
                Date date = format.parse( sDate );
                String dateString = getDateTime( dateTimePatterns, date );
                timestamp = Timestamp.valueOf( dateString );
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return timestamp;
    }

    /**
     * 
     * 方法名: formatSQLdate 功能描述：客户端传来的时间格式，日期和时间之间总是两个或多个空格，此方法为去掉之间的多余空格，只保留一个
     * 参数:
     * 
     * @param date
     *            参数:
     * @return 返回值类型: String 返回值意义: 开发人：zhenyu_wang 开发日期：2007-10-16上午10:22:54
     */
    public static String formatSQLdate(
            String date)
    {
        String returnDate = "1900-01-01 00:00:00.000";
        if (date != null && !date.equals( "" ))
        {
            int flag = 1;
            while (flag == 1)
            {
                int m = date.indexOf( " " );
                char n = date.charAt( m + 1 );
                if (n == ' ')
                {
                    date = date.replace( "  ", " " );
                } else
                {
                    flag = 2;
                }
            }
            returnDate = date;
        }
        return returnDate;
    }

    /**
     * 
     * 方法名: getYearList 功能描述：获取年份类别 参数:
     * 
     * @param startYear
     *            参数:开始年份
     * @param endYear
     *            参数:截止年份
     * @return 返回值类型: List 返回值意义:年份集合列表 开发人：lei_chi 开发日期：2007-5-29下午08:21:55
     */
    public static List getYearList(
            int startYear, int endYear)
    {
        List<Integer> yearList = new ArrayList<Integer>();
        if (startYear == 0)
        {
            startYear = 1900;
        }
        if (endYear == 0 || endYear > 3000)
        {
            endYear = 3000;
        }
        for (; startYear <= endYear; startYear++)
        {
            yearList.add( startYear );
        }
        return yearList;
    }

    /**
     * 
     * 方法名: getMonthList 功能描述：获得12个月的列表 参数:
     * 
     * @return 返回值类型: List 返回值意义:12个月的集合 开发人：lei_chi 开发日期：2007-5-29下午08:24:45
     */
    public static List getMonthList()
    {
        List<Integer> monthList = new ArrayList<Integer>();
        for (int i = 1; i <= 12; i++)
        {
            monthList.add( i );
        }
        return monthList;
    }

    /**
     * 方法名:getSeasonList 功能描述: 获得4个季度的列表
     * 
     * @return List 返回值意义：4个季度的集合 开发人：szq 时间：2010-04-09
     */
    public static List getSeasonList()
    {
        List<Integer> seasonList = new ArrayList<Integer>();
        for (int i = 1; i <= 4; i++)
        {
            seasonList.add( i );
        }
        return seasonList;
    }

    /**
     * 
     * 方法名: getSystemTime 功能描述：取得系统时间格式为 Timestamp 参数:
     * 
     * @return 返回值类型: Timestamp 返回值意义: 开发人：zhl 开发日期：2007-7-3上午09:48:37
     */
    public static Timestamp getSystemTime()
    {
        return Timestamp.valueOf( getSysDateAndTime() );

    }

    /**
     * 
     * 方法名: getSysHour 功能描述：根据当前系统时间取得当前时间的小时，为保持小时和分一致，需要传入当前时间字符串 参数:
     * 
     * @param currentTime
     *            当前系统时间的字符串
     * @return 返回值类型: int 小时 返回值意义: 开发人：yyd 开发日期：2007-10-11下午05:46:41
     */
    public static int getSysHour(
            String currentTime)
    {

        return new Integer( currentTime.substring( 0, currentTime.indexOf( ":" ) ) ).intValue();
    }

    /**
     * 
     * 方法名: getSysMinute 功能描述：根据当前系统时间取得当前时间的分，为保持小时和分一致，需要传入当前时间字符串 参数:
     * 
     * @param currentTime
     *            当前时间字符串
     * @return 返回值类型: int 分 返回值意义: 开发人：yyd 开发日期：2007-10-11下午05:48:06
     */
    public static int getSysMinute(
            String currentTime)
    {

        return new Integer( currentTime.substring( currentTime.indexOf( ":" ) + 1 ) ).intValue();
    }

    /**
     * 
     * @方法名: convertDateToTimestamp
     * @功能描述：将java.util.Date转换为java.sql.Timestamp,时间格式为yyyy-MM-dd 
     *                                                            HH:mm:ss.SSSSSS
     * @param（参数）：
     * @param date
     * @return
     * @返回值类型: Timestamp
     * @参考方法
     * @exception 异常处理类
     * @开发人：yyd
     * @开发日期：Nov 11, 20086:24:00 PM
     */
    public static Timestamp convertDateToTimestamp(
            Date date)
    {

        String dateString = getDateTime( dateTimePatterns, date );

        if (dateString.equals( "" ))
        {
            return Timestamp.valueOf( "1900-01-01 00:00:00.000000" );
        } else
        {
            return Timestamp.valueOf( dateString );
        }
    }

    /**
     * @方法名: convertDateToTimestamp
     * @功能描述：将java.sql.Timestamp,时间格式为yyyy-MM-dd HH:mm:ss
     * @param（参数）：
     * @param timestamp
     * @return
     * @返回值类型: String
     * @参考方法
     * @exception 异常处理类
     * @开发人：yyd
     * @开发日期：Fre 02, 2009 6:24:00 PM
     */
    public static String getDateAndTimeFromTimestamp(
            Timestamp timestamp)
    {
        SimpleDateFormat format = new SimpleDateFormat( dateTimePatterns );
        return format.format( timestamp );
    }

    /**
     * @方法名: convertDateToTimestamp
     * @功能描述：将java.sql.Timestamp,时间格式为yyyy-MM-dd HH:mm:ss
     * @param（参数）：
     * @param timestamp
     * @return
     * @返回值类型: String
     * @参考方法
     * @exception 异常处理类
     * @开发人：yyd
     * @开发日期：Fre 02, 2009 6:24:00 PM
     */
    public static String getDateAndTimeFromTimestamp(
            Date timestamp)
    {
        SimpleDateFormat format = new SimpleDateFormat( dateTimePatterns );
        return format.format( timestamp );
    }

    private static void setLoaclTimeZone(
            SimpleDateFormat format)
    {
        TimeZone timeZoneChina = TimeZone.getTimeZone( "Asia/Shanghai" );// 获取时区
        // dateFormatterChina.setTimeZone(timeZoneChina);//设置系统时区
        format.setTimeZone( timeZoneChina );
    }

    // 日期转化为大小写 2009-05-25 转化为二零零九年五月二十五日
    public static String dataToUpper(
            Date date)
    {
        Calendar ca = Calendar.getInstance();
        ca.setTime( date );
        int year = ca.get( Calendar.YEAR );
        int month = ca.get( Calendar.MONTH ) + 1;
        int day = ca.get( Calendar.DAY_OF_MONTH );
        return numToUpper( year ) + "年" + monthToUppder( month ) + "月" + dayToUppder( day ) + "日";
    }

    // 将数字转化为大写
    public static String numToUpper(
            int num)
    {
        // String u[] = {"零","壹","贰","叁","肆","伍","录","柒","捌","玖"};
        String u[] =
        { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        char[] str = String.valueOf( num ).toCharArray();
        String rstr = "";
        for (int i = 0; i < str.length; i++)
        {
            rstr = rstr + u[ Integer.parseInt( str[ i ] + "" ) ];
        }
        return rstr;
    }

    // 月转化为大写
    public static String monthToUppder(
            int month)
    {
        if (month < 10)
        {
            return numToUpper( month );
        } else if (month == 10)
        {
            return "十";
        } else
        {
            return "十" + numToUpper( month - 10 );
        }
    }

    // 日转化为大写
    public static String dayToUppder(
            int day)
    {
        if (day < 20)
        {
            return monthToUppder( day );
        } else
        {
            char[] str = String.valueOf( day ).toCharArray();
            if (str[ 1 ] == '0')
            {
                return numToUpper( Integer.parseInt( str[ 0 ] + "" ) ) + "十";
            } else
            {
                return numToUpper( Integer.parseInt( str[ 0 ] + "" ) ) + "十" + numToUpper( Integer.parseInt( str[ 1 ] + "" ) );
            }
        }
    }

    /**
     * 
     * @方法名: getDatePatternStr
     * @功能描述：
     * @param（参数）：
     * @param date
     * @return
     * @返回值类型: String
     * @参考方法
     * @exception 异常处理类
     * @开发人：Oliver
     * @开发日期：Sep 2, 200910:26:08 AM
     */
    public static String getDatePatternStr(
            Date date)
    {
        String datePatternStr = "19000101";
        SimpleDateFormat format = new SimpleDateFormat( dateSqlPattern );
        setLoaclTimeZone( format );
        datePatternStr = format.format( date );
        return datePatternStr;
    }

    /**
     * 
     * @方法名: getLastDayInfo
     * @功能描述：根据给定的日期计算前一天的日期
     * @param（参数）：
     * @param nowDate
     * @return
     * @返回值类型: String
     * @参考方法
     * @exception 异常处理类
     * @开发人：hx
     * @开发日期：Oct 13, 200911:28:07 AM
     */
    public static String getLastDayInfo(
            String nowDate)
    {
        String yesterday = "";
        int year = 0;
        int month = 0;
        int day = 0;

        try
        {

            year = Integer.parseInt( nowDate.substring( 0, nowDate.indexOf( "-" ) ) );
            month = Integer.parseInt( nowDate.substring( nowDate.indexOf( "-" ) + 1, nowDate.lastIndexOf( "-" ) ) );
            day = Integer.parseInt( nowDate.substring( nowDate.lastIndexOf( "-" ) + 1 ) );
            day = day - 1;
            if (day == 0)
            {
                month = month - 1;
                if (month == 0)
                {
                    month = 12;
                    day = 31;
                    year = year - 1;
                } else
                {
                    switch (month)
                    {
                    case 1:
                        day = 31;
                        break;
                    case 3:
                        day = 31;
                        break;
                    case 5:
                        day = 31;
                        break;
                    case 7:
                        day = 31;
                        break;
                    case 8:
                        day = 31;
                        break;
                    case 10:
                        day = 31;
                        break;
                    case 12:
                        day = 31;
                        break;
                    case 4:
                        day = 30;
                        break;
                    case 6:
                        day = 30;
                        break;
                    case 9:
                        day = 30;
                        break;
                    case 11:
                        day = 30;
                        break;
                    case 2:
                        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
                        {
                            day = 29;
                        } else
                        {
                            day = 28;
                        }
                    }
                }
            }
            String monthStr = "";
            String dayStr = "";
            if (month < 10)
            {
                monthStr = "0" + String.valueOf( month );
            } else
            {
                monthStr = String.valueOf( month );
            }
            if (day < 10)
            {
                dayStr = "0" + String.valueOf( day );
            } else
            {
                dayStr = String.valueOf( day );
            }
            yesterday = String.valueOf( year ) + "-" + monthStr + "-" + dayStr;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return yesterday;
    }

    /**
     * 
     * @方法名: getDaysBetween
     * @功能描述：
     * @param（参数）：
     * @param startDate
     * @param endDate
     * @return
     * @返回值类型: int
     * @参考方法 计算开始日期和结束日期之间的间隔天数 日期格式为yyyy-mm-dd
     * @exception 异常处理类
     * @开发人：hx
     * @开发日期：Dec 3, 200911:04:54 AM
     */
    public static int getDaysBetween(
            java.util.Calendar startDate, java.util.Calendar endDate)
    {
        if (startDate.after( endDate ))
        {
            java.util.Calendar swap = startDate;
            startDate = endDate;
            endDate = swap;
        }
        int days = endDate.get( java.util.Calendar.DAY_OF_YEAR ) - startDate.get( java.util.Calendar.DAY_OF_YEAR );
        int year = endDate.get( java.util.Calendar.YEAR );
        if (startDate.get( java.util.Calendar.YEAR ) != year)
        {
            startDate = (java.util.Calendar) startDate.clone();
            do
            {
                days += startDate.getActualMaximum( java.util.Calendar.DAY_OF_YEAR );
                startDate.add( java.util.Calendar.YEAR, 1 );
            } while (startDate.get( java.util.Calendar.YEAR ) != year);
        }
        return days;
    }

    public static long getHoursBetween(
            Date t1, Date t2)
    {
        long bet = t2.getTime() - t1.getTime();
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        return bet / nd * 24 + bet % nd / nh;
    }

    /**
     * 
     * @方法名: calculateDate
     * @功能描述：根据日期，输入要计算的天数，得到想要的日期 日期格式为yyyy-mm-dd
     * @param（参数）：
     * @param date
     * @param day
     * @return
     * @返回值类型: String
     * @参考方法
     * @exception 异常处理类
     * @开发人：hx
     * @开发日期：Dec 9, 20098:11:15 AM
     */
    public static String calculateDate(
            String date, int dayNum)
    {
        String year = date.substring( 0, 4 );
        String month = date.substring( 5, 7 );
        String day = date.substring( 8, 10 );
        String hour = date.substring( 11, 13 );
        String minute = date.substring( 14, 16 );
        String second = date.substring( 17, 19 );
        GregorianCalendar gc1 = new GregorianCalendar( Integer.valueOf( year ), Integer.valueOf( month ) - 1, Integer.valueOf( day ) + dayNum, Integer.valueOf( hour ), Integer.valueOf( minute ), Integer.valueOf( second ) );
        Date dates = gc1.getTime();
        SimpleDateFormat time = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String factDate = time.format( dates );
        return factDate;
    }

    public static String getCTimestampPattern(
            Timestamp timeStamp)
    {
        SimpleDateFormat format = new SimpleDateFormat( datePattern );
        String strDate = "1900-01-01";
        if (timeStamp != null && !timeStamp.equals( "" ))
        {

            strDate = (format.format( timeStamp ));

        }

        String curDate = "1900年1月1日";
        if (strDate != null)
        {
            String year = DateUtil.getYearInString( strDate );
            String month = DateUtil.getMonthInString( strDate );
            String day = DateUtil.getDayInString( strDate );
            curDate = year + "年" + month + "月" + day + "日";
        }
        return curDate;
    }

    public static String getETimestampPattern(
            Timestamp timeStamp)
    {
        SimpleDateFormat format = new SimpleDateFormat( datePattern );
        String strDate = "1900-01-01";
        if (timeStamp != null && !timeStamp.equals( "" ))
        {

            strDate = (format.format( timeStamp ));

        }

        return strDate;
    }

    public static String getDatePattern(
            Date date)
    {
        String datePatternStr = datePattern;
        SimpleDateFormat format = new SimpleDateFormat( dateSqlPattern );
        setLoaclTimeZone( format );
        datePatternStr = format.format( date );
        return datePatternStr;
    }

    public static String CheckDateAndTime(
            String aMask, String strDate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat( aMask );
        SimpleDateFormat sdf1 = new SimpleDateFormat( dateTimePatterns );
        Date date = null;

        if (log.isDebugEnabled())
        {
            log.debug( "converting '" + strDate + "' to date with mask '" + aMask + "'" );
        }

        try
        {
            date = sdf.parse( strDate );
            return sdf1.format( date );
        } catch (ParseException pe)
        {
            // log.error("ParseException: " + pe);
            return getSysDateAndTime();
        }
    }

    public static final String convertDateTime(
            Date aDate)
    {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null)
        {
            log.error( "aDate is null!" );
        } else
        {
            df = new SimpleDateFormat( dateTimePatterns );
            returnValue = df.format( aDate );
        }

        return (returnValue);
    }

    /**
     * 
     * @方法名：getMonthLastDay
     * @开发人：zbp
     * @开发时间：2011-06-16 16:47:22
     * @功能描述: 按给定的年月计算本月最后一天，返回本月的最后一天
     * @param(传入参数)：
     * @param year
     *            年
     * @param month
     *            月
     * @return
     * @返回类型：int 本月最后一天
     * @参考方法：
     */
    public static int getMonthLastDay(
            int year, int month)
    {
        int day = -1;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
            day = 31;
        if (month == 4 || month == 6 || month == 9 || month == 11)
            day = 30;
        if (month == 2)
        {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                day = 29;
            else
                day = 28;
        }
        return day;

    }

    /**
     * 
     * @方法名：getMonthLastDay
     * @开发人：zbp
     * @开发时间：2011-06-16 16:47:22
     * @功能描述: 按给定的日期计算本月最后一天的日期，返回日期的最后一天日期格式
     * @param(传入参数)：
     * @param aDate
     *            格式：yyyy-MM-dd
     * @return
     * @throws ParseException
     * @返回类型：int 本月最后一天日期格式yyyy-MM-dd
     * @参考方法：getMonthLastDay
     */
    public static String getMonthLastDay(
            String aDate)
            throws ParseException
    {
        String endDate;
        int year = Integer.parseInt( aDate.substring( 0, aDate.indexOf( "-" ) ) );
        int month = Integer.parseInt( aDate.substring( aDate.indexOf( "-" ) + 1, aDate.lastIndexOf( "-" ) ) );
        int day = getMonthLastDay( year, month );
        endDate = year + "-" + month + "-" + day;
        SimpleDateFormat format = new SimpleDateFormat( datePattern );
        Date bDate = format.parse( endDate );
        String s = format.format( bDate );
        return s;
    }

    public static int getDaysBetween(
            String beginDate, String endDate)
            throws ParseException
    {
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        Date bDate = format.parse( beginDate );
        Date eDate = format.parse( endDate );
        Calendar d1 = new GregorianCalendar();
        d1.setTime( bDate );
        Calendar d2 = new GregorianCalendar();
        d2.setTime( eDate );
        long diff = d1.getTimeInMillis() - d2.getTimeInMillis();
        int days = Integer.parseInt( String.valueOf( diff / (1000 * 60 * 60 * 24) ) );
        // Integer.parseInt(String.valueOf(diff/(1000*60*60*24)));
        // int y2 = d2.get(Calendar.YEAR);
        // if (d1.get(Calendar.YEAR) != y2) {
        // d1 = (Calendar) d1.clone();
        // do {
        // days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数
        // d1.add(Calendar.YEAR, 1);
        // } while (d1.get(Calendar.YEAR) != y2);
        // }
        return days;
    }

    /** 获取两个时间间隔 */
    public static String getTwoDay(
            String sj1, String sj2)
    {
        SimpleDateFormat myFormatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        long day = 0;
        try
        {
            java.util.Date date = myFormatter.parse( sj1 );
            java.util.Date mydate = myFormatter.parse( sj2 );
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e)
        {
            return "";
        }

        return day + "";
    }

    // 比较两个时间大小及间隔的天数
    /**
     * 得到几天前的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(
            Date d, int day)
    {
        Calendar now = Calendar.getInstance();
        now.setTime( d );
        now.set( Calendar.DATE, now.get( Calendar.DATE ) - day );
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static String getDateAfter(
            String startTime, int day)
    {
        String endTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Date dateStart;
        try
        {
            dateStart = sdf.parse( startTime );
            Calendar now = Calendar.getInstance();
            now.setTime( dateStart );
            now.set( Calendar.DATE, now.get( Calendar.DATE ) + day );
            endTime = sdf.format( now.getTime() );

        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return endTime;

    }

    /*
     * java 比较时间大小 Java中文网:http://www.javaweb.cc/
     */

    public static Integer getDateCompareTo(
            String startTime, String EndTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try
        {
            c1.setTime( sdf.parse( startTime ) );
            c2.setTime( sdf.parse( EndTime ) );
        } catch (java.text.ParseException e)
        {
            System.err.println( "格式不正确" );
        }
        int result = c1.compareTo( c2 );
        return result;

    }

    /**
     * 
     * @方法名: addMonth
     * @功能描述：将传入时间的月份加一，当月份超过12时，年份加一。
     * @param（参数）：参数格式“yyyy-mm-dd”
     * @param date
     * @return
     * @返回值类型: String
     * @参考方法
     * @exception 异常处理类
     * @开发人：lyb
     * @开发日期：Nov 22, 2011 8:55:02 AM
     */
    public static String addMonth(
            String date)
    {
        try
        {
            String[] dates = date.split( "-" );
            int year = Integer.valueOf( dates[ 0 ] );
            int month = dates[ 1 ].startsWith( "0" ) ? Integer.valueOf( dates[ 1 ].substring( 1 ) ) : Integer.valueOf( dates[ 1 ] );
            month++;
            if (month > 12)
            {
                year++;
                month = month - 12;
            }
            date = year + "-" + month + "-" + dates[ 2 ];
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 
     * @方法名: addMonth
     * @功能描述：将传入时间的月份加n，当月份超过12时，年份加一。
     * @param（参数）：
     * @param date
     *            格式“yyyy-mm-dd”
     * @param n
     * @return
     * @返回值类型: String
     * @参考方法
     * @exception 异常处理类
     * @开发人：lyb
     * @开发日期：Dec 19, 2011 11:54:13 AM
     */
    public static String addMonth(
            String date, int n)
    {
        try
        {
            int year = Integer.valueOf( date.split( "-" )[ 0 ] );
            int month = Integer.valueOf( date.split( "-" )[ 1 ] );
            int day = Integer.valueOf( date.split( "-" )[ 2 ] );
            Calendar cc = new GregorianCalendar( year, month - 1, day );
            cc.add( Calendar.MONTH, n );
            return cc.get( Calendar.YEAR ) + "-" + (cc.get( Calendar.MONTH ) + 1) + "-" + cc.get( Calendar.DAY_OF_MONTH );
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 
     * @方法名: addDay
     * @功能描述：将传入时间的日加n，当月份日超过当月最大天数自动月数加一，当月份加一超出12年份加一。
     * @param（参数）：
     * @param date
     *            格式“yyyy-mm-dd”
     * @param n
     * @return
     * @返回值类型: String
     * @参考方法
     * @exception 异常处理类
     * @开发人：zxf
     * @开发日期：Jan 31, 2012 16:24:13 PM
     */
    public static String addDay(
            String date, int n)
    {
        try
        {
            int year = Integer.valueOf( date.split( "-" )[ 0 ] );
            int month = Integer.valueOf( date.split( "-" )[ 1 ] );
            int day = Integer.valueOf( date.split( "-" )[ 2 ] );
            Calendar cc = new GregorianCalendar( year, month - 1, day );
            cc.add( Calendar.DATE, n );
            return convertDateToString(cc.getTime());
            //return cc.get( Calendar.YEAR ) + "-" + (cc.get( Calendar.MONTH ) + 1) + "-" + cc.get( Calendar.DAY_OF_MONTH );
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDateTimePatterns()
    {
        return dateTimePatterns;
    }

    /**
     * 
     * 方法名: getCurrentDate 功能描述： 参数:
     * 
     * @param startDate
     *            目标日期 参数:
     * @param flag
     *            目标（年，月，日）（year,month,day） 参数:
     * @param add
     *            增量 参数:
     * @param forward
     *            true 增加, false 减少 参数:
     * @return 返回值类型: String 返回值意义: 开发人：zhl 开发日期：2007-5-17上午09:21:08
     * @throws ParseException
     */
    public static String getCurrentDate(
            String startDate, String flag, int add, boolean forward, String dateType)
            throws ParseException
    {
        if (!(flag.equals( "year" ) || flag.equals( "YEAR" ) || flag.equals( "month" ) || flag.equals( "MONTH" ) || flag.equals( "day" ) || flag.equals( "DAY" )))
        {
            return null;
        }
        String s = "";
        SimpleDateFormat format = new SimpleDateFormat( dateType );
        Calendar dar = Calendar.getInstance();
        dar.setTime( convertStringToDate( datePattern, startDate ) );
        if (forward)
        {
            if (flag.equals( "year" ) || flag.equals( "YEAR" ))
            {
                dar.add( Calendar.YEAR, add );
            } else if (flag.equals( "month" ) || flag.equals( "MONTH" ))
            {
                dar.add( Calendar.MONTH, add );
            } else if (flag.equals( "day" ) || flag.equals( "DAY" ))
            {
                dar.add( Calendar.DATE, add );
            }
        } else
        {
            if (flag.equals( "year" ) || flag.equals( "YEAR" ))
            {
                dar.add( Calendar.YEAR, -add );
            } else if (flag.equals( "month" ) || flag.equals( "MONTH" ))
            {
                dar.add( Calendar.MONTH, -add );
            } else if (flag.equals( "day" ) || flag.equals( "DAY" ))
            {
                dar.add( Calendar.DATE, -add );
            }
        }
        s = format.format( dar.getTime() );
        return s;

    }

    /**
     * 
     * @方法名: getCompareDate
     * @功能描述：根据field，将targetDate对应的年或月或日加remindDay
     * @param（参数）：
     * @param targetDate
     * @param remindDay
     * @param field
     * @return
     * @返回值类型: String
     * @参考方法
     * @exception 异常处理类
     * @开发人：lyb
     * @开发日期：Nov 30, 2011 11:22:35 AM
     */
    public static String getCompareDate(
            String targetDate, int remindDay, int field)
    {
        String[] targetDates = targetDate.split( "-" );
        Calendar cc = new GregorianCalendar( Integer.valueOf( targetDates[ 0 ] ), Integer.valueOf( targetDates[ 1 ] ), Integer.valueOf( targetDates[ 2 ] ) );
        switch (field)
        {
        case 1:
            cc.add( Calendar.YEAR, remindDay );
            break;
        case 2:
            cc.add( Calendar.MONTH, remindDay );
            break;
        case 3:
            cc.add( Calendar.DAY_OF_MONTH, remindDay );
            break;
        default:
            break;
        }

        return cc.get( Calendar.YEAR ) + "-" + (cc.get( Calendar.MONTH ) + 1) + "-" + cc.get( Calendar.DAY_OF_MONTH );
    }

    /**
     * 
     * @方法名: getQuarterInt
     * @功能描述：根据传入的时间，返回当前时间所在的季度
     * @param（参数）：
     * @param targetDate
     * @return
     * @返回值类型: int
     * @参考方法
     * @开发人：zbp
     * @开发日期：Nov 30, 2011 11:22:35 AM
     */
    public static int getQuarterInt(
            String targetDate)
    {
        int month = getMonthInt( targetDate );
        int quarter = 1;
        if (month % 3 != 0)
        {
            quarter = (month / 3) + 1;
        } else
        {
            quarter = (month / 3);
        }
        return quarter;
    }

    /**
     * 
     * @方法名: getQuarterInt
     * @功能描述：根据传入的时间，返回当前时间所在周的开始时间 星期一为本周开始时间
     * @param（参数）：
     * @param targetDate
     *            (格式：yyyy-MM-dd)
     * @return
     * @throws Exception
     * @返回值类型: int
     * @参考方法
     * @开发人：zbp
     * @开发日期：Nov 30, 2011 11:22:35 AM
     */
    public static String getWeekBeginDate(
            String targetDate)
            throws Exception
    {
        Calendar dar1 = Calendar.getInstance();
        Date date = convertStringToDate( "yyyy-MM-dd", targetDate );
        dar1.setTime( date );
        // 取得当前日期在本周的所在天
        // dar1.setMinimalDaysInFirstWeek(dar1.MONDAY);
        int dayOrweek = dar1.get( Calendar.DAY_OF_WEEK ) - 2;
        if (dayOrweek < 0)
            dayOrweek = 6;
        // System.out.println(dar1.get(dar1.DAY_OF_WEEK));
        String retrunDate = getCurrentDate( targetDate, "day", dayOrweek, false, "yyyy-MM-dd" );
        return retrunDate;
    }

    /**
     * 
     * @方法名: getQuarterInt
     * @功能描述：根据传入的时间，返回当前时间所在周的结束时间 星期日为本周开始时间
     * @param（参数）：
     * @param targetDate
     *            (格式：yyyy-MM-dd)
     * @return
     * @throws Exception
     * @返回值类型: int
     * @参考方法
     * @开发人：zbp
     * @开发日期：Nov 30, 2011 11:22:35 AM
     */
    public static String getWeekEndDate(
            String targetDate)
            throws Exception
    {
        Calendar dar1 = Calendar.getInstance();
        Date date = convertStringToDate( "yyyy-MM-dd", targetDate );
        dar1.setTime( date );
        // 取得当前日期在本周的所在天
        // dar1.setMinimalDaysInFirstWeek(dar1.MONDAY);
        int dayOrweek = dar1.get( Calendar.DAY_OF_WEEK ) - 1;
        dayOrweek = 7 - dayOrweek;
        // System.out.println(dar1.get(dar1.DAY_OF_WEEK));
        String retrunDate = getCurrentDate( targetDate, "day", dayOrweek, true, "yyyy-MM-dd" );
        return retrunDate;
    }

    /**
     * 
     * @方法名: getDate
     * @功能描述：传入年、月、日，将其拼成日期(格式：yyyy-MM-dd)
     * @param（参数）：
     * @param targetDate
     *            (格式：yyyy-MM-dd)
     * @return
     * @throws Exception
     * @返回值类型: String
     * @参考方法
     * @开发人：wqt
     * @开发日期：Nov 30, 2011 11:22:35 AM
     */
    public static String getDate(
            int year, int month, int day)
    {
        if (year > 0 && year < 1900)
        {
            year = year + 1900;
        }
        Calendar calle = new GregorianCalendar();
        calle.set( Calendar.YEAR, year );
        calle.set( Calendar.MONTH, 0 );
        calle.set( Calendar.DAY_OF_MONTH, 0 );

        calle.add( Calendar.MONTH, month - 1 );
        calle.add( Calendar.DAY_OF_MONTH, day );

        return calle.get( Calendar.YEAR ) + "-" + (calle.get( Calendar.MONTH ) + 1) + "-" + calle.get( Calendar.DAY_OF_MONTH );
    }

    public static void main(
            String[] args)
    {
        // Date dataNow = new Date();
        // Calendar calenda = Calendar.getInstance();
        // calenda.setTime(dataNow);
        // calenda.add(Calendar.DAY_OF_MONTH, 1);
        // dataNow = calenda.getTime();
        // System.out.println(calenda.get(Calendar.YEAR)+ "-" +
        // (calenda.get(Calendar.MONTH)+1) + "-"+
        // calenda.get(Calendar.DAY_OF_MONTH));

        // Date loanEndDate = new Date();
        // loanEndDate.setYear(2012-1900);
        // loanEndDate.setMonth(12-1);
        // loanEndDate.setDate(14);
        // try {
        // System.out.println(Math.abs(DateUtil.getDaysBetween(DateUtil.getSysDate(),
        // DateUtil.convertDateToString(loanEndDate))));
        // System.out.println(getDaysBetween(new Date(), new
        // SimpleDateFormat("yyyy-MM-dd").parse("2014-11-30")));
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // String s = "123";
        // System.out.println(s.substring(0,2));
        System.out.println( "==============" );
        //		System.out.println(getmillinBetweenToday());

    }

    /** 获取两个时间间隔 秒 */
    public static Long getSecond(
            Date date, Date mydate)
    {
        SimpleDateFormat myFormatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        long second = 0;
        try
        {
            // java.util.Date mydate = myFormatter.parse(sj2);
            second = (date.getTime() - mydate.getTime()) / 1000;
        } catch (Exception e)
        {
            return null;
        }

        return second;
    }

    public static int getDaysBetween(
            Date startDate, Date endDate)
    {
        return getDaysBetween( DateUtils.toCalendar( startDate ), DateUtils.toCalendar( endDate ) );
    }

    /**
     * @desc 时间转化字符串
     * @param Date
     *            date
     * @return String yyyy-MM-dd
     */
    public static String dateToString(
            Date date)
    {
        SimpleDateFormat simp = new SimpleDateFormat( datePattern );
        String sDate = simp.format( date );
        return sDate;
    }

    public static Date addDay(
            Date currDate, long day, boolean flag)
    {
        long currTime = currDate != null ? currDate.getTime() : System.currentTimeMillis();
        long time = currTime + (flag ? day : -day) * 24 * 60 * 60 * 1000;
        return new Date( time );
    }

    /**
     * @Description 功能：根据传入参数格式将Date类型转化为String类型
     * @param aDate
     * @param Pattern
     *            格式 如：yyy-MM-dd 或 yy/MM/dd（不包括时分秒）
     * @return
     */
    public static final String getDateByPattern(
            Date aDate, String Pattern)
    {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null)
        {
            df = new SimpleDateFormat( Pattern );
            returnValue = df.format( aDate );
        }

        return (returnValue);
    }

    /**
     * 
     * @方法名: addMonth
     * @功能描述：将传入时间的月份加n，当月份超过12时，年份加一。
     * @param（参数）：
     * @param date
     *            格式“yyyy-mm-dd”
     * @param n
     * @return
     * @返回值类型: String
     * @参考方法
     * @exception 异常处理类
     * @开发人：lyb
     * @开发日期：Dec 19, 2011 11:54:13 AM
     */
    public static Date addMonth(
            Date date, int n)
    {
        try
        {
            String oldDate = getDateFromTimeStamp( date );
            int year = Integer.valueOf( oldDate.split( "-" )[ 0 ] );
            int month = Integer.valueOf( oldDate.split( "-" )[ 1 ] );
            int day = Integer.valueOf( oldDate.split( "-" )[ 2 ] );
            Calendar cc = new GregorianCalendar( year, month - 1, day );
            cc.add( Calendar.MONTH, n );
            String newDate = cc.get( Calendar.YEAR ) + "-" + (cc.get( Calendar.MONTH ) + 1) + "-" + cc.get( Calendar.DAY_OF_MONTH );
            return convertStringToDate( newDate );
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取当前时间距离今天0点的时间差
     * 
     * @return
     */
    public static long getmillinBetweenToday()
    {
        long second = 0;
        try
        {
            long now = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
            String time = sdf.format( new Date() );
            time = time + "235959";
            SimpleDateFormat sdf2 = new SimpleDateFormat( "yyyyMMddhhmmss" );
            long end = sdf2.parse( time ).getTime();
            second = end - now;
        } catch (ParseException e)
        {
            e.printStackTrace();
            return 0;
        }
        return second;
    }

    /**
     * @功能描述：字符串类型转为日期。
     * @param（参数）：
     * @param date
     *            格式“yyyy-MM-dd HH:mm:ss”
     * @开发人：xuchao
     * @开发日期：2015-08-28
     */
    public static Date strToDate(
            String dateStr)
    {
        Date date = null;
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat( datePatterns );
            date = dateFormat.parse( dateStr );
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @功能描述：字符串类型转为日期。
     * @param（参数）：
     * @param date
     *            格式“yyyy-MM-dd HH:mm:ss”
     * @throws ParseException
     * @开发人：mzx
     * @开发日期：2016-08-24
     */
    public static Date strToDateNoCatch(
            String dateStr)
            throws ParseException
    {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat( datePatterns );
        date = dateFormat.parse( dateStr );
        return date;
    }

    /**
     * @功能描述：日期转为字符串。
     * @param（参数）：
     * @param date
     * @开发人：xuchao
     * @开发日期：2015-09-01
     */
    public static String DateToStr(
            Date date)
    {
        String dateStr = "";
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat( datePatterns );
            dateStr = dateFormat.format( date );
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * @功能描述：获取每个月最后一天最晚时间点 如果参数为null 默认当前日期
     * @param（参数）：date
     * @param date
     * @开发人：liudh
     * @开发日期：2016-03-18
     */
    public static Timestamp getLastDayOfMonth(
            Date date)
    {
        SimpleDateFormat df = new SimpleDateFormat( datePattern );
        Calendar calendar = Calendar.getInstance();
        if (date != null)
        {
            calendar.setTime( date );
            calendar.set( Calendar.DAY_OF_MONTH, calendar.getActualMaximum( Calendar.DAY_OF_MONTH ) );
        } else
        {
            calendar.set( Calendar.DAY_OF_MONTH, calendar.getActualMaximum( Calendar.DAY_OF_MONTH ) );
        }
        String dayFormat = df.format( calendar.getTime() );
        Timestamp ts = Timestamp.valueOf( dayFormat + " 23:59:59" );
        return ts;

    }

    /**
     * @功能描述：获取每个月第一天零点，如果参数为null 默认当前日期
     * @param（参数）：date
     * @开发人：liudh
     * @开发日期：2016-03-18
     */
    public static Timestamp getFirstDayOfMonth(
            Date date)
    {
        SimpleDateFormat df = new SimpleDateFormat( datePattern );
        Calendar calendar = Calendar.getInstance();
        if (date != null)
        {
            calendar.setTime( date );
            calendar.set( Calendar.DAY_OF_MONTH, calendar.getActualMinimum( Calendar.DAY_OF_MONTH ) );
        } else
        {
            calendar.set( Calendar.DAY_OF_MONTH, calendar.getActualMinimum( Calendar.DAY_OF_MONTH ) );
        }
        String dayFormat = df.format( calendar.getTime() );
        Timestamp ts = Timestamp.valueOf( dayFormat + " 00:00:00" );
        return ts;
    }

    /**
     * @功能描述：获取每天最晚时间点 如果参数为null 默认当前日期
     * @param（参数）：date
     * @param date
     * @开发人：liudh
     * @开发日期：2016-03-18
     */
    public static Timestamp getLastTimeOfDay(
            Date date)
    {
        SimpleDateFormat df = new SimpleDateFormat( datePattern );
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        String dayFormat = df.format( calendar.getTime() );
        Timestamp ts = Timestamp.valueOf( dayFormat + " 23:59:59" );
        return ts;

    }

    /**
     * @功能描述：获取每天最晚时间点 如果参数为null 默认当前日期
     * @param（参数）：date
     * @param date
     * @开发人：liudh
     * @开发日期：2016-03-18
     */
    public static Timestamp getLastTimeOfDay()
    {
        return getLastTimeOfDay( new Date() );

    }

    /**
     * 
     * @Description: 判断日期是否是今天
     * @param @param date
     * @param @return
     * @return boolean
     * @throws
     * @author xuhuiliu@hongkun.com.cn 刘旭辉
     * @date 2016年8月31日
     */
    public static boolean isToday(
            Date date)
    {
        long startToday = getFirstTimeOfDay().getTime();
        long endToday = getLastTimeOfDay().getTime();
        long nowTime = date.getTime();
        if (nowTime >= startToday && nowTime <= endToday)
        {
            return true;
        }
        return false;
    }

    /**
     * @功能描述：获取每天零点，如果参数为null 默认当前日期
     * @param（参数）：date
     * @param date
     * @开发人：liudh
     * @开发日期：2016-03-18
     */
    public static Timestamp getFirstTimeOfDay(
            Date date)
    {
        SimpleDateFormat df = new SimpleDateFormat( datePattern );
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        String dayFormat = df.format( calendar.getTime() );
        Timestamp ts = Timestamp.valueOf( dayFormat + " 00:00:00" );
        return ts;
    }

    /**
     * @功能描述：获取每天零点 默认当前日期
     * @param date
     * @开发人：liudh
     * @开发日期：2016-03-18
     */
    public static Timestamp getFirstTimeOfDay()
    {
        return getFirstTimeOfDay( new Date() );
    }

    /*
     * 毫秒转化
     */
    public static String formatTime(
            long ms)
    {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strMinute + " 分钟 " + strSecond + " 秒";
    }

    /**
     * 解析日期
     * */
    public static Date parseDate(
            String time, String suffix)
    {
        SimpleDateFormat TIMEFORMAT = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Date tempDate = null;
        if (!"".equals( time ))
        {
            try
            {
                if (!"".equals( suffix ))
                {
                    tempDate = TIMEFORMAT.parse( time + " " + suffix );
                } else
                {
                    tempDate = TIMEFORMAT.parse( time );
                }
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return tempDate;
    }

    //计算时不包括时分秒的值
    public static long getDayBetween(
            Date t1, Date t2, String type)
    {
        if (t1 != null && t2 != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
            String lastString = sdf.format( t1 );
            String nowString = sdf.format( t2 );
            try
            {
                t1 = sdf.parse( lastString );
                t2 = sdf.parse( nowString );
            } catch (ParseException e)
            {
                e.printStackTrace();
            }

            long bet = t2.getTime() - t1.getTime();
            long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
            long nh = 1000 * 60 * 60;//一小时的毫秒数
            long nm = 1000 * 60;//一分钟的毫秒数
            if (betweenType_date.equals( type ))
            {
                return bet / nd;
            } else if (betweenType_hours.equals( type ))
            {
                return bet / nd * 24 + bet % nd / nh;
            } else if (betweenType_mintus.equals( type ))
            {
                return bet / nm;
            } else if (betweenType_second.equals( type ))
            {
                return bet / 1000;
            }
        }
        return 0;
    }
    public static String getBetweenTime(Date t1, Date t2) {  

        long day = 0;  
        long hour = 0;  
        long min = 0;  
        long sec = 0;  
        long time1 = t1.getTime();  
        long time2 = t2.getTime();  
        long diff ;  
        if(time1<time2) {  
            diff = time2 - time1;  
        } else {  
            diff = time1 - time2;  
        }  
        day = diff / (24 * 60 * 60 * 1000);  
        hour = (diff / (60 * 60 * 1000) - day * 24);  
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
        sec = (diff/1000-day*24*60*60-hour*60*60-min*60);  
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";  
    }  
}