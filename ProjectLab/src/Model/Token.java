package Model;

import java.security.SecureRandom;

public class Token {

    private String token;
    private String email;

    public static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // 2048 bit keys should be secure until 2030 - https://web.archive.org/web/20170417095741/https://www.emc.com/emc-plus/rsa-labs/historical/twirl-and-rsa-key-size.htm
    public static final int SECURE_TOKEN_LENGTH = 256;

    private static final SecureRandom random = new SecureRandom();

    private static final char[] symbols = CHARACTERS.toCharArray();

    private static final char[] buf = new char[SECURE_TOKEN_LENGTH];

    /**
     * Generate the next secure random token in the series.
     */
    public static String nextToken() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public Token(String token, String email)
    {
        this.token = token;
        this.email = email;
    }


    private void addTokenToDataBase(String token, String email)
    {
        //DB connect add token to database
    }

    public static boolean tokenExists(Token token)
    {
        //DB Connect
        // check in database
        return false;
    }

    public static void deleteToken(Token token)
    {
        if (tokenExists(token))
        {
            //DB connect
            //delete from database

        }

    }


    public static void checkExpiarationDate()
    {
        //DB connect
        //If expiration date excceeded => delete

    }
}
