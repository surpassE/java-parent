package com.sirding.javase;

import java.util.Arrays;
import java.util.List;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

public class ScirptEngineTest {

	public static void main(String[] args) {
		try {
			Model m1 = new Model();
			m1.setName("hello");
			Model2 m2 = new Model2();
			m2.setHand("hand");
			String[] arrs = {"aa", "bb", "cc"};
			List<String> list = Arrays.asList("1", "2", "3");
			Model l1 = new Model("l1");
			Model l2 = new Model("l2");
			Model l3 = new Model("l3");
			List<Model> l = Arrays.asList(l1, l2, l3);
			
			List<Object> objs = Arrays.asList(m1, m2, list, l);
			
			ScriptEngine engine = (new ScriptEngineManager()).getEngineByName("js");
			ScriptContext context = new SimpleScriptContext();
			context.setAttribute("args", objs, ScriptContext.ENGINE_SCOPE);
//			context.setAttribute(name, value, scope);
			System.out.println(engine.eval("args[0].name", context));
			System.out.println(engine.eval("args[1].hand", context));
			System.out.println(engine.eval("args[2][1]", context));
			System.out.println(engine.eval("args[3][1].name", context));
			System.out.println(engine.eval("1==1 ? '你好' : false"));

			engine.eval("var arr = 'hello';");
//			Object[] o = (Object[]) engine.get("arr");
			System.out.println(engine.get("arr"));
			
			System.out.println(engine.eval("var date = new Date();" +"date.getHours();"));
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
