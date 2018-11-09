package Model;

public class LogInController {


    public static Token logIn(String email, String password)
    {
        if (UserAuthentication.userAuthenticated(email, password) == true)
        {
            return new Token(Token.nextToken(), email);
        }
        else
        {
            return null;
        }

    }
}
