/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.model;

/**
 *
 * @author bannouny
 */
public class Spectacle {

    private final Integer numeroSpec;
    private final String nomSpec;
    private final String nomGenre;

    public Spectacle(Integer numeroSpec, String nomSpec,String nomGenre) {
        this.numeroSpec = numeroSpec;
        this.nomSpec = nomSpec;
        this.nomGenre = nomGenre;
    }

    public String getNomGenre() {
        return nomGenre;
    }

    public Integer getNumeroSpec() {
        return numeroSpec;
    }

    public String getNomSpec() {
        return nomSpec;
    }

    @Override
    public String toString() {
        return "Spectacle{" + "numeroSpec=" + numeroSpec + ", nomSpec=" + nomSpec + ", nomGenre=" + nomGenre + '}';
    }

    

}
