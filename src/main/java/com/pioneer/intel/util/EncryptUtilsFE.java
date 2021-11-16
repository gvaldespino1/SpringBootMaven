/**
 * @(#) EncryptUtilsFE.java
 * 
 * Project: PioneerService
 * Title: EncryptUtilsFE.java
 * Description: Utility class used to perform encryption and decryption
 * of information to the FrontEnd.
 * @author: Gustavo Valdespino
 * @version 1.0
 */
package com.pioneer.intel.util; 

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pioneer.intel.exception.EncryptException;

/**
 */
public class EncryptUtilsFE {

	// Property Fields Logger
	private static final Logger log = LoggerFactory.getLogger(EncryptUtilsFE.class.getName());

	// Encryption Data Constant
	private static final String SALT = "*aPUSwi_ah2brudre-lxicunlkoKehuc";
	private static final String KEY = "WIyOtU0T3ne!rEVa&=@WRokI*05V_&#E";

	// Instants EncryptUtilsFE
	private static EncryptUtilsFE instancia = null;

	// Fields
	private Cipher cipher = null;
	private SecretKey secretKey = null;
	
	/**
	 * 
	 * @return EncryptUtilsFE
	 */
	public static EncryptUtilsFE getInstance() {
		if (instancia == null) {
			instancia = new EncryptUtilsFE(EncryptUtilsFE.KEY);
		}
		return instancia;
	}

	/**
	 * 
	 * @param password
	 */
	private EncryptUtilsFE(final String password) {
		try {
			final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			final PBEKeySpec keySpec = new PBEKeySpec(EncryptUtilsFE.KEY.toCharArray(), 
				EncryptUtilsFE.SALT.getBytes(), 65536, 256);
			secretKey = secretKeyFactory.generateSecret(keySpec);

			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (final Exception e) {
			log.error("EncryptUtilsFE(password) - Invoking the method that sets the encryption password", e);
		}
	}

	/**
	 * 
	 * @param cadena
	 * @return String
	 * @throws EncryptException
	 */
	public String encrypt(final String cadena) throws EncryptException {
		byte iv[] = new byte[16];

		try {
			final IvParameterSpec parameterSpec = new IvParameterSpec(iv);
			final SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, parameterSpec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(cadena.getBytes("UTF-8")));
		} catch (final InvalidKeyException | InvalidAlgorithmParameterException | UnsupportedEncodingException
				| BadPaddingException | IllegalBlockSizeException e) {
			// Envia un error en caso de excepcion
			log.error("encrypt(cadena) - Invoking the encryption method given a byte string", e);

			throw new EncryptException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param cadena
	 * @return String
	 * @throws EncryptException
	 */
	public String decrypt(String cadena) throws EncryptException {
		byte iv[] = new byte[16];

		try {
			final IvParameterSpec parameterSpec = new IvParameterSpec(iv);
			final SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, parameterSpec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(cadena)));
		} catch (final InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException
				| IllegalBlockSizeException e) {
			log.error("encrypt(cadena) - Invoking the encryption method given a byte string", e);

			throw new EncryptException(e.getMessage());
		}
	}
}