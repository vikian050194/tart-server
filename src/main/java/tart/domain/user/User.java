package tart.domain.user;

public class User {

    String id;
    String login;
    String password;

    public User() {

    }

    public User(String id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String value) {
        id = value;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String value) {
        login = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        password = value;
    }
}
