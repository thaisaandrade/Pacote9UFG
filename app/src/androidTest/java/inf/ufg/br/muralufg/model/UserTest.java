package inf.ufg.br.muralufg.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by weslley on 02/03/16.
 */
public class UserTest {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User("Weslley");
    }

    @Test
    public void testSetName() throws Exception {
        user.setName("Weslley M. A.");
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Weslley M. A.", user.getName());
    }

    @Test
    public void testSetPassword() throws Exception {
        user.setPassword("3lkjfdu4");
    }

    @Test
    public void testGetPassword() throws Exception {
        assertEquals("3lkjfdu4", user.getPassword());
    }

    @Test
    public void testSetCurso() throws Exception {
        user.setCurso("Engenharia de Software");
    }

    @Test
    public void testGetCurso() throws Exception {
        assertEquals("Engenharia de Software", user.getCurso());
    }


}