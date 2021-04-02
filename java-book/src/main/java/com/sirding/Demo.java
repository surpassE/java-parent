package com.sirding;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Demo {

	public static void main(String[] args) throws Exception {
        // Java 8例子
        List<String> lines = Files.readAllLines(Paths.get("D:\\test\\张旭东20210319221427.txt"), StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        for(String line : lines){
            if (StringUtils.isEmpty(line) && line.length() <= 0) {
                continue;
            }
            JSONObject json = new JSONObject();
            json.put("class", "com.jd.jr.gyl.activity.export.request.GaRequest");
            json.put("defaultCipherText", "239213c81059533640d390d022ee017c");
            json.put("defaultPlainText", "1606577262869");
            json.put("pageNo", 0);
            json.put("pageSize", 10);
            json.put("productCode", "00000");
            json.put("systemCode", "CREDIT");
            JSONObject obj = JSONObject.parseObject(line);
            obj.put("class", "com.jd.jr.gyl.activity.export.param.UserSignUpParam");
            json.put("request", obj);
            System.out.println(json.toJSONString());
        }


    }
	

	
}
