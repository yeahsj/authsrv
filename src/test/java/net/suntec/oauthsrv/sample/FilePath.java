package net.suntec.oauthsrv.sample;

import com.openjava.core.util.SystemUtil;

public class FilePath {
	public static void main(String[] args) {
		String str = SystemUtil.getClassPath( SystemUtil.class);
		System.out.println( str );
	}
}
