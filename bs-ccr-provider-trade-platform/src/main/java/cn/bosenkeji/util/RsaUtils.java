package cn.bosenkeji.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author CAJR
 * @create 2019/9/20 15:57
 */
public class RsaUtils {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 获取公钥的key
     */
    public static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    public static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * RSA 位数 如果采用2048 上面最大加密和最大解密则须填写:  245 256
     */
    private static final int INITIALIZE_LENGTH = 1024;

    private static final String PUBLIC_KEY_FILE_NAME = "publicKey.keystore";

    public static final String PRIVATE_KEY_FILE_NAME = "privateKey.keystore";

    private static final String ACCESS_KEY = "LTAI4Fd1zNzk8YdGKrgF5ohp";

    private static final String SECRET_KEY = "aja6ZkZUeA11ZroIdOADMQRKzhSjOJ";

//    private static final String ACCESS_KEY = "LTAI4FiW7ykVpsp1C3M2EBzg";
//
//    private static final String SECRET_KEY = "hZZmISbQzKUucNPe2woxXFYa3aplaP";

//    private static final String END_POINT = "oss-cn-shenzhen.aliyuncs.com";

    private static final String END_POINT = "oss-cn-shenzhen-internal.aliyuncs.com";

//    private  static final String BUCKET_NAME = "bs-follow";

    private  static final String BUCKET_NAME = "bs-follow-prod";


    /**
     * 生成密钥对
     * @return 返回密钥对哈希表
     * @throws Exception
     */
    public static Map<String,String> genKeyPair() throws Exception {
        Map<String,String> keyMap = new HashMap<>(2);
        String publicKey = null,privateKey = null;

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(INITIALIZE_LENGTH);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        byte[] encoded = keyPair.getPrivate().getEncoded();
        PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(encoded);

        ASN1Encodable encodable = privateKeyInfo.parsePrivateKey();
        ASN1Primitive primitive = privateKeyInfo.toASN1Primitive();
        byte[] privateKeyPKCS1 = primitive.getEncoded();
        PemObject pemObject = new PemObject("PRIVATE KEY",privateKeyPKCS1);
        try(StringWriter stringWriter = new StringWriter()){
            try (PemWriter pemWriter = new PemWriter(stringWriter)) {
                pemWriter.writeObject(pemObject);
                pemWriter.flush();
                privateKey = stringWriter.toString();
            }
    }

        encoded = keyPair.getPublic().getEncoded();
        SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(encoded);
        primitive = publicKeyInfo.toASN1Primitive();
        byte[] publicPKCS1 = primitive.getEncoded();
        pemObject = new PemObject("PUBLIC KEY",publicPKCS1);

        try(StringWriter stringWriter = new StringWriter()){
            try (PemWriter pemWriter = new PemWriter(stringWriter)) {
                pemWriter.writeObject(pemObject);
                pemWriter.flush();
                publicKey =stringWriter.toString();
            }
        }

        keyMap.put(PUBLIC_KEY,publicKey);
        keyMap.put(PRIVATE_KEY,privateKey);

        return keyMap;
    }

    /**
     * 初始化密钥对
     * @return
     * @throws Exception
     */
    public static Map<String,Object> initKey() {
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //初始化密钥生成器
        keyPairGenerator.initialize(INITIALIZE_LENGTH);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        System.out.println("系数："+publicKey.getModulus()+"  加密指数："+publicKey.getPublicExponent());
        //私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        System.out.println("系数："+privateKey.getModulus()+"解密指数："+privateKey.getPrivateExponent());

        Map<String,Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY,publicKey);
        keyMap.put(PRIVATE_KEY,privateKey);

