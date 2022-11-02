package com.xiaozheng.encrypt.utils;

import java.io.ByteArrayOutputStream;
import java.lang.Character.UnicodeBlock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * What -- 该类处理字符串的转码，可以处理字符串到二进制字符、16进制字符、unicode字符、base64字符之间的转换
 * <br>
 * Describe --
 * <br>
 *
 * @author 小政同学    QQ:xiaozheng666888@qq.com
 * @ClassName: TranscodeUtil
 * @CreateTime 2022/11/2 22:59
 */
public class MzTranscodeUtils {

    /**
     * 将字符串转换成unicode码
     *
     * @param str 要转码的字符串
     * @return 返回转码后的字符串
     */
    public static String strToUnicodeStr(String str) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            UnicodeBlock ub = UnicodeBlock.of(ch);
            if (ub == UnicodeBlock.BASIC_LATIN) {//英文及数字等
                buffer.append(ch);
            } else if ((int) ch > 255) {
                buffer.append("\\u" + Integer.toHexString((int) ch));
            } else {
                buffer.append("\\" + Integer.toHexString((int) ch));
            }
        }
        return buffer.toString();
    }

    /**
     * 将unicode码反转成字符串
     *
     * @param unicodeStr unicode码
     * @return 返回转码后的字符串
     */
    public static String unicodeStrToStr(String unicodeStr) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(unicodeStr);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            unicodeStr = unicodeStr.replace(matcher.group(1), ch + "");
        }
        return unicodeStr;
    }

    /**
     * 将字符串通过base64转码
     *
     * @param str 要转码的字符串
     * @return 返回转码后的字符串
     */
    public static String strToBase64Str(String str) {
        return new String(encode(str.getBytes()));
    }

    /**
     * 将base64码反转成字符串
     *
     * @param base64Str base64码
     * @return 返回转码后的字符串
     */
    public static String base64StrToStr(String base64Str) {
        char[] dataArr = new char[base64Str.length()];
        base64Str.getChars(0, base64Str.length(), dataArr, 0);
        return new String(decode(dataArr));
    }

    /**
     * 将字节数组通过base64转码
     *
     * @param byteArray 字节数组
     * @return 返回转码后的字符串
     */
    public static String byteArrayToBase64Str(byte byteArray[]) {
        return new String(encode(byteArray));
    }

    /**
     * 将base64码转换成字节数组
     *
     * @param base64Str base64码
     * @return 返回转换后的字节数组
     */
    public static byte[] base64StrToByteArray(String base64Str) {
        char[] dataArr = new char[base64Str.length()];
        base64Str.getChars(0, base64Str.length(), dataArr, 0);
        return decode(dataArr);
    }

    /**
     * 将一个字节数组转换成base64的字符数组
     *
     * @param data 字节数组
     * @return base64字符数组
     */
    private static char[] encode(byte[] data) {
        char[] out = new char[((data.length + 2) / 3) * 4];
        for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
            boolean quad = false;
            boolean trip = false;
            int val = (0xFF & (int) data[i]);
            val <<= 8;
            if ((i + 1) < data.length) {
                val |= (0xFF & (int) data[i + 1]);
                trip = true;
            }
            val <<= 8;
            if ((i + 2) < data.length) {
                val |= (0xFF & (int) data[i + 2]);
                quad = true;
            }
            out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 1] = alphabet[val & 0x3F];
            val >>= 6;
            out[index + 0] = alphabet[val & 0x3F];
        }
        return out;
    }

    /**
     * 将一个base64字符数组解码成一个字节数组
     *
     * @param data base64字符数组
     * @return 返回解码以后的字节数组
     */
    private static byte[] decode(char[] data) {
        int len = ((data.length + 3) / 4) * 3;
        if (data.length > 0 && data[data.length - 1] == '=') --len;
        if (data.length > 1 && data[data.length - 2] == '=') --len;
        byte[] out = new byte[len];
        int shift = 0;
        int accum = 0;
        int index = 0;
        for (int ix = 0; ix < data.length; ix++) {
            int value = codes[data[ix] & 0xFF];
            if (value >= 0) {
                accum <<= 6;
                shift += 6;
                accum |= value;
                if (shift >= 8) {
                    shift -= 8;
                    out[index++] = (byte) ((accum >> shift) & 0xff);
                }
            }
        }
        if (index != out.length)
            throw new Error("miscalculated data length!");
        return out;
    }

    /**
     * base64字符集 0..63
     */
    static private char[] alphabet =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
                    .toCharArray();

    /**
     * 初始化base64字符集表
     */
    static private byte[] codes = new byte[256];

    static {
        for (int i = 0; i < 256; i++) codes[i] = -1;
        for (int i = 'A'; i <= 'Z'; i++) codes[i] = (byte) (i - 'A');
        for (int i = 'a'; i <= 'z'; i++) codes[i] = (byte) (26 + i - 'a');
        for (int i = '0'; i <= '9'; i++) codes[i] = (byte) (52 + i - '0');
        codes['+'] = 62;
        codes['/'] = 63;
    }

    /**
     * 16进制数字字符集
     */
    private static String hexString = "0123456789ABCDEF";

    /**
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     *
     * @param str 字符串
     * @return 返回16进制字符串
     */
    public static String strToHexStr(String str) {
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /**
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     *
     * @param hexStr 16进制字符串
     * @return 返回字符串
     */
    public static String hexStrToStr(String hexStr) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                hexStr.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < hexStr.length(); i += 2)
            baos.write((hexString.indexOf(hexStr.charAt(i)) << 4 | hexString
                    .indexOf(hexStr.charAt(i + 1))));
        return new String(baos.toByteArray());
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param byteArray 要转码的字节数组
     * @return 返回转码后的16进制字符串
     */
    public static String byteArrayToHexStr(byte byteArray[]) {
        StringBuffer buffer = new StringBuffer(byteArray.length * 2);
        int i;
        for (i = 0; i < byteArray.length; i++) {
            if (((int) byteArray[i] & 0xff) < 0x10)//小于十前面补零
                buffer.append("0");
            buffer.append(Long.toString((int) byteArray[i] & 0xff, 16));
        }
        return buffer.toString();
    }

    /**
     * 将16进制字符串转换成字节数组
     *
     * @param hexStr 要转换的16进制字符串
     * @return 返回转码后的字节数组
     */
    public static byte[] hexStrToByteArray(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] encrypted = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);//取高位字节
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);//取低位字节
            encrypted[i] = (byte) (high * 16 + low);
        }
        return encrypted;
    }

    /**
     * 将字符串转换成二进制字符串，以空格相隔
     *
     * @param str 字符串
     * @return 返回二进制字符串
     */
    public static String strToBinStr(String str) {
        char[] chars = str.toCharArray();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            result.append(Integer.toBinaryString(chars[i]));
            result.append(" ");
        }
        return result.toString();
    }

    /**
     * 将二进制字符串转换成Unicode字符串
     *
     * @param binStr 二进制字符串
     * @return 返回字符串
     */
    public static String binStrToStr(String binStr) {
        String[] tempStr = strToStrArray(binStr);
        char[] tempChar = new char[tempStr.length];
        for (int i = 0; i < tempStr.length; i++) {
            tempChar[i] = binstrToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }

    /**
     * 将二进制字符串转换为char
     *
     * @param binStr 二进制字符串
     * @return 返回字符
     */
    private static char binstrToChar(String binStr) {
        int[] temp = binstrToIntArray(binStr);
        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += temp[temp.length - 1 - i] << i;
        }
        return (char) sum;
    }

    /**
     * 将初始二进制字符串转换成字符串数组，以空格相隔
     *
     * @param str 二进制字符串
     * @return 返回字符串数组
     */
    private static String[] strToStrArray(String str) {
        return str.split(" ");
    }

    /**
     * 将二进制字符串转换成int数组
     *
     * @param binStr 二进制字符串
     * @return 返回int数组
     */
    private static int[] binstrToIntArray(String binStr) {
        char[] temp = binStr.toCharArray();
        int[] result = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = temp[i] - 48;
        }
        return result;
    }
}
