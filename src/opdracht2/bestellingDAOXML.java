/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht2;
import Interface.BestellingInterface;
import POJO.ArtikelBestelling;
import POJO.Bestelling;
import POJO.Klant;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
 
/**
 *
 * @author maurice
 */
public class bestellingDAOXML implements BestellingInterface {

    @Override
    public void addArtikelToBestelling(Bestelling bestelling, ArtikelBestelling artikelBestelling) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buildInsertStatement(Bestelling bestelling) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeConnectionStatement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Bestelling createBestelling(Klant klant) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createBestelling(Bestelling bestelling) throws SQLException {
		FileOutputStream fos;
		BufferedOutputStream bos;
		FileInputStream fis = null;
                Bestelling bestelling1 = new Bestelling();
                List<Bestelling> list = new ArrayList<Bestelling>();
                boolean stop = false;
                File file = new File("C:\\Users\\maurice\\Desktop\\Workshoptest1.xml");
    if (file.length() !=0){            
        try {
            fis = new FileInputStream("C:\\Users\\maurice\\Desktop\\Workshoptest1.xml");
            BufferedInputStream bis = new BufferedInputStream(fis);       
            XMLDecoder xmlDecoder = new XMLDecoder(bis);
            do{
                try{
                    list = (ArrayList<Bestelling>)xmlDecoder.readObject();
                }
                catch(ArrayIndexOutOfBoundsException e){
                    stop = true;
                }
            }while(!stop);
                    } catch (FileNotFoundException ex) {
            Logger.getLogger(bestellingDAOXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
                
            list.add(bestelling);    
        try {
            fos = new FileOutputStream("C:\\Users\\maurice\\Desktop\\Workshoptest1.xml");
            bos = new BufferedOutputStream(fos);


                    try (XMLEncoder xmlEncoder = new XMLEncoder(bos)) {
                        xmlEncoder.writeObject(list);
                    }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(bestellingDAOXML.class.getName()).log(Level.SEVERE, null, ex);
        }        
    } //To change body of generated methods, choose Tools | Templates.
    

    @Override
    public void createCS(String SQLStatement) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createCSR(String SQLStatement) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteArtikelFromBestelling(ArtikelBestelling artikelBestelling, int bestel_id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteBestelling(Bestelling bestelling) throws SQLException {
		FileOutputStream fos;
		BufferedOutputStream bos;
		FileInputStream fis = null;
                Bestelling bestelling1 = new Bestelling();
                List<Bestelling> list = new ArrayList<>();
                boolean stop = false;
                File file = new File("C:\\Users\\maurice\\Desktop\\Workshoptest1.xml");
    if (file.length() !=0){            
        try {
            fis = new FileInputStream("C:\\Users\\maurice\\Desktop\\Workshoptest1.xml");
            BufferedInputStream bis = new BufferedInputStream(fis);       
            XMLDecoder xmlDecoder = new XMLDecoder(bis);

                try{
                    list = (ArrayList<Bestelling>)xmlDecoder.readObject();
                }
                catch(ArrayIndexOutOfBoundsException e){

                }

                    } catch (FileNotFoundException ex) {
            Logger.getLogger(bestellingDAOXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        for (Bestelling element: list){
            if (element.getBestelling_id() == bestelling.getBestelling_id()
                    && element.getKlant_id() == bestelling.getKlant_id()){
                        list.remove(element);
                        break;
            }
        }
        try {
            fos = new FileOutputStream("C:\\Users\\maurice\\Desktop\\Workshoptest1.xml");
            bos = new BufferedOutputStream(fos);


                    try (XMLEncoder xmlEncoder = new XMLEncoder(bos)) {
                        xmlEncoder.writeObject(list);
                    }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(bestellingDAOXML.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    else System.out.println("Er valt niets te deleten brah...");
    
    }

    @Override
    public void deleteBestelling(ArrayList<Bestelling> bestelling) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteBestellingen(Klant klant) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Bestelling> findAlleBestellingen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Bestelling> findAlleBestellingen(Klant klant) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ArtikelBestelling> readArtikelBestelling(Bestelling bestelling) throws SQLException {
		FileInputStream fis = null;
                List<Bestelling> bestellingList = new ArrayList<Bestelling>();
                List<ArtikelBestelling> list = new ArrayList<ArtikelBestelling>();
                boolean stop = false;
        try {
            fis = new FileInputStream("C:\\Users\\maurice\\Desktop\\Workshoptest1.xml");
            BufferedInputStream bis = new BufferedInputStream(fis);       
            XMLDecoder xmlDecoder = new XMLDecoder(bis);
              
                    bestellingList = (ArrayList<Bestelling>)xmlDecoder.readObject();

                    } catch (FileNotFoundException ex) {
            Logger.getLogger(bestellingDAOXML.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(bestellingDAOXML.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Bestelling element: bestellingList){
            if (element.getBestelling_id() == bestelling.getBestelling_id()
                    && element.getKlant_id() == bestelling.getKlant_id()){
                        list = element.getArtikelBestellingList();
                        break;
            }
        }
        return(list);
    }

    @Override
    public void updateBestelling(ArtikelBestelling artikelBestelling, int bestel_id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void updateBestelling(Bestelling bestelling) throws SQLException {    
		FileOutputStream fos;
		BufferedOutputStream bos;
		FileInputStream fis = null;
                Bestelling bestelling1 = new Bestelling();
                List<Bestelling> list = new ArrayList<>();
                boolean stop = false;
                File file = new File("C:\\Users\\maurice\\Desktop\\Workshoptest1.xml");
    if (file.length() !=0){            
        try {
            fis = new FileInputStream("C:\\Users\\maurice\\Desktop\\Workshoptest1.xml");
            BufferedInputStream bis = new BufferedInputStream(fis);       
            XMLDecoder xmlDecoder = new XMLDecoder(bis);

                try{
                    list = (ArrayList<Bestelling>)xmlDecoder.readObject();
                }
                catch(ArrayIndexOutOfBoundsException e){

                }

                    } catch (FileNotFoundException ex) {
            Logger.getLogger(bestellingDAOXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        for (Bestelling element: list){
            if (element.getBestelling_id() == bestelling.getBestelling_id()
                    && element.getKlant_id() == bestelling.getKlant_id()){
                        element.setArtikelBestellingList(bestelling.getArtikelBestellingList());
                        
                        break;
            }
        }
        try {
            fos = new FileOutputStream("C:\\Users\\maurice\\Desktop\\Workshoptest1.xml");
            bos = new BufferedOutputStream(fos);


                    try (XMLEncoder xmlEncoder = new XMLEncoder(bos)) {
                        xmlEncoder.writeObject(list);
                    }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(bestellingDAOXML.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    else System.out.println("Er valt niets te deleten brah...");
    
}
}
