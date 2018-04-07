package com.sirding.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dzc on 18.1.12
 */
public class Demo {
    public static void main(String[] args) throws Exception{
        InputStream inputStream = Demo.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);


        SqlSession session = sqlSessionFactory.openSession();
        Connection conn = session.getConnection();
        conn.setAutoCommit(false);
        Blog blog = new Blog();
        blog.setNames(Arrays.asList("a", "b"));
        blog.setStates(Arrays.asList(1, 2));
        List<Blog> list = session.selectList("com.dobe.BlogMapper.selectBlog", blog);
        Long count = session.selectOne("com.dobe.BlogMapper.selectCount", blog);
        conn.commit();
        System.out.println(list);
        System.out.println(count);
        list.forEach(e -> System.out.println(e));
//        list1.forEach(e -> System.out.println(e));

        System.out.println("ok...");
    }
}
