package cn.edu.jlu.personnel.management.util;

import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by jessin on 17-4-1.
 */
public class EncryptUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(EncryptUtil.class);
    private final static String ALGORITHM = "AES";
    private final static int KEY_BIT_LEN = 128;
    // 真正起作用的只有前8个字符?
    private final static String DEFAULT_SECRET_KEY = "secretKey";
    private final static String CHARSET = Charsets.UTF_8.name();
    private final static BaseEncoding BASE_ENCODING = BaseEncoding.base16();
    /**
     * 利用密码算法(AES)，密码和密钥位数生成密钥
     * 
     * @param password
     * @return 当指定的密码算法不支持，或者字符编码出错时，返回null
     */
    private static SecretKeySpec getSecretKeySpec(String password) {
        try {
            // 密码算法工厂类
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            // 利用密码和密钥位数生成安全的密钥
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes(CHARSET));
            keyGenerator.init(KEY_BIT_LEN, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            return new SecretKeySpec(enCodeFormat, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("算法不存在：{}，原因：{}", ALGORITHM, e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("不支持的字符编码：{}，原因：{}", CHARSET, e.getMessage(), e);
        }
        return null;
    }

    /**
     * 加密
     *
     * @param plainText 需要加密的内容
     * @param password 加密密码
     * @return 当密钥生成失败，或者加密出错，返回null
     */
    public static String encode(String plainText, String password) {
        try {
            // 生成密钥
            SecretKeySpec secretKeySpec = getSecretKeySpec(password);
            if (null == secretKeySpec) {
                return null;
            }
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 将明文字符串转化为字节
            byte[] byteContent = plainText.getBytes(CHARSET);
            // 加密模式，利用密钥初始化
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            // 加密，生成密文字节数组
            byte[] result = cipher.doFinal(byteContent);
            String encryptText = BASE_ENCODING.encode(result);
            LOGGER.debug("明文长度：{}，密文长度：{}", plainText.length(), encryptText.length());
            return encryptText;
        } catch (Exception e) {
            LOGGER.error("密码算法不存在 : {}", ALGORITHM,e);
        }
        return null;
    }

    /**
     * 使用默认密钥加密
     * 
     * @param plainText
     * @return
     */
    public static String encode(String plainText) {
        return encode(plainText, DEFAULT_SECRET_KEY);
    }

    /**
     * 解密
     *
     * @param encryptText 待解密内容，内容必须是16进制
     * @param password 解密密钥
     * @return 密钥生成出错哦或者解密出错，返回null
     */
    public static String decode(String encryptText, String password) {
        try {
            SecretKeySpec secretKeySpec = getSecretKeySpec(password);
            if (null == secretKeySpec) {
                return null;
            }
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 解密模式
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            // 先将密文恢复为字节数组
            byte[] encryptBytes = BASE_ENCODING.decode(encryptText);
            byte[] result = cipher.doFinal(encryptBytes);
            // 再利用加密时使用的字符编码转化为明文字符串
            return new String(result, CHARSET);
        } catch (Exception e) {
            LOGGER.error("密码算法不存在：{}", ALGORITHM,e);
        }
        return null;
    }

    /**
     * 使用默认密钥解密
     * 
     * @param encryptText
     * @return
     */
    public static String decode(String encryptText) {
        return decode(encryptText, DEFAULT_SECRET_KEY);
    }
}