        return keyMap;
    }

    /**
     * 利用私钥对数据生成数字签名
     * @param data 已加密数据
     * @param privateKey 私钥（Base64 编码）
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception{
        byte[] keyBytes = Base64.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);

        return Base64.toBase64String(signature.sign());
    }

    /**
     * 校验数据签名
     * @param data 已加密数据
     * @param publicKey 公钥（Base64 编码）
     * @param sign 私钥对数据生成的数字签名
     * @return boolean
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decode(sign));
    }
    /**
     * 密钥加密
     * @param data 待加密的数据
     * @param key 密钥
     * @return 加密数据
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data,byte[] key) throws Exception {
        //取得私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE,privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 公钥加密
     * @param data 待加密数据
     * @param key 密钥
     * @return byte[] 加密数据
     * */
    public static byte[] encryptByPublicKey(byte[] data,byte[] key) throws Exception{

        //实例化密钥工厂
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);

        //数据加密
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     * @param data 待解密数据
     * @param key 密钥
     * @return byte[] 解密数据
     * */
    public static byte[] decryptByPrivateKey(byte[] data,byte[] key,int segmentSize) throws Exception{
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey=keyFactory.generatePrivate(pkcs8KeySpec);
        //数据解密
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        if (segmentSize >= 128){
            return cipherDoFinal(cipher,data,segmentSize);
        }
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     * @param keyMap 密钥map
     * @return byte[] 私钥
     * */
    public static byte[] getPrivateKey(Map<String,Object> keyMap){
        Key key=(Key)keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }
    /**
     * 取得公钥
     * @param keyMap 密钥map
     * @return byte[] 公钥
     * */
    public static byte[] getPublicKey(Map<String,Object> keyMap) {
        Key key=(Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

    /**
     *  把密钥保存到本地文件
     * @param publicKey 公钥
     * @param privateKey 私钥
     */
    public static void loadKeyPairToFile(String publicKey,String privateKey){
        try(
                FileWriter pubFw = new FileWriter(PUBLIC_KEY_FILE_NAME);
                FileWriter priFw = new FileWriter(PRIVATE_KEY_FILE_NAME);
                BufferedWriter pubBw = new BufferedWriter(pubFw);
                BufferedWriter priBw = new BufferedWriter(priFw)
        ) {
            pubBw.write(publicKey);
            priBw.write(privateKey);
            pubBw.flush();
            priBw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 把密钥上传到OSS
     */
    public static void loadKeyPairToOSS(String publicKey,String privateKey){
        OSS ossClient = new OSSClientBuilder().build(END_POINT,ACCESS_KEY,SECRET_KEY);
        loadKeyPairToFile(publicKey, privateKey);

        try {
            InputStream inputStreamPub = new FileInputStream(PUBLIC_KEY_FILE_NAME);
            InputStream inputStreamPri = new FileInputStream(PRIVATE_KEY_FILE_NAME);

            //设置存储类型与访问权限
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setObjectAcl(CannedAccessControlList.Private);

            ossClient.putObject(BUCKET_NAME,"key/"+PUBLIC_KEY_FILE_NAME,inputStreamPub);
            ossClient.putObject(BUCKET_NAME,"key/"+PRIVATE_KEY_FILE_NAME,inputStreamPri,metadata);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
        }
    }

    /**
     * 从oss下载私钥到本地
     */
    public static void downloadPrivateKeyByOSS(){
        OSS ossClient = new OSSClientBuilder().build(END_POINT,ACCESS_KEY,SECRET_KEY);
        ossClient.getObject(new GetObjectRequest(BUCKET_NAME,"key/"+PRIVATE_KEY_FILE_NAME),new File(PRIVATE_KEY_FILE_NAME));
        ossClient.shutdown();
    }

    /**
     * 判断oss上的私钥是否存在
     * @return boolean
     */
    public static boolean checkPrivateKeyOnOSS(){
        OSS ossClient = new OSSClientBuilder().build(END_POINT,ACCESS_KEY,SECRET_KEY);

        //判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；如果为false，则考虑302重定向或镜像。
        boolean found = ossClient.doesObjectExist(BUCKET_NAME,"key/"+PRIVATE_KEY_FILE_NAME);
        ossClient.shutdown();

        return found;
    }

    /**
     * 判断oss上的密钥对是否存在
     * @return boolean
     */
    public static boolean checkKeyPairOnOSS(){
        OSS ossClient = new OSSClientBuilder().build(END_POINT,ACCESS_KEY,SECRET_KEY);

        //判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；如果为false，则考虑302重定向或镜像。
        boolean foundPub = ossClient.doesObjectExist(BUCKET_NAME,"key/"+PRIVATE_KEY_FILE_NAME);
        boolean foundPri = ossClient.doesObjectExist(BUCKET_NAME,"key/"+PRIVATE_KEY_FILE_NAME);
        ossClient.shutdown();

        return foundPub && foundPri;
    }

    /**
     * 检查文件是否为空
     * @param fileName 文件名
     * @return 空 true  不空 false
     */
    public static boolean checkFileIsNull(String fileName){
        boolean isNull = false;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            if (bufferedReader.readLine() == null){
                isNull = true;
            }
        } catch (FileNotFoundException e) {
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        return isNull;
    }

    /**
     * 从文件中输入流中加载公钥
     * @return 公钥
     * @throws Exception
     */
    public static String loadPublicKeyByFile() throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(PUBLIC_KEY_FILE_NAME))) {

            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }
    /**
     * 从文件中输入流中加载私钥
     * @return 私钥
     * @throws Exception
     */
    public static String loadPrivateKeyByFile() throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(PRIVATE_KEY_FILE_NAME))) {

            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("私钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     *  公钥格式处理
     * @param publicKey
     * @return
     */
    private static  String formatPublicKey(String publicKey){
       return publicKey.replaceAll("-----BEGIN PUBLIC KEY-----","").replaceAll("\n","").replaceAll("-----END PUBLIC KEY-----","").trim();
    }

    /**
     * 私钥格式处理
     * @param privateKey
     * @return
     */
    public static  String formatPrivateKey(String privateKey){
        return privateKey.replaceAll("-----BEGIN PRIVATE KEY-----","").replaceAll("\n","").replaceAll("-----END PRIVATE KEY-----","").trim();
    }
    /**
     * 分段大小
     * @param cipher
     * @param srcBytes
     * @param segmentSize
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] cipherDoFinal(Cipher cipher, byte[] srcBytes, int segmentSize)
            throws  IOException, BadPaddingException, IllegalBlockSizeException {
        if (segmentSize <= 0){
            throw new RuntimeException("分段大小必须大于0");
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int inputLen = srcBytes.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > segmentSize) {
                cache = cipher.doFinal(srcBytes, offSet, segmentSize);
            } else {
                cache = cipher.doFinal(srcBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * segmentSize;
        }
        byte[] data = out.toByteArray();
        out.close();
        return data;
    }

    public static void main(String[] args) throws Exception {

//        Map<String,String> keyMaps = RsaUtils.genKeyPair();
//        System.out.println(keyMaps.get(PUBLIC_KEY));
//        System.out.println(keyMaps.get(PRIVATE_KEY));

//        //初始化密钥
//        //生成密钥对
//        Map<String,Object> keyMap=RsaUtils.initKey();
//
//        //公钥
//        byte[] publicKey = RsaUtils.getPublicKey(keyMap);
//        //私钥
//        byte[] privateKey = RsaUtils.getPrivateKey(keyMap);
//        System.out.println("公钥："+ Base64.toBase64String(publicKey));
//        System.out.println("私钥："+ Base64.toBase64String(privateKey));

//        RsaUtils.loadKeyPairToFile(keyMaps.get(PUBLIC_KEY),keyMaps.get(PRIVATE_KEY));
//
        String publicKeyStr = RsaUtils.loadPublicKeyByFile();
        String privateKeyStr = RsaUtils.loadPrivateKeyByFile();

        String pubKeyFormat = RsaUtils.formatPublicKey(publicKeyStr);
        String priKeyFormat = RsaUtils.formatPrivateKey(privateKeyStr);


        System.out.println("================密钥对构造完毕,甲方将公钥公布给乙方，开始进行加密数据的传输=============");
        String accessKey = "28edf12c-cf599498-5b76183e-dqnh6tvdf3";
        String secretKey = "967b59f5-c10d9f96-63126899-a0cc0";
        String str = "7368a4f8-8010201b-9a2f7ddf-dab4c45e6f_a0994100-9d8f721b-73df1b4d-43f29";
        System.out.println("===========甲方向乙方发送加密数据==============");
        System.out.println("原文:"+str);
        //公钥加密
        byte[] code = RsaUtils.encryptByPublicKey(str.getBytes(StandardCharsets.UTF_8),Base64.decode(pubKeyFormat));
        System.out.println("甲方 使用乙方公钥加密后的数据："+Base64.toBase64String(code));
        System.out.println("===========乙方使用甲方提供的公钥对数据进行解密==============");
        //私钥解密
        byte[] decode = RsaUtils.decryptByPrivateKey(code,Base64.decode(priKeyFormat),RsaUtils.MAX_DECRYPT_BLOCK);
        System.out.println("乙方解密后的数据："+new String(decode)+"");

//        //私钥签名
//        String sign = RsaUtils.sign(code,Base64.toBase64String(privateKey));
//        System.out.println("sign ==> "+sign);
//
//        //公钥验证签名
//        System.out.println(RsaUtils.verify(code,Base64.toBase64String(publicKey),sign));
//
//        System.out.println(genKeyPair().get(PUBLIC_KEY));
//        System.out.println(genKeyPair().get(PRIVATE_KEY));
//        System.out.println("公钥为："+RsaUtils.loadPublicKeyByFile());
//        System.out.println(RsaUtils.checkFile("publicKey.keystore"));
//        System.out.println(RsaUtils.checkFile("privateKey.keystore"));
//        System.out.println("私钥为："+RsaUtils.loadPrivateKeyByFile());
//
//        downloadPrivateKeyByOSS();
//        System.out.println(checkPrivateKeyOnOSS());
    }
}
