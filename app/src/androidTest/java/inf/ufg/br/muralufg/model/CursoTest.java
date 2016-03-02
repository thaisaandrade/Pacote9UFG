package inf.ufg.br.muralufg.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by weslley on 02/03/16.
 */
public class CursoTest {

    private Curso curso;

    @Before
    public void setUp() throws Exception {
        curso = new Curso(1, "Weslley");
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals(curso.getId(), 2);
    }

    @Test
    public void testSetId() throws Exception {
        curso.setId(3);
        assertEquals(3, curso.getId());
    }

    @Test
    public void testGetNome() throws Exception {
        assertEquals(curso.getNome(), "Weslley");
    }

    @Test
    public void testSetNome() throws Exception {
        curso.setNome("Construção de Software");
        assertEquals("Construção de Software", curso.getNome());
    }
}