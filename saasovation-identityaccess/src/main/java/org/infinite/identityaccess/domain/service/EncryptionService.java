package org.infinite.identityaccess.domain.service;

/**
 * 加密服务
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:10:57
 * @version V1.0
 */
public interface EncryptionService {

	/**
	 * 将明文加密
	 * @param aPlainTextValue 明文
	 * @return 加密后的字符串
	 */
	String encryptedValue(String aPlainTextValue);
}
