package com.xsebe.yumao;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AbstractTest {
    private BeanFactory beans;

    @Before
    public void before() {
        beans = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
    }

    @After
    public void after() {
        beans = null;
    }

    public <T> T get(Class<T> clazz) {
        return beans.getBean(clazz);
    }
}
