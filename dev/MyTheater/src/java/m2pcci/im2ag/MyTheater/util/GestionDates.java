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

/**
 *
 * @author antoine
 */
public class GestionDates {

    /**
     * 
     * @param sDate
     * @return date : permet de transformer un string en valeur de class Date sous la forme "yyyy/MM/dd"
     * @throws Exception 
     */
        
    public static Date stringToDate(String sDate) throws Exception {
        Date date = new SimpleDateFormat("yyyy/MM/dd").parse(sDate);
        return date;
    }

    
    /**
     * 
     * @param date
     * @return sDate permet de transformer une date de classe Date en string sous la forme "dd/MM/yyyy"
     */
    
        public static String dateToString(Date date) {
        String sDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        return sDate;
    }

        
    /**
     * 
     * @param sDate
     * @return une date de format string en changeant les séparateurs "-" par des "/"
     * @throws ParseException 
     */    
        
    public static String inversionFormatDateTiretVersSlash(String sDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd");
        return formatter1.format(formatter.parse(sDate));
    }
    
    
   /**
    * 
    * @param sDate
    * @return une date sous format string en changeant le format initial "yyyy/MM/dd" vers le format "dd/MM/yyyy"
    * @throws ParseException 
    */

    public static String inversionFormatDate(String sDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        return formatter1.format(formatter.parse(sDate));
    }
    
     /**
    * 
    * @param sDate
    * @return une date sous format string en changeant le format initial "dd/MM/yyyy" vers le format "yyyy/MM/dd"
    * @throws ParseException 
    */
    
     public static String inversionFormatDateInverse(String sDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd");
        return formatter1.format(formatter.parse(sDate));
    }
}
