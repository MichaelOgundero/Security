package Model;

public class LogOutController {
    //true = successful log out
    //false = token was not authenticated
    public static boolean logOut(Token token)
    {
        if (Token.tokenExists(token))
        {
            Token.deleteToken(token);
            return true;
        }
        else
        {
            return false;
        }

    }

}
