package com.sirding.javaeight.js;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 测试javascript引擎
 *
 * @author zc.ding
 * @create 2018/10/8
 */
public class TestJs {
    
    @Test
    public void test1() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine scriptEngine = manager.getEngineByName("js");
        Object result = scriptEngine.eval("'hello, world'.length");
        System.out.println(result);
    }
}
