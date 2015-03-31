package net.suntec.iauto.jca.psetua;

/**
 * Java版本Token解析Class
 * 修订履历：
 * rev1.	2012-03-**	TXF		第一版
 * rev2.	2012-03-16	TXF		Base64解码使用org.apache.commons.codec.binary.Base64
 * 								替换原先的sun.misc.BASE64Decoder，避免编译警告
 */

import java.io.FileReader;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import sun.misc.BASE64Decoder;

public class AuthToken {
	Logger logger = LoggerFactory.getLogger(AuthToken.class);
	static final int TOKEN_TIMEOUT = 3600; // token设定1小时超时
	static final String TOKEN_TAG = "PSET-UA-TOKEN";
	static final String TOKEN_VERSION1 = "1";
	static final String TOKEN_VERSION2 = "2";

	static final String PUBLIC_KEY_HEAD = "-----BEGIN PUBLIC KEY-----";
	static final String PUBLIC_KEY_TAIL = "-----END PUBLIC KEY-----";

	private String aplver;
	private long userId;
	private String deviceNo;
	private String nickname;
	private long sessionId;
	private int loginType;
	private double timestamp;

	private Cipher cipher;

	public static void main(String[] args) {
		try {
			// String publicKeyFile =
			// "C:/Business/NaviCloud/SVN/trunk/src/navisocial/src/navisocial/resources/id_rsa.pub";
			String publicKeyFile = "H:\\code\\java\\openjava\\authsrv\\src\\test\\resources\\key\\id_rsa.pub";

			String token_v1 = "Bp/8lHDh9yULI/dVb6K8K3X5GPznf9GnZANPNC/Oq3D8ApRgYUiDumoFLf3rxqvJtYHVcn6IlZm6i+nlwa4LzEM/sSh5/Y2BcVRKacMfg6xPBTEmBSOnCl566Um23qH9GNVtXGHJ3r7HqwbsGR9cEeGtS8ZOqFwXtvtgoeTdPFz+jpbPX3BTYaja5bYstMTofvo+XbKYNs90EmigAXQZMOB1TxF3Hikgh13kRPGRksAovEBiRVQNMjIoY5X+9T6ijti8TYK1iN84Jd+AkxashyyDFmuOzW0G4YzWzOBmEXwc3tRDw1Y/oh6mbY8PeuGkSxFdA69YPjZJB1/2YUwfxQ==";
			String token_v2 = "VunOssqt8c1bZHOZ4AyAGcDztfrF0GAxRQOPkWTVwBjNKrvswBjPnhGinZAWpsa1GCWWLeRnUBrBMFSvK4hWp4+Zm+ZhfLGG5bFxgqPAb/dZJzqKCpe9zcLuD65pxmIZYyAVd7ub0eWWYR6yhYppvQq2MZ7B2ytSez8Xq95ncEA3eddNe5p4n92N2eqYtawyovD3KAwZyuHeuNgresEkzDU0/RaSShQ1umbDveopQEP1IC5DCccA2GWx7biu2Eg1UxFJVPk6KqZTvi1jFyo9PKa4hVgvOVQ+WvkgocskT84OMNjug0DcXzJ0ivZvzQ3ivugio/z+QhozMD7QDxmfmw==";
			AuthToken obj = new AuthToken();
			obj.prepareCipher(publicKeyFile);
			int result = obj.loadToken(token_v1);
			System.out.print("load token_v1 result:");
			System.out.println(result);

			result = obj.loadToken(token_v2);
			System.out.print("load token_v2 result:");
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static PublicKey getPublicKey(String strKey) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decodeBase64(strKey);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	private void prepareCipher(String publicKeyFile) throws Exception {
		FileReader fr = new FileReader(publicKeyFile);
		char[] keyBuff = new char[1024];
		int keyLen = fr.read(keyBuff);
		fr.close();

		String strKey = new String(keyBuff, 0, keyLen);
		if (strKey.startsWith(PUBLIC_KEY_HEAD))
			strKey = strKey.substring(PUBLIC_KEY_HEAD.length());
		if (strKey.endsWith(PUBLIC_KEY_TAIL))
			strKey = strKey.substring(0,
					strKey.length() - PUBLIC_KEY_TAIL.length());
		strKey = strKey.replace("\n", "");

		PublicKey key = AuthToken.getPublicKey(strKey);
		Cipher rsaCipher = Cipher.getInstance("RSA");
		rsaCipher.init(Cipher.DECRYPT_MODE, key);

		cipher = rsaCipher;
	}

	/**
	 * 返回0，表示成功 错误代码：1: timeout 2: 其他错误
	 */
	int loadToken(String token) {
		try {
			byte[] buff = Base64.decodeBase64(token);
			byte[] buff2 = cipher.doFinal(buff);

			String strToken2 = new String(buff2, "utf-8");

			String[] tokenParts = strToken2.split(";");
			String tokenTag = tokenParts[0];
			String tokenVer = tokenParts[1];
			if (!TOKEN_TAG.equals(tokenTag))
				return 2;

			if (TOKEN_VERSION1.equals(tokenVer)) {
				// token格式:PSET-UA-TOKEN;<tokenver>;<aplver>;<userId>;<nickname>;<sessionId>;<loginType>;<timestamp>
				if (tokenParts.length != 8)
					return 2;

				aplver = tokenParts[2];
				userId = Long.parseLong(tokenParts[3]);
				nickname = tokenParts[4];
				sessionId = Long.parseLong(tokenParts[5]);
				loginType = Integer.parseInt(tokenParts[6]);
				timestamp = Double.parseDouble(tokenParts[7]);

			} else if (TOKEN_VERSION2.equals(tokenVer)) {
				// token格式:PSET-UA-TOKEN;<tokenver>;<aplver>;<userId>;<deviceno>;<nickname>;<sessionId>;<loginType>;<timestamp>
				if (tokenParts.length != 9)
					return 2;

				aplver = tokenParts[2];
				userId = Long.parseLong(tokenParts[3]);
				deviceNo = tokenParts[4];
				nickname = tokenParts[5];
				sessionId = Long.parseLong(tokenParts[6]);
				loginType = Integer.parseInt(tokenParts[7]);
				timestamp = Double.parseDouble(tokenParts[8]);
			}
			logger.info("aplver: " + aplver);
			logger.info("userId: " + userId);
			logger.info("deviceNo: " + deviceNo);
			logger.info("nickname: " + nickname);
			logger.info("sessionId: " + sessionId);
			logger.info("loginType: " + loginType);
			logger.info("timestamp: " + timestamp);

			double now = (new Date()).getTime() / 1000; // 转换为以秒为单位
			if (now - timestamp > TOKEN_TIMEOUT)
				return 1; // timeout
		} catch (Exception e) {
			return 2;
		}
		return 0;
	}

	public String getAplver() {
		return aplver;
	}

	public String getNickname() {
		return nickname;
	}

	public long getUserId() {
		return userId;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public long getSessionId() {
		return sessionId;
	}

	public int getLoginType() {
		return loginType;
	}

	public double getTimestamp() {
		return timestamp;
	}
}
