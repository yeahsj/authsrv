package net.suntec.common;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseTest extends TestCase {
	public ApplicationContext ctx = null;
	public Logger logger = null;

	public void setUp() throws Exception {
		super.setUp();
		logger = LoggerFactory.getLogger(BaseTest.class);
		logger.info(" start ");
		try {
			ctx = new ClassPathXmlApplicationContext("/springCore.xml");
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
}
