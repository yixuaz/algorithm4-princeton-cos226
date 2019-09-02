package commonutil;

import java.util.Random;

public class RandomStringBuilder {
    private static final Random r = new Random();
    public static String randomStringBase62(int len){
        String AB = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        return randomString(len, AB);
    }
    public static String randomStringLowerCase(int len){
        String AB = "abcdefghijklmnopqrstuvwxyz";
        return randomString(len, AB);
    }
    public static String randomStringEnglishLetter(int len){
        String AB = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return randomString(len, AB);
    }
    public static String randomString(int len, String base){
        StringBuilder sb = new StringBuilder(len);
        for( int i = 0; i < len; i++ )
            sb.append(base.charAt(r.nextInt(base.length())));
        return sb.toString();
    }
    public static String randomStringExtendASCII256(int len) {
        StringBuilder sb = new StringBuilder(len);
        for( int i = 0; i < len; i++ )
            sb.append((char)r.nextInt(256));
        return sb.toString();
    }

}
