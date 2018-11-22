package sec.model;

public class LogInController {
    public static Token logIn(String email, String password)
    {
        if (UserAuthentication.userAuthenticated(email, password) == true)
        {
            Token token = new Token(Token.nextToken(), email);
            token.addTokenToDataBase();
            return token;

        }
        else
        {
            return null;
        }

    }
}
