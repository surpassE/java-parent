package com.sirding;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * fast-json格式数据与对象转换工具
 * @author zc.ding
 * @date 2016年10月16日
 *
 */
public class JsonUtil {

	/**
	 * 
	 * @param clazz
	 * @param data
	 * @return
	 */

		private static SerializerFeature[] features = {SerializerFeature.WriteClassName, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteNullListAsEmpty, SerializerFeature.DisableCircularReferenceDetect};
		@SuppressWarnings("deprecation")
		protected static SerializerFeature[] features2 = {
			SerializerFeature.QuoteFieldNames, SerializerFeature.UseSingleQuotes, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteEnumUsingToString,
			SerializerFeature.UseISO8601DateFormat, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty,
			SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.SkipTransientField, SerializerFeature.SortField, SerializerFeature.WriteClassName,
			SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteSlashAsSpecial, SerializerFeature.BrowserCompatible, SerializerFeature.WriteDateUseDateFormat,
			SerializerFeature.NotWriteRootClassName, SerializerFeature.DisableCheckSpecialChar, SerializerFeature.BeanToArray, SerializerFeature.WriteNonStringKeyAsString
		};

		/**
		 * 过滤对象中单个属性字段
		 * @param obj
		 * @param clazz
		 * @param arr
		 * @return
		 */
		public static String objectToJson(Object obj, Class<?> clazz, String... arr){
			SimplePropertyPreFilter spp = new SimplePropertyPreFilter(clazz, arr);
			String data = JSON.toJSONString(obj, spp, features);
			showMsg(data);
			return data;
		}
		
		/**
		 * 
		 * @param data
		 * @return
		 */
		public static Object jsonToObject(String data){
			return JSONObject.parse(data);
		}
		
		public static List<?> jsonToCollection(String data, Class<?> clazz){
			return JSONArray.parseArray(data, clazz);
		}
		
		
		private static void showMsg(Object obj){
//			logger.debug("########################## alibaba fastJson ####################");
//			logger.debug(obj);
//			logger.debug("########################## alibaba fastJson ####################");
		}
		
		
		
	
		 /**
		 * demo
		 * @param obj
		 * @return
		 */
		/**
	public String objectToJsonByFastjson(Object obj){
		String data = "";
		//alibaba fastJson
		Map<Class<?>, String[]> map = new HashMap<Class<?>, String[]>();
		map.put(AgentUserAgent.class, new String[]{"agentUserInfo","agentInfo"});
		map.put(SpcsInfo.class, new String[]{"agentInfos"});
		data = JsonUtil.objectToJson(obj, map);
		return data;
	}
		**/
}
