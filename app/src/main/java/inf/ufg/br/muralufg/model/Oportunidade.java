package inf.ufg.br.muralufg.model;

import java.io.Serializable;

/**
 * Created by Marla Aragão on 01/07/2015.
 */
public class Oportunidade implements Serializable {

    private int id;
    private String horas;
    private String titulo;
    private String cidade;
    private String bolsa;
    private String valor;
    private String local;
    private String endereco;
    private String telefone;
    private String email;
    private String descricao;
    private String horario;

    public Oportunidade() {
        //Construtor
    }

    public Oportunidade(int id, String horas, String titulo, String cidade
            , String bolsa, String valor, String local, String endereco
            , String telefone, String email, String descricao, String horario) {
        this.id = id;
        this.horas = horas;
        this.titulo = titulo;
        this.cidade = cidade;
        this.bolsa = bolsa;
        this.valor = valor;
        this.local = local;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.descricao = descricao;
        this.horario = horario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBolsa() {
        return bolsa;
    }

    public void setBolsa(String bolsa) {
        this.bolsa = bolsa;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
