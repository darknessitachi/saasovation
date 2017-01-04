package org.infinite.identityaccess.infrastructure.services;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.infinite.identityaccess.domain.service.EncryptionService;

import com.abigdreamer.infinity.common.lang.AssertionConcern;


/**
 * MD5加密服务
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:25:48
 * @version V1.0
 */
public class MD5EncryptionService extends AssertionConcern implements EncryptionService {

    public MD5EncryptionService() {
        super();
    }

    @Override
    public String encryptedValue(String aPlainTextValue) {
        this.assertArgumentNotEmpty(
                aPlainTextValue,
                "Plain text value to encrypt must be provided.");

        String encryptedValue = null;

        try {

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.update(aPlainTextValue.getBytes("UTF-8"));

            BigInteger bigInt = new BigInteger(1, messageDigest.digest());

            encryptedValue = bigInt.toString(16);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return encryptedValue;
    }
}
