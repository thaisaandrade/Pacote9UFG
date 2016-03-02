package inf.ufg.br.muralufg.model;

/**Esta classe model é responsavel
 * pela definição do objeto curso
 * com seus metodos gets e sets *
 */
public class Curso {

    private int id;
    private String nome;

    public Curso() {
        //Construtor
    }

    public Curso(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
