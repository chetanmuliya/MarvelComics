package cm.velotio.marvelcomics.constant;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class Util {
    public static final String BASE_URL = "https://gateway.marvel.com";
    public static final String API_KEY = "22ff92235b49d2df3cc7b94ef810d476";
    public static final String PUB_KEY = "22ff92235b49d2df3cc7b94ef810d476";
    public static final String PRI_KEY = "cfec79030b8096925168199e942d455b928a474a";
    public static final int LIMIT = 20;

    public static String ts = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

    public static String hash(){
        String input = ts+PRI_KEY+PUB_KEY;
        return getMd5(input);
    }

    public static String getMd5(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
