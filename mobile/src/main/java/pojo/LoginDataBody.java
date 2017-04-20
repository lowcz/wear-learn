package pojo;

/**
 * Created by Michał on 4/19/2017.
 */

public class LoginDataBody {

    private String USERNAME;
    private String PASSWORD;


    public LoginDataBody(String username, String password) {
        this.USERNAME = username;
        this.PASSWORD = password;
    }

    public String getUserName() {
        return USERNAME;
    }

    public void setUserName(String name) {
        this.USERNAME = name;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public void setPassword(String name) {
        this.PASSWORD = name;
    }
}
