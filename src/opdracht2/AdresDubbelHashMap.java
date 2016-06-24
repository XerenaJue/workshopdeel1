/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;

import POJO.Adres;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jeroenO
 */
public class AdresDubbelHashMap {
    private HashMap<Integer, Adres> idAlsKey;
    private HashMap<String, Adres> postcodeAlsKey;
    
    private Integer size = 0;
    
    public AdresDubbelHashMap() {
        idAlsKey = new HashMap<>();
        postcodeAlsKey =  new HashMap<>();       
        
    }
    
    public Adres add(Adres adres) {
        
        adres.setAdresID(++size);
        String postcode = adres.getPostcode();
        int huisnr = adres.getHuisnummer();
        postcode += huisnr;
      //  postcode += adres.getToevoeging();
        idAlsKey.put(adres.getAdresID(), adres);
        postcodeAlsKey.put(postcode, adres);
        
        return adres;
    }
    
    public List<Adres> get(List<Integer> adresIDs) {
        List adressen = new ArrayList<>();
        for (Integer i : adresIDs) {
            adressen.add(this.get(i));
        }
        return adressen;
    }
        
    public Adres get(int adresID) {
        
        Adres adres = idAlsKey.get(adresID);
        
        return adres;
    }
    
        
    public Adres get(String postcodeHuisnr) {
        
        Adres adres = postcodeAlsKey.get(postcodeHuisnr);
        
        return adres;
    }
    
    public void remove(int adresID) {
        
        Adres adres = idAlsKey.get(adresID);
        String postcode = adres.getPostcode();
        postcode += adres.getHuisnummer();
       // postcode += adres.getToevoeging();
        
        postcodeAlsKey.put(postcode, null);
        idAlsKey.put(adresID, null);
        
    }
    
    public void remove(String  postcodeHuisnr) {
        
        Adres adres = postcodeAlsKey.get(postcodeHuisnr);
        Integer adresID = adres.getAdresID();
        postcodeAlsKey.put(postcodeHuisnr, null);
        idAlsKey.put(adresID, null);
        
    }
    
    public void update(Adres adresNieuw) {
        
        idAlsKey.put(adresNieuw.getAdresID(), adresNieuw);
        String postcode = adresNieuw.getPostcode();
        postcode += adresNieuw.getHuisnummer();
        postcodeAlsKey.put(postcode, adresNieuw);
        
        
    }
    
    public List<Adres> getValues() {
        ArrayList<Adres> adressen = new ArrayList<>();
        adressen.addAll(idAlsKey.values());
        return adressen;
    }
    
    
    public HashMap<Integer, Adres> getPerID() {
        return idAlsKey;
    }
    public void  setPerID(HashMap<Integer, Adres> perID) {
        this.idAlsKey = perID;
    }
    public HashMap<Integer, Adres> getPerPost() {
        return idAlsKey;
    }
    public void  setPerPost(HashMap<Integer, Adres> perPost) {
        this.idAlsKey = perPost;
    }
    public int getSize() {
        return size;
    }
    public void setSize(Integer is) {
        size = is;
    }
}
