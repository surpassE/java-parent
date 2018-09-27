package com.sirding.javaeight;

import org.junit.Test;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;

import static java.lang.System.out;


public class LambdaDemo {

	@Test
	public void test1(){
		Arrays.asList( "a", "b", "d" ).forEach( e -> out.println( e ));
		List<String> list = Arrays.asList( "a", "d", "b" );
		list.sort( ( e1, e2 ) -> e1.compareTo( e2 ));
		list.forEach( e -> out.println( e ));
	}
	
	@Test
	public void test2(){
		Optional< String > fullName = Optional.ofNullable( null );
		System.out.println( "Full Name is set? " + fullName.isPresent() );        
		System.out.println( "Full Name: " + fullName.orElseGet( () -> "[none]" ) ); 
		System.out.println( fullName.map( s -> "Hey " + s + "!" ).orElse( "Hey Stranger!" ) );
	}
	
	@Test
	public void test3(){
		Map<String, String> m = new HashMap<>();
		m.put("a", "hello");
		m.put("b", "world");
		Optional<Map<String, String>> o = Optional.ofNullable(m);
		Optional<Set<Entry<String, String>>> os = o.map(map -> map.entrySet()).filter(key -> {out.println(key);return true;});
		out.println(os.isPresent());
		Set<Entry<String, String>> set = os.orElseGet(() -> null);
		set.forEach(e -> out.println(e.getValue()));
	}
	
	
	static void calc(int val, Function<Integer, Integer> function){
        out.println(function.andThen(a -> a * 10).apply(val));
        out.println(function.compose(b -> (Integer)b * 10).apply(val));
	}

	@Test
	public void testCalc(){
		calc(10, a -> a + 10);
	}
	
	@Test
	public void testOptional(){
		final String dv = "None";
		String msg = "test";
		Optional<String> o = Optional.of(msg);
		out.println(o.filter(t -> "test".equals(t)).get());
		//flatMap与map（Function）非常类似，区别在于传入方法的lambda表达式的返回类型。
		//map方法中的lambda表达式返回值可以是任意类型，在map函数返回之前会包装为Optional。 
		//但flatMap方法中的lambda表达式返回值必须是Optionl实例。
		o = o.flatMap(s -> Optional.of(s));
		o.map(v -> v);
		o = Optional.empty();
//		有值返回值，无值返回none
		out.println(o.orElse("none"));
//		有值返回值，无值返回由Supplier提供的默认值
		out.println(o.orElseGet(() -> dv + "none"));
		//有值返回值，无值抛出异常
//		out.println(o.get());
		
	}
	
	@Test
	public void testL()	{
		int[] arr = new int[]{1,2,3,4,5,6,7,8};
		
	}
	
	@Test
	public void testRun(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello");
			}
		}).start();
		
		new Thread(() -> {System.out.println("hello world");}).start();
	}
	
	@Test
	public void testCompare(){
		List<Model> list = new ArrayList<>();
		list.sort((m1, m2) -> m1.getName().compareTo(m2.getName()));
		Collections.sort(list, (m1, m2) -> m1.getName().compareTo(m2.getName()));
		//多条件组合排序
		list.sort(Comparator.comparing(Model::getName).thenComparing(Model::getAge));
	}
}
