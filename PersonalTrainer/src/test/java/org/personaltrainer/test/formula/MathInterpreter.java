package org.personaltrainer.test.formula;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class MathInterpreter {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		ExpressionParser parser = (ExpressionParser) new SpelExpressionParser();
		Expression e = parser.parseExpression("((90.0 -(90.0*0.85)+28-(86.0*0.35))*100.0)/90.0");
		System.out.println("resultado de la expresion: "+e.getValue().toString());
	}

}
