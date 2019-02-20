package njurestaurant.njutakeout.util;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

public class AESDecodeUtils {
    public static String decrypt(String sessionKey, String ivCode, String encrypData) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(Base64.decodeBase64(ivCode));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(Base64.decodeBase64(sessionKey), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        //解析解密后的字符串
        return (String) JSONObject.fromObject(new String(cipher.doFinal(Base64.decodeBase64(encrypData)), "UTF-8")).get("phoneNumber");
    }
}

