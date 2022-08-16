package com.lagou.utils;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class Md5 {

    // 相当于在md5加密的过程中起这么个默认值，可以用也可以不用 此处用不到
    public final static  String md5key = "lagou";
    /**
     * MD5方法
     * @param text 明文密码
     * @param key 密钥 也就是上方声明出来的 md5key 可以用它，也可以手动传递一份
     *            只要调用 md5 方法和调用 verify 方法时传递的 key 值一致即可
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text, String key) throws Exception {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(text+key); // 加密成 32位 16进制的密文密码
        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }

    /**
     * MD5验证方法：验证明文密码经过 相同秘钥相同加密算法加密 之后的密文密码和我传入进来的这个密文密码是不是一致的
     * @param text 明文
     * @param key 密钥 和md5传入的key一致
     * @param md5 密文
     * @return true/false
     * @throws Exception
     * 登录的时候主要调用的方法就是这个方法
     */
    public static boolean verify(String text, String key, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = md5(text, key); // 明文密码转密文密码
        if(md5Text.equalsIgnoreCase(md5)) // 与数据库传进来的密文密码进行对比
        {
            System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {

        // 注册 明文转密文存入数据库 用户名：tom 密码：123456
        // 添加用户的时候，要将明文密码转换成密文密码
        String lagou = Md5.md5("123456", "lagou"); // 秘钥随便传只要 md5 方法和 verify 方法一致即可
        System.out.println(lagou);

        // 登录：用户名：tom 密码：123456
        // 1.根据前台传递过来的用户名 tom 先在 user表中查询出对应的密文密码
        // select * from user where username = tom

        // 2.调用 verify 方法进行密码的校验 相同秘钥相同算法的加密
        boolean verify = Md5.verify("123456", "lagou", "f00485441dfb815c75a13f3c3389c0b9");
        System.out.println(verify);
    }

}
