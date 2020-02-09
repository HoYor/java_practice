package com.hr;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class EncryptTest {

    private static final String server_key = "098f6bcd4621d373cade4e832627b4f6";
    private static final String auth_key = "0147780847ea95b8b7c013d907324e5e3ff11d3fa2c020d6a6209b9e6419cf8b";
    private static final String body_key = "a36a998dcf7443bbef24ebd7426639dd90406125b85da2036bd80456801c4fc4";

    public static String jsonEncode(String text, String password){
        String jsonBase64 = Base64.encodeToString(CodesUtil.bytes2Hex(CodesUtil.hash_hmac_sha256(text,text)).getBytes(US_ASCII), Base64.DEFAULT);
        String clientPassword = jsonBase64.substring(0,64);
        String tmpRawPassword = clientPassword+server_key;
        password = CodesUtil.bytes2Hex(CodesUtil.hash_hmac_sha256(tmpRawPassword, tmpRawPassword))+password;
        String aesEncode = null;
        try {
            aesEncode = Aes256.encrypt(text, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(clientPassword);
//        System.out.println(aesEncode);
        return clientPassword+aesEncode;
    }

    public static String jsonDecode(String text, String password){
        try {
            String clientPassword = text.substring(0,64);
            String tmpRawPassword = clientPassword+server_key;
            password = CodesUtil.bytes2Hex(CodesUtil.hash_hmac_sha256(tmpRawPassword, tmpRawPassword))+password;
            String decrypt = Aes256.decrypt(text.substring(64), password);

            String hiddenPassword = Base64.encodeToString(CodesUtil.bytes2Hex(CodesUtil.hash_hmac_sha256(decrypt, decrypt)).getBytes(US_ASCII), Base64.DEFAULT).substring(0, 64);
            if(clientPassword.equals(hiddenPassword)){
                return decrypt;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){

        // header加密
        String header = "{\"requestId\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"deviceId\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"}";
        String jsonEncodeHeader = jsonEncode(header, auth_key);
        System.out.println(jsonEncodeHeader);
        System.out.println(jsonDecode(jsonEncodeHeader, auth_key));

        // body加密
        String body = "{\"api\":\"start\",\"pubdate\":\"20190202\",\"param\":null}";
        String jsonEncodeBody = jsonEncode(body, body_key);
        System.out.println(jsonEncodeBody);
        System.out.println(jsonDecode(jsonEncodeBody, body_key));

        // 解密
        String encodeString = "NTljMGVmZWM2NWI3NjFiYjZiMTE2MzYyNTdkYzI3ZmQ2ZWU1MTA3Mzk3YmUyMWJjU2FsdGVkX1/GBF+PKdmPq2RGJnQ003QJagZrjhVB/IQ=";
        jsonDecode(encodeString, auth_key+body_key);
    }

}
