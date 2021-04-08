/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.dao;

import m2pcci.im2ag.MyTheater.model.Representation;
import m2pcci.im2ag.MyTheater.model.Spectacle;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author marjo
 */
public class MyTheaterDAOTest {

    private DataSource ds = new DataSourceDeTest();

    public MyTheaterDAOTest() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of programmationSpectacle method, of class MyTheaterDAO.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testProgrammationSpectacle() throws Exception {
        System.out.println("programmationSpectacle");

        List<Representation> result = MyTheaterDAO.getRepresentations(ds);
        assertEquals(5, result.size());
    }
}
