/**
 * @(#) EncryptUtilsDB.java
 * 
 * Project: PioneerService
 * Title: EncryptUtilsDB.java
 * Description:Utility class used to perform encryption and decryption
 * of information to the BD.
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
public class EncryptUtilsDB {

	// Property Fields Logger
	private static final Logger log = LoggerFactory.getLogger(EncryptUtilsDB.class.getName());

	// Encryption Data Constant
	private static final String SALT = "*aPUSwi_ah2brudre-lxicunlkoKehuc";
	private static final String KEY = "d@UZIkiCe-t@th7Mi$==wreyi_w+5ovo";

	// Instants EncryptUtilsDB
	private static EncryptUtilsDB instancia = null;

	// Fields
	private Cipher cipher = null;
	private SecretKey secretKey = null;
	
	/**
	 * 
	 * @return EncryptUtilsDB
	 */
	public static EncryptUtilsDB getInstance() {
		if (instancia == null) {
			instancia = new EncryptUtilsDB(EncryptUtilsDB.KEY);
		}
		return instancia;
	}

	/**
	 * 
	 * @param password
	 */
	private EncryptUtilsDB(final String password) {
		try {
			final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			final PBEKeySpec keySpec = new PBEKeySpec(EncryptUtilsDB.KEY.toCharArray(), 
				EncryptUtilsDB.SALT.getBytes(), 65536, 256);
			secretKey = secretKeyFactory.generateSecret(keySpec);

			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (final Exception e) {
			log.error("EncryptUtilsDB(password) -Invoking the method that sets the encryption password", e);
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
			log.error("encrypt(cadena) -Invoking the method that sets the encryption password", e);

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
			log.error("encrypt(cadena) - Invoking the method that sets the encryption password", e);

			throw new EncryptException(e.getMessage());
		}
	}
}