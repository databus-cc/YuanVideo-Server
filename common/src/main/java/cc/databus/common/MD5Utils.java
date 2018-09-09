package cc.databus.common;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;

public class MD5Utils {

    /**
     * Calculate the md5 of given string
     *
     * @param str given string
     * @return md5 of given string
     */
    public static String md5(String str) {
        Objects.requireNonNull(str, "Given string is null");
        return DigestUtils.md5Hex(str);
    }
}
