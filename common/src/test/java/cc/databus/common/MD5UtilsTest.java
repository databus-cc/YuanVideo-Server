package cc.databus.common;

import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5UtilsTest {
    @Test
    public void testMd5() {
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("MD5 not supported");
            e.printStackTrace();
            return;
        }

        String md5Str = "This is a test message";
        String jdkMd5 = Hex.encodeHexString(md5.digest(md5Str.getBytes()));
        String md5Val = MD5Utils.md5(md5Str);


        Assert.assertEquals(jdkMd5.toLowerCase(), md5Val.toLowerCase());
    }
}
