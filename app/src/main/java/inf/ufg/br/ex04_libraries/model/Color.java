package inf.ufg.br.ex04_libraries.model;

/**
 * Created by marceloquinta on 24/04/15.
 */
public class Color {

    private String nome;
    private String hex;

    public Color(String nome, String hex) {
        this.nome = nome;
        this.hex = hex;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }
}
