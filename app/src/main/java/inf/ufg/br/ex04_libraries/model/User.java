package inf.ufg.br.ex04_libraries.model;

/**
 * Created by marceloquinta on 24/04/15.
 */
public class User {

    private String name;
    private String password;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
