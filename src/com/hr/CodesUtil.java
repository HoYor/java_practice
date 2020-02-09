//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hr;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CodesUtil {
    public static final String ALGORITHM_PCKS5PADDING = "AES/CBC/PKCS5Padding";
    public static final String KEY_ALGORITHM = "AES";
    private static final String STRING_FORMAT = "utf-8";
    public static final String HMAC_SHA_256 = "HmacSHA256";
    private static final int IV_LENGTH = 16;
    private static final int HMAC_HASH_LENGTH = 32;

    public CodesUtil() {
    }

    public static String decrypt(String codeTextBase64, String key) {
        try {
            byte[] decode64 = Base64.decode(codeTextBase64, 2);
            byte[] iv_byte = Arrays.copyOfRange(decode64, 0, 16);
            byte[] ase_byte = Arrays.copyOfRange(decode64, 48, decode64.length);
            byte[] decrypt = decrypt(key.getBytes("utf-8"), iv_byte, ase_byte);
            if (decrypt != null) {
                return new String(decrypt);
            }
        } catch (UnsupportedEncodingException | IndexOutOfBoundsException var6) {
            var6.printStackTrace();
        }

        return null;
    }

    private static String makeRawIv() {
        return UUID.randomUUID().toString().substring(0, 16);
    }

    public static String encrypt(String source, String key) {
        String iv = makeRawIv();

        try {
            byte[] encryptAesText = encrypt(key, iv, source.getBytes("utf-8"));
            byte[] hmac = hash_hmac_sha256(key, source);
            return new String(Base64.encode(append(append(iv.getBytes(), hmac), encryptAesText), 2));
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(String key, String iv, byte[] needDecrypt) {
        try {
            return decrypt(key.getBytes("utf-8"), iv.getBytes("utf-8"), needDecrypt);
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] key_bytes, byte[] iv_bytes, byte[] needDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, new SecretKeySpec(key_bytes, "AES"), new IvParameterSpec(iv_bytes));
            return cipher.doFinal(needDecrypt);
        } catch (NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static byte[] encrypt(String key, String iv, byte[] needEncrypt) {
        try {
            return encrypt(key.getBytes("utf-8"), iv.getBytes("utf-8"), needEncrypt);
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static byte[] encrypt(byte[] key_bytes, byte[] iv_bytes, byte[] needEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, new SecretKeySpec(key_bytes, "AES"), new IvParameterSpec(iv_bytes));
            return cipher.doFinal(needEncrypt);
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static byte[] append(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static byte[] hash_hmac_sha256(String key, String text) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            return mac.doFinal(text.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | InvalidKeyException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;

        for(int i = 0; i < bts.length; ++i) {
            tmp = Integer.toHexString(bts[i] & 255);
            if (tmp.length() == 1) {
                des = des + "0";
            }

            des = des + tmp;
        }

        return des;
    }

    public static byte[] hexToByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];

        for(int i = 0; i < len; ++i) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }

        return result;
    }

    public static byte[] md5Hash(String origin) {
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        mDigest.update(origin.getBytes());
        return mDigest.digest();
    }

    public static String md5Hash(String origin, boolean isHex) {
        String key;
        byte[] bytes = md5Hash(origin);
        if(bytes == null){
            return null;
        }
        if(isHex) {
            key = bytesToHexString(bytes);
        }else{
            key = new String(bytes, StandardCharsets.US_ASCII);
        }

        return key;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < bytes.length; ++i) {
            String hex = Integer.toHexString(255 & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }

            sb.append(hex);
        }

        return sb.toString();
    }
}
