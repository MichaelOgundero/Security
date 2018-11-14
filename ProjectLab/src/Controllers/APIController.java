package Controllers;
import Model.LogInController;
import Model.SignUpController;
import Model.Token;
import Model.UserAuthentication;
import com.sun.deploy.net.HttpResponse;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;

import javax.xml.ws.Response;

//SERVICE

public class APIController  {

    public static void logIn(String email, String password)
    {
        boolean responseFlag;
        if(UserAuthentication.newAccountAuthorization(email)==false)
        {
            responseFlag =UserAuthentication.userAuthenticated(email, password);
            if(responseFlag == true )
            {
                Token token = new Token(email, Token.nextToken());
                //I should return the token;
            }
            else
            {
                //I should return null => 401
            }
        }
        else
        {
            responseFlag = false; //logIn failed return 401
        }
    }


/*
    @POST
    public static Response logIn(String email, String password)
    {

        ResponseBuilder builder = Response.ok(LogInController.logIn(email, password));


        return builder.build();

    }

    @GET
    public static Response logOut(Token token)
    {
        ResponseBuilder builder = Response.ok(Token.deleteToken(token));

        return builder.build();
    }


    public static Response SignUp(String email, String password, String name, String lastName )
    {
        ResponseBuilder builder = Response.ok(SignUpController.signUp(email, password.toCharArray(), name, lastName));

        return builder.build();
    }

    public static Response deleteAccount()
    {
        //in case not found return 404

        return Response.status(Status.GONE).build();
    }
    */
}
