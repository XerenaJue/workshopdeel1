/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

/**
 *
 * @author maurice
 */
public class Bestelling {
    private int bestelling_id, klant_id, artikel_aantal, artikel2_aantal, artikel3_aantal;
    
   
            
    
    
    public int getBestelling_id(){
        return bestelling_id;
    }
    
    public void setBestellingID(int bestelling_id){
        this.bestelling_id = bestelling_id;
    }
    
        public int getKlant_id(){
        return klant_id;
    }
    
    public void setKlant(int klant_id){
        this.klant_id = klant_id;
    }
    
        public int getArtikel_aantal(){
        return artikel_aantal;
    }
    
    public void setAantalArtikel1(int artikel_aantal){
        this.artikel_aantal = artikel_aantal;
    }
    
        public int getAantalArtikel2(){
        return artikel2_aantal;
    }
    
    public void setAantalArtikel2(int artikel2_aantal){
        this.artikel2_aantal = artikel2_aantal;
    }
        public int getAantalArtikel3(){
        return artikel3_aantal;
    }
    
    public void setAantalArtikel3(int artikel3_aantal){
        this.artikel3_aantal = artikel3_aantal;
    }
    
           @Override
    public String toString() {
        return "Bestelling_ID: " + bestelling_id + ", klant ID: " + klant_id +",artikel aantal: " +artikel_aantal +
                ", artikel 2 aantal: " + artikel2_aantal + "artikel 3 aantal: " + artikel3_aantal;

    } 
}
