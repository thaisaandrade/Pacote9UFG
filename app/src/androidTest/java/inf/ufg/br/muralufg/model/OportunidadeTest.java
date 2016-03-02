package inf.ufg.br.muralufg.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by weslley on 02/03/16.
 */
public class OportunidadeTest {

    private Oportunidade oportunidade;

    @Before
    public void setUp() throws Exception {
        oportunidade = new Oportunidade();
    }

    @Test
    public void testSetId() throws Exception {
        oportunidade.setId(1);
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals(1, oportunidade.getId());
    }

    @Test
    public void testSetHoras() throws Exception {
        oportunidade.setHoras("12:30:45");
    }

    @Test
    public void testGetHoras() throws Exception {
        assertEquals("12:30:45", oportunidade.getHoras());
    }

    @Test
    public void testSetTitulo() throws Exception {
        oportunidade.setHoras("Título 1");
    }

    @Test
    public void testGetTitulo() throws Exception {
        assertEquals("Título 1", oportunidade.getTitulo());
    }

    @Test
    public void testSetCidade() throws Exception {
        oportunidade.setCidade("Goiânia");
    }

    @Test
    public void testGetCidade() throws Exception {
        assertEquals("Goiânia", oportunidade.getCidade());
    }

    @Test
    public void testSetBolsa() throws Exception {
        oportunidade.setBolsa("CNPQ");
    }

    @Test
    public void testGetBolsa() throws Exception {
        assertEquals("CNPQ", oportunidade.getBolsa());
    }

    @Test
    public void testSetValor() throws Exception {
        oportunidade.setValor("450");
    }

    @Test
    public void testGetValor() throws Exception {
        assertEquals("450", oportunidade.getValor());
    }

    @Test
    public void testSetLocal() throws Exception {
        oportunidade.setLocal("INF");
    }

    @Test
    public void testGetLocal() throws Exception {
        assertEquals("INF", oportunidade.getLocal());
    }

    @Test
    public void testSetEndereco() throws Exception {
        oportunidade.setEndereco("Rua abc");
    }

    @Test
    public void testGetEndereco() throws Exception {
        assertEquals("Rua abc", oportunidade.getEndereco());
    }

    @Test
    public void testSetTelefone() throws Exception {
        oportunidade.setTelefone("6283002828");
    }

    @Test
    public void testGetTelefone() throws Exception {
        assertEquals("6283002828", oportunidade.getTelefone());
    }

    @Test
    public void testSetEmail() throws Exception {
        oportunidade.setEmail("eu@nos.com");
    }

    @Test
    public void testGetEmail() throws Exception {
        assertEquals("eu@nos.com", oportunidade.getEmail());
    }

    @Test
    public void testSetDescricao() throws Exception {
        oportunidade.setDescricao("Descrição 123");
    }

    @Test
    public void testGetDescricao() throws Exception {
        assertEquals("Descrição 123", oportunidade.getDescricao());
    }

    @Test
    public void testSetHorario() throws Exception {
        oportunidade.setHorario("8:00 - 12:00");
    }

    @Test
    public void testGetHorario() throws Exception {
        assertEquals("8:00 - 12:00", oportunidade.getHorario());
    }

}