/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marjo
 */
public class GestionDatesTest {

    public GestionDatesTest() {
    }

    /**
     * Test of stringToDate method, of class GestionDates.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testStringToDate() throws Exception {
        System.out.println("stringToDate");
        String sDate = "2019/03/20";
        Date expResult = new SimpleDateFormat("yyyy/MM/dd").parse("2019/03/20");
        Date result = GestionDates.stringToDate(sDate);
        assertEquals(expResult, result);
    }

    /**
     * Test of dateToString method, of class GestionDates.
     * @throws java.text.ParseException
     */
    @Test
    public void testDateToString() throws ParseException {
        System.out.println("dateToString");
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("20/03/2019");
        String expResult = "20/03/2019";
        String result = GestionDates.dateToString(date);
        assertEquals(expResult, result);
    }

    /**
     * Test of inversionFormatDateTiretVersSlash method, of class GestionDates.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testInversionFormatDateTiretVersSlash() throws Exception {
        System.out.println("inversionFormatDateTiretVersSlash");
        String sDate = "2019-03-20";
        String expResult = "2019/03/20";
        String result = GestionDates.inversionFormatDateTiretVersSlash(sDate);
        assertEquals(expResult, result);
    }

    /**
     * Test of inversionFormatDate method, of class GestionDates.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testInversionFormatDate() throws Exception {
        System.out.println("inversionFormatDate");
        String sDate = "2019/03/20";
        String expResult = "20/03/2019";
        String result = GestionDates.inversionFormatDate(sDate);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of inversionFormatDateInverse method, of class GestionDates.
     * 
     * @throws ParseException 
     */
    @Test
    public void inversionFormatDateInverse() throws ParseException {
        System.out.println("inversionFormatDateInverse");
        String sDate = "20/03/2019";
        String expResult = "2019/03/20";
        String result = GestionDates.inversionFormatDateInverse(sDate);
        assertEquals(expResult, result);
    }
}
