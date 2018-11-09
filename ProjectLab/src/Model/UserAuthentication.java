package Model;

public class UserAuthentication {

    //false user non authenticated
    // true authenticated
    public static boolean userAuthenticated(String email, String password)
    {
        char[] passwordToCheck = password.toCharArray();
        //DB connect
        //retrieve password
        //SQL STATEMENT : SELECT password, salt FROM users
        //                WHERE email = 'email';
        char[] user_password= null;

        //retrieve salt;
        byte[] user_salt = null;

        if(Password.isExpectedPassword(passwordToCheck, user_salt, user_password))
        {
            return true;
        }
        else
            return false;
    }

    public static Boolean userAuthorized(Token token)
    {
        if (Token.tokenExists(token))
        {
            return true;
        }
        else
            return false;
    }


    public static boolean newAccountAuthorization(String email)
    {
        //DB connection
        //SELECT count(email) FROM users
        //WHERE email = 'email'
        // if count(email) == 0
        //return true;
        //else return false;

        return false;
    }
}
