import java.lang.reflect.Field;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sirding.service.LockService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-core.xml" })
public class TestLock {

	@Autowired
	private LockService lockService;

	@Autowired
	protected ApplicationContext applicationContext;

	@Test
	public void test1() {
		// lockService.testLock();
		Class<?>[] a = TestIocImpl.class.getInterfaces();
		System.out.println(a);
		BeanDefinitionBuilder dataSourceBuider = BeanDefinitionBuilder.genericBeanDefinition(TestIocImpl.class);
		BeanDefinition beanDefinition = dataSourceBuider.getBeanDefinition();
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext
				.getAutowireCapableBeanFactory();
		beanFactory.registerBeanDefinition(TestIoc.class.getName(), beanDefinition);

		Object obj = applicationContext.getBean(TestIoc.class);
		a = obj.getClass().getInterfaces();
		System.out.println(obj);
		System.out.println("okokokok.....");
	}

	@Test
	public void test2() {
		Field[] arr = TestIocImpl.class.getFields();
		System.out.println("==========");
		System.out.println(TestIoc.class.getName());
		for (Field f : arr) {
			System.out.println(f.getType().getName());
			System.out.println(f.getType().getClass().getName());
		}
		System.out.println("==========");
	}
}
