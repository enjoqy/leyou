package org.junhi.auth.test;

import org.junhi.common.pojo.UserInfo;
import org.junhi.common.utils.JwtUtils;
import org.junhi.common.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "H:\\JavaTmpFile\\rsa\\rsa.pub";

    private static final String priKeyPath = "H:\\JavaTmpFile\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU2NDkxMjMzM30.E3pReTnI_W1TCCnr-4rUA6_hzsCsTahu_sVL_Slx7YcJ020X-w0XhOc0gcc-0NSOt7VAsv2suvEpsqPqcjk8fGQnK54e19e0SdauueDrrqGq1OsZjc9mMOiL2rvJBV_5oziaIOnPZxoveOGluK3entmoX6oNm5EgO-tn2rUsKXM";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}