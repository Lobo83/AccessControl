package org.personaltrainer.business;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.personaltrainer.persistence.bean.BioMeasure;
import org.personaltrainer.web.beans.user.UserDataBean;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class BioMeasureHelper {

	private ExpressionParser parser;
	private HibernateTemplate hibernateTemplate;
	private static final Log log=LogFactory.getLog(BioMeasureHelper.class);
	
	public ExpressionParser getParser() {
		return parser;
	}

	public void setParser(ExpressionParser parser) {
		this.parser = parser;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, readOnly=true)
	public BigDecimal getMeasureValue(final String codMeasure, final UserDataBean userDataBean){
		BigDecimal value=null;
		BioMeasure measure=(BioMeasure)hibernateTemplate.load(BioMeasure.class, codMeasure);
		String sFormula=measure.getFormula();
		
		//Sustitución de los parámetros por los valores del usuario
		sFormula=sFormula.replaceAll("PECHO", new String(userDataBean.getChest().toString()));
		sFormula=sFormula.replaceAll("CINTURA", new String(userDataBean.getBelly().toString()));
		sFormula=sFormula.replaceAll("CADERA", new String(userDataBean.getHip().toString()));
		sFormula=sFormula.replaceAll("PESO", new String(userDataBean.getWeight().toString()));
		if(null!=userDataBean.getMaxheartrate()){//durante la creación este dato no está disponible, tendrá que venir calculado
			sFormula=sFormula.replaceAll("MAX_FREQ_CARDIACA", new String(userDataBean.getMaxheartrate().toString()));
		}
		sFormula=sFormula.replaceAll("FREQ_CARDIACA", new String(userDataBean.getHeartrate().toString()));
		sFormula=sFormula.replaceAll("ALTURA", new String(userDataBean.getHeight().toString()));
		sFormula=sFormula.replaceAll("EDAD", userDataBean.getAge());
		
		log.info("Se ha obtenido la formula "+sFormula);
		Expression e = parser.parseExpression(sFormula);
		value=new BigDecimal(e.getValue().toString());
		return value.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	}
}
