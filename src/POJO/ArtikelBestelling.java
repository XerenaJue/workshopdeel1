/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

/**
 *
 * @author maurice
 */
public class ArtikelBestelling {
    private ArtikelPOJO artikel;
    private int aantal_artikelen;
    
    public ArtikelPOJO getArtikelPojo(){
        return artikel;
    }

    public void setArtikelPojo(ArtikelPOJO artikel){
        this.artikel = artikel;
    }
    
    public int getArtikelenAantal(){
        return aantal_artikelen;
    }
    
    public void setArtikelenAantal(int aantal_artikelen){
        this.aantal_artikelen = aantal_artikelen;
    }
    
    @Override
    public String toString(){
        return "ArtikelBestellingPOJO: " + artikel.toString() + ". aantal artikelen: " + aantal_artikelen; 
    }
}
