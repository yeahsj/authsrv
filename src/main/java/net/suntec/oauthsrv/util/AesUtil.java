package net.suntec.oauthsrv.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:49:27
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class AesUtil {
	private static final String SALT = "3FF2EC019C627B945225DEBAD71A01B6985FE84C95A70EB132882F88C0A59A55";

	private static final String IV = "F27D5C9927726BCEFE7510B1BDD3D137";

	private static final int ITERATION_COUNT = 5;

	private static final int KEY_SIZE = 128;
	private static final String PASSKEY = "psetsuntec";

	private final int keySize;
	private final int iterationCount;
	private final Cipher cipher;

	public AesUtil(int keySize, int iterationCount) {
		this.keySize = keySize;
		this.iterationCount = iterationCount;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			throw fail(e);
		} catch (NoSuchPaddingException e) {
			throw fail(e);
		}
	}

	public String encrypt(String salt, String iv, String passphrase,
			String plaintext) {
		try {
			SecretKey key = generateKey(salt, passphrase);
			byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, key, iv,
					plaintext.getBytes("UTF-8"));
			return base64(encrypted);
		} catch (UnsupportedEncodingException e) {
			throw fail(e);
		}
	}

	public String decrypt(String salt, String iv, String passphrase,
			String ciphertext) {
		try {
			SecretKey key = generateKey(salt, passphrase);
			byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, iv,
					base64(ciphertext));
			return new String(decrypted, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw fail(e);
		}
	}

	private byte[] doFinal(int encryptMode, SecretKey key, String iv,
			byte[] bytes) {
		try {
			cipher.init(encryptMode, key, new IvParameterSpec(hex(iv)));
			return cipher.doFinal(bytes);
		} catch (InvalidKeyException e) {
			throw fail(e);
		} catch (InvalidAlgorithmParameterException e) {
			throw fail(e);
		} catch (IllegalBlockSizeException e) {
			throw fail(e);
		} catch (BadPaddingException e) {
			throw fail(e);
		}
	}

	private SecretKey generateKey(String salt, String passphrase) {
		try {
			SecretKeyFactory factory = SecretKeyFactory
					.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), hex(salt),
					iterationCount, keySize);
			SecretKey key = new SecretKeySpec(factory.generateSecret(spec)
					.getEncoded(), "AES");
			return key;
		} catch (NoSuchAlgorithmException e) {
			throw fail(e);
		} catch (InvalidKeySpecException e) {
			throw fail(e);
		}
	}

	public static String random(int length) {
		byte[] salt = new byte[length];
		new SecureRandom().nextBytes(salt);
		return hex(salt);
	}

	public static String base64(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	public static byte[] base64(String str) {
		return Base64.decodeBase64(str);
	}

	public static String hex(byte[] bytes) {
		return Hex.encodeHexString(bytes);
	}

	public static byte[] hex(String str) {
		try {
			return Hex.decodeHex(str.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException(e);
		}
	}

	private IllegalStateException fail(Exception e) {
		return new IllegalStateException(e);
	}

	public static String encrypt(String src) {
		AesUtil util = new AesUtil(KEY_SIZE, ITERATION_COUNT);
		String encrypt = util.encrypt(SALT, IV, PASSKEY, src.trim());
		return encrypt;
	}

	public static String encrypt(String src, String key) {
		AesUtil util = new AesUtil(KEY_SIZE, ITERATION_COUNT);
		String encrypt = util.encrypt(SALT, IV, key, src.trim());
		return encrypt;
	}

	public static String decrypt(String src) {
		AesUtil util = new AesUtil(KEY_SIZE, ITERATION_COUNT);
		String decrypt = util.decrypt(SALT, IV, PASSKEY, src.trim());
		return decrypt;
	}

	public static String decrypt(String src, String key) {
		AesUtil util = new AesUtil(KEY_SIZE, ITERATION_COUNT);
		String decrypt = util.decrypt(SALT, IV, key, src.trim());
		return decrypt;
	}
}
