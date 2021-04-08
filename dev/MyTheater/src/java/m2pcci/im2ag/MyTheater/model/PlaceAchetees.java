/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.model;

/**
 *
 * @author marjo
 */
public class PlaceAchetees extends Place{
    
    private final String ReductionProfil;

      

    public PlaceAchetees(Integer numeroRang, Integer numeroPlace, Integer numeroZone,String ReductionProfil) {
        super(numeroRang, numeroPlace, numeroZone);
        this.ReductionProfil = ReductionProfil;
    }
    
     public String getReductionProfil() {
        return ReductionProfil;
    }

    @Override
    public String toString() {
        return "PlaceAchetees{" + "ReductionProfil=" + ReductionProfil + '}';
    }
    
    
   
    
    
    
}
