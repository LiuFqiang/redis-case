package org.pigliu.rediscase;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.http.HttpUtil;
import com.sun.javafx.scene.traversal.Algorithm;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import sun.security.krb5.internal.crypto.Des;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.*;
import java.util.*;

/**
 * 加密解密测试
 *
 * @author liufuqiang
 * @since 2023/6/20
 */
public class encUtilsTest {

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
//        String url = "https://bkneng-opm-m.bkneng.com/audio/466575b22bed4872b52bc65af62009a5.mp3";
//        byte[] data = HttpUtil.downloadBytes(url);
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
//        System.out.println(1);
        HashMap<Object, Object> map = new HashMap<>(2, 1);
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
//        base64编码解码
//        byte[] encode = Base64.getEncoder().encode("liufuqiang".getBytes());
//        System.out.println(new String(encode));
//        byte[] decode = Base64.getDecoder().decode(encode);
//        System.out.println(new String(decode));

//        DES加密解密
//        DES des = new DES("1111111".getBytes());
//        byte[] bytes = des.encrypt("liufuqiang");
//        System.out.println(new String(bytes));
//        byte[] decrypt = des.decrypt(bytes);
//        System.out.println(new String(decrypt));

//        RSA加密解密
        RSA rsa = new RSA();
        PrivateKey privateKey = rsa.getPrivateKey();
        byte[] sssses = rsa.encrypt("ssss", KeyType.PublicKey);
        System.out.println(new String(sssses));
        byte[] decrypt1 = rsa.decrypt(sssses, KeyType.PrivateKey);
        System.out.println(new String(decrypt1));

        MD5 md5 = MD5.create();
        String digest = md5.digestHex("liufuqiang");

        System.out.println(digest);

        byte[] digest1 = md5.digest("liufuqiang", CharsetUtil.CHARSET_UTF_8);
        String s = HexUtil.encodeHexStr(digest1);
        System.out.println(s);
        System.out.println(new String(digest1, CharsetUtil.CHARSET_UTF_8));

        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update("liufuqiang".getBytes());
        System.out.println(new String(instance.digest()));

//        RSA rsa1 = new RSA();
//        System.out.println(rsa1.getPublicKey());
//        System.out.println(rsa1.getPrivateKey());
//
//        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DESede.getValue()).getEncoded();
//        byte[] key1 = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue()).getEncoded();
//        System.out.println(new String(Base64.getEncoder().encode(key)));
//        System.out.println(new String(Base64.getEncoder().encode(key1)));

//        String url = "https://111.txt";
//        String keyValue = "p5uAtaGtuu8O0On+blvVJqebgLWhrbrv";
//        byte[] bytes = HttpUtil.downloadBytes(url);
//        System.out.println(new String(bytes));
//        byte[] decode = Base64.getDecoder().decode(keyValue.getBytes());
//        System.out.println(new String(decode));
//        DES des = new DES(Mode.CBC, Padding.PKCS5Padding, decode, "111".getBytes());
//        byte[] decrypt = des.decrypt(bytes);
//        System.out.println(new String(decrypt));

//        final byte[] bytes = HttpUtil.downloadBytes("https://bkneng-opm-m.bkneng.com/audio/466575b22bed4872b52bc65af62009a5.mp3");
//        int resize = 10;
//        byte[] end = new byte[bytes.length * resize] ;
//        for (int i = 0; i < resize; i++) {
//            System.arraycopy(bytes, 0, end, bytes.length * i, bytes.length);
//        }
//
//        FileUtil.writeBytes(end, "/Users/liufuqiang/Downloads/padding.mp3");
//        System.out.println("end");
    }
}
