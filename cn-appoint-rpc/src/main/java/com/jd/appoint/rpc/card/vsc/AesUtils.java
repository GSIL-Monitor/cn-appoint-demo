package com.jd.appoint.rpc.card.vsc;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * <p><b>Description：</b></p>
 * <p> </p>
 * <p><b>createTime: </b></p>
 * <p>2018/2/11 11:43</p>
 *
 * @author baoyang
 * @since 1.0
 */
@SuppressWarnings("restriction")
public final class AesUtils {

    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     * Java 6支持PKCS5Padding填充方式
     * Bouncy Castle支持PKCS7Padding填充方式
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String KEY_ALGORITHM = "AES"; //密钥算法
    private static final String charetSet = "UTF-8";

    private AesUtils() {
    }

    /**
     * 转换密钥
     *
     * @param secretKey 密钥字符串
     * @return SecretKeySpec 密钥
     * @throws UnsupportedEncodingException
     */
    private static Key toKey(String secretKey) throws UnsupportedEncodingException {
        byte[] raw = secretKey.getBytes(charetSet);
        return new SecretKeySpec(raw, KEY_ALGORITHM); // 实例化AES密钥材料
    }

    public static String encrypt(String secretKey, String str) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, toKey(secretKey));
            byte[] byteContent = str.getBytes(charetSet);
            byte[] encrypted = cipher.doFinal(byteContent);
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(encrypted);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException
                | UnsupportedEncodingException | IllegalBlockSizeException e) {
            throw new RuntimeException("encrypt fail", e);
        }
    }

    public static String decrypt(String secretKey, String str) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, toKey(secretKey));
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] decrypted = cipher.doFinal(decoder.decodeBuffer(str));
            return new String(decrypted, charetSet);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException
                | IllegalBlockSizeException | IOException e) {
            throw new RuntimeException("decrypt fail", e);
        }
    }

}
