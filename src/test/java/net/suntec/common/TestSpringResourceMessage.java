package net.suntec.common;

import java.util.Locale;

public class TestSpringResourceMessage extends BaseTest {

	public void setUp() throws Exception {
		super.setUp();
	}

	public void testSpringLocale() {
		logger.info("testSpringLocale.................");
		String msgBind = super.ctx
				.getMessage("MSG_BIND", null, Locale.JAPANESE);
		logger.info(Locale.JAPANESE.toString()  + " bind msg: " + msgBind);
		
		msgBind = super.ctx.getMessage("MSG_BIND", null, Locale.JAPAN);
		logger.info( Locale.JAPAN.toString()  + " bind msg: " + msgBind);
		msgBind = super.ctx.getMessage("MSG_BIND", null, Locale.CHINA);
		logger.info(Locale.CHINA.toString()  + " bind msg: " + msgBind);
	}
}
