package org.personaltrainer.test.statistic;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.personaltrainer.business.StatisticHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StatisticTest {
	static ApplicationContext ctx = null;
	static StatisticHelper statisticHelper;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx=new ClassPathXmlApplicationContext ("WebApplicationContext.xml");
		statisticHelper=(StatisticHelper) ctx.getBean("statisticHelper");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		List<Object>result=statisticHelper.getUserMeasureStastic("weight", 1);
		for(Object registro:result){
			Object[] dato=(Object[]) registro;
			System.out.println("Peso "+dato[0]+" Fecha "+dato[1]);	
		}
		
	}

}
