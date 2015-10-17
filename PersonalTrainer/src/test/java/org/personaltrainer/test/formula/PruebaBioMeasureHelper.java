package org.personaltrainer.test.formula;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.personaltrainer.business.BioMeasureHelper;
import org.personaltrainer.web.beans.user.UserDataBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PruebaBioMeasureHelper {

	static ApplicationContext ctx = null;
	static UserDataBean userDataBean=null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ctx=new ClassPathXmlApplicationContext ("WebApplicationContext.xml");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		userDataBean= new UserDataBean();
		userDataBean.setBelly(new BigDecimal(86.0));
		userDataBean.setWeight(new BigDecimal(90.0));
		userDataBean.setChest(new BigDecimal(0.0));
		userDataBean.setHeight(new BigDecimal(180.0));
		userDataBean.setHip(new BigDecimal(0.0));
		userDataBean.setHeartrate(new BigDecimal(72.0));
		userDataBean.setMaxheartrate(new BigDecimal(190.0));
		userDataBean.setAge("30.0");
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		BioMeasureHelper helper = (BioMeasureHelper)ctx.getBean("bioMeasureHelper");
		 
		
		System.out.println("se ha obtenido el valor IGC="+helper.getMeasureValue("IGCMALE", userDataBean));
		System.out.println("se ha obtenido el valor IMC="+helper.getMeasureValue("IMC", userDataBean));
		System.out.println("se ha obtenido el valor MBAS="+helper.getMeasureValue("METBASMALE", userDataBean));
		System.out.println("se ha obtenido el valor MCM="+helper.getMeasureValue("MCMMALE", userDataBean));
		System.out.println("se ha obtenido el valor MCM="+helper.getMeasureValue("HRTRTMALE", userDataBean));
		System.out.println("se ha obtenido el valor Maxima Frecuencia Cardiaca="+helper.getMeasureValue("MXHRTRTMAL", userDataBean));
		System.out.println("se ha obtenido el valor Rango de frecuencias Zona A: "+helper.getMeasureValue("MINHRRATEA", userDataBean)+" - "+helper.getMeasureValue("MAXHRRATEA", userDataBean));
		System.out.println("se ha obtenido el valor Rango de frecuencias Zona B: "+helper.getMeasureValue("MAXHRRATEB", userDataBean)+" - "+helper.getMeasureValue("MAXHRRATEB", userDataBean));
		System.out.println("se ha obtenido el valor Rango de frecuencias Zona C: "+helper.getMeasureValue("MINHRRATEC", userDataBean)+" - "+helper.getMeasureValue("MAXHRRATEC", userDataBean));
		System.out.println("se ha obtenido el valor Rango de frecuencias Zona D: "+helper.getMeasureValue("MINHRRATED", userDataBean)+" - "+helper.getMeasureValue("MAXHRRATED", userDataBean));
		System.out.println("se ha obtenido el valor Rango de frecuencias Zona E: "+helper.getMeasureValue("MINHRRATEE", userDataBean)+" - "+helper.getMeasureValue("MAXHRRATEE", userDataBean));
	}

}
