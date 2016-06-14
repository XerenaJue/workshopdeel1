/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import POJO.ArtikelPOJO;
import java.util.List;

/**
 *
 * @author jeroen 
 */
public interface ArtikelDao {
    
     public ArtikelPOJO createArtikel(ArtikelPOJO artikel);
     public ArtikelPOJO readArtikel(ArtikelPOJO artikel );
     public ArtikelPOJO readArtikel(int artikelID);
     public void updateArtikel(ArtikelPOJO artikel );
     public List<ArtikelPOJO> findAlleArtikelen();
     public void deleteArtikel(ArtikelPOJO artikel);
     
    
}
