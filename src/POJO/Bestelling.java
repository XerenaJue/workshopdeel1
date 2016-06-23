/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import java.util.*;
import Annotations.*;
/**
 *
 * @author maurice
 */
@Entity
@Table(tableName = "bestelling")
public class Bestelling {
    @ID
    @Column
    private int bestelling_id;
    @Column            
    private int klant_id;
    @Column
    private List<ArtikelBestelling> artikelList;
    @Column
    private ArtikelBestelling bestellingArtikel;
    
    private HashMap<Integer,List<ArtikelBestelling>> hashmap;
    
 

    public int getBestelling_id(){
        return bestelling_id;
    }
    
    public void setBestelling_id(int bestelling_id){
        this.bestelling_id = bestelling_id;
    }
    
        public int getKlant_id(){
        return klant_id;
    }
    
    public void setKlant_id(int klant_id){
        this.klant_id = klant_id;
    }
    
    public void setArtikelBestellingList(List<ArtikelBestelling> artikelList){
        this.artikelList = artikelList;
    }
    
    public void setArtikelBestellingListJSON(HashMap<Integer,List<ArtikelBestelling>> hashmap){
        this.hashmap = hashmap;
    }
    
    public HashMap<Integer,List<ArtikelBestelling>> getArtikelBestellingListJSON(){
        return this.hashmap;
    }
    
    public List<ArtikelBestelling> getArtikelBestellingList(){
        return artikelList;
    }
    

    
           @Override
    public String toString() {
        return "Bestelling_ID: " + bestelling_id + ", klant ID: " + klant_id +", artikelenlijst: "+artikelList;

    }
    
        @Override
    public int hashCode() {
        return new HashCodeBuilder(37, 41).append(bestelling_id).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Bestelling))
            return false;
        if (obj == this)
            return true;

        Bestelling anderArtikel = (Bestelling) obj;
        return new EqualsBuilder().
            append(bestelling_id, anderArtikel.getBestelling_id()).isEquals();
    }
}
