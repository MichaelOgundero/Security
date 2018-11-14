package Model;

public class SignUpController {

    //-1 if email exists
    //1 if new user signed up
    public static int signUp(String email, char[] password, String name, String lastName)
    {
        //en effet, je peux changer le check
        // cela veut dire que je teste UserAuthentication.newAccountAuthorization(email) fel APIController
        if (UserAuthentication.newAccountAuthorization(email))
        {

            byte[] salt = Password.getNextSalt();
            char[] hashedPassword = Password.hash(password, salt);

            User user = new User(name, lastName, email, hashedPassword, salt);
            User.addNewUserToDataBase(user);

            return 1;
        }
        else
        {

            return -1;
        }

    }
}
