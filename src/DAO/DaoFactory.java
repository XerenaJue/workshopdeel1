/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Interface.AdresDao;
import Interface.ArtikelDao;
import Interface.BestellingInterface;
import Interface.KlantDAO;

/**
 *
 * @author jeroenO
 */
public class DaoFactory {
    private String soort; 
    
    public DaoFactory() {
        
        this.soort = "SQL";
    }
    
    public void setSoort(String nieuweSoort) {
        //SQL or Json
        this.soort = nieuweSoort;
    }
    public String getSoort() {
        
        return this.soort;
    }
    
    public String switchSoort() {
        
        String oudeDaoSoort = this.getSoort();
        if (soort.equals("Json")) {
            
            this.setSoort("SQL");
        }
        else this.setSoort("Json");
        
        return oudeDaoSoort;
        
    }
    
    public AdresDao makeAdresDao() {
        
        if (soort.equals("Json")) {
           return new AdresDaoJsonNieuw(); 
        }
        else return new AdresDaoImpl();
    }
    public ArtikelDao makeArtikelDao() {
        
        if (soort.equals("Json")) {
           return new ArtikelDAOJson(); 
        }
        else return new ArtikelDAOSQL();
    }
    public BestellingInterface makeBestellingDao() {
        
        if (soort.equals("Json")) {
           return new BestellingDAOJson(); 
        }
        else return new BestellingDAO();
    }
    public KlantDAO makeKlantDao() {
        
        if (soort.equals("Json")) {
           return new KlantDaoJson(); 
        }
        else return new KlantDAOImpl();
    }
}
