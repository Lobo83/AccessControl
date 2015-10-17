package org.accessControl.test.accessTest;

import org.accessControl.persistence.logic.AccessControlLogic;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccessTest {
	static ApplicationContext ctx;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
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

		DummyCanAccess obj = (DummyCanAccess)ctx.getBean("claseDummy");
		obj.metodoDummy("ACPROFILE1");
		
		AccessControlLogic logic =(AccessControlLogic)ctx.getBean("logicaAcceso");
		System.out.println("Perfil obtenido: "+logic.doLoginAndGetProfile("Lobo", "1a2j4a"));

	}

}
