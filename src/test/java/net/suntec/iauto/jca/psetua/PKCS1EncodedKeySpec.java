package net.suntec.iauto.jca.psetua;

import java.security.spec.EncodedKeySpec;

public class PKCS1EncodedKeySpec extends EncodedKeySpec {

	public PKCS1EncodedKeySpec(byte[] encodedKey) {
		super(encodedKey);
	}

	public byte[] getEncoded() {
		return super.getEncoded();
	}

	@Override
	public String getFormat() {
		return "PKCS#1";
	}

}
