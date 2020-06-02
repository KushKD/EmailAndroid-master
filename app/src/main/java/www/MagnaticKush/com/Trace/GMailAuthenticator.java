package www.MagnaticKush.com.Trace;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Kush.Dhawan
 * @project EmailAndroid-master
 * @Time 29, 05 , 2020
 */
public class GMailAuthenticator extends Authenticator {
    String user;
    String pw;
    public GMailAuthenticator (String username, String password)
    {
        super();
        this.user = username;
        this.pw = password;
    }
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(user, pw);
    }
}
