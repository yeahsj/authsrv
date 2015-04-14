package net.suntec.oauthsrv.action.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import com.openjava.core.util.CalendarUtil;

public class TestTime extends TestCase{
	public void testGetTime(){
		long time=1427788848706L;
		//15:57:07
		Date d = new Date(time);
		DateFormat format= new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		System.out.println( format.format( d ));
		System.out.println( CalendarUtil.getCurrTime());
	}
}
