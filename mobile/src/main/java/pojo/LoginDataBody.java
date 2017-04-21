package pojo;

/**
 * Created by Michał on 4/19/2017.
 */

public class LoginDataBody {

    private String username;
    private String password;


    public LoginDataBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String name) {
        this.password = name;
    }
}
