package inf.ufg.br.muralufg.model;

/**Esta classe model é responsavel
 * pela definição do objeto User
 * com seus metodos gets e sets *
 */
public class User {

    private String name;
    private String password;
    private String curso;

    public User() {
        //Construtor
    }

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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
