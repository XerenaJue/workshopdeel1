/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import POJO.ArtikelPOJO;
import POJO.Bestelling;
import POJO.Klant;
import POJO.ArtikelBestelling;
import facade.FacadeDatabaseMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author jeroen
 */
public class BestellingScherm extends CrudInvoerMenu {
    
    
    private MenuButton btnVerwijderArtikel, btnVoegToe ;
    private final TableView pojoTabel;
    private final TableView inhoudBestellingTabel;
    private final ObservableList weerTeGevenPOJOs;
    private final ObservableList weerTeGevenInhoud;
    private final Klant dezeKlant;
    private Bestelling dezeBestelling;
    
    private Label lbl1a, lbl1b, lbl2a , lbl2b;
   
        Label artikelLabel;
        TextField artikelTextField;
        Label artikelAantalLabel;
        TextField artikelAantalTextField;
    
    
    
    BestellingScherm(FacadeDatabaseMenu deFacade) {
         
        super(deFacade);
        dezeKlant = klant;
        dezeBestelling = new Bestelling();
        pojoTabel = new TableView<>();
        inhoudBestellingTabel =  new TableView<>();
        weerTeGevenPOJOs = FXCollections.observableArrayList();
        weerTeGevenInhoud =   FXCollections.observableArrayList();     
    }
    
    @Override
    public void startMenu() {
       
        this.setStage();
        this.initializeButtons();
        this.setLabels();
        super.setBackground();
        root.setCenter(pane);
        root.setRight(vBox);
        root.setBottom(pojoTabel);
        root.setLeft(inhoudBestellingTabel);
             
        setUpForBestellingen();
        setUpInhoudBestellingen();
        zoekBestellingen();
               
        this.refreshPanes("Bestelgegevens");
        window.setScene(scene);
        window.showAndWait();
    }
    
    @Override
    protected void initializeButtons() {
       
        btnTerug = new MenuButton("Terug");
        btnTerug.setOnMouseClicked(event -> { 
        	window.close();        	
        });
        btnVoegToe = new MenuButton("Artikelen Toevoegen");
        btnVoegToe.setOnMouseClicked(event -> {   
            voegArtikelToe();  
            zoekInDezeBestelling();
                         
        });
        btnMaak = new MenuButton("Nieuwe bestelling ");
        btnMaak.setOnMouseClicked(event -> { plaatsBestelling();
        });
        btnVerwijder = new MenuButton("Verwijder bestelling");
        btnVerwijder.setOnMouseClicked(event -> { verwijderBestelling(); zoekInDezeBestelling();
               
        });
        btnVerwijderAlles = new MenuButton("Verwijder alle bestellingen");
        btnVerwijderAlles.setOnMouseClicked(event -> {  verwijderAlleBestellingen(); zoekInDezeBestelling();
        });
        btnVerwijderArtikel = new MenuButton("Verwijder artikel");
        btnVerwijderArtikel.setOnMouseClicked(event -> { verwijderArtikel(); zoekInDezeBestelling();
        });
        
        pojoTabel.setOnMousePressed(e -> {zoekInDezeBestelling(); });
        
    }
                
    @Override
    public void refreshPanes(String header) {
       
        artikelLabel = new Label("Artikel ID");
        artikelTextField = new TextField(); 
        artikelAantalLabel = new Label("Artikel Aantal");
        artikelAantalTextField = new TextField(); 
        Text txtTitel = new Text(header);
        txtTitel.setFont(Font.font(20));
        txtTitel.setFill(Color.BLACK);   
       
        pane.getChildren().clear();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(50, 50, 15, 50));
        pane.add(txtTitel, 0, 0, 5, 1);
        pane.setHalignment(txtTitel, HPos.CENTER);
        pane.add(lbl1b, 5, 1, 5, 5);
        pane.add(lbl1a, 0, 1, 5, 5);
        pane.add(lbl2b, 5, 5, 5, 5);
        pane.add(lbl2a, 0, 5, 5, 5);
        pane.add(artikelTextField, 5, 13, 5, 5);
        pane.add(artikelLabel, 0, 13, 5, 5);
        pane.add(artikelAantalTextField, 5, 17, 5, 5);
        pane.add(artikelAantalLabel, 0, 17, 5, 5);
                         
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 100, 5, 5));
        vBox.getChildren().clear();
        vBox.getChildren().addAll( btnTerug, btnVoegToe, btnMaak, btnVerwijder, btnVerwijderAlles, btnVerwijderArtikel);
       
    }
    @Override
    protected void setLabels() {
        
        lbl1a= new Label("Klant ID:");
        lbl1b = new Label(Integer.toString(klant.getKlantID()));
        lbl2a = new Label("Achternaam klant:");
        lbl2b = new Label(klant.getAchternaam());
            
    }
     private void setUpForBestellingen() {
   
        List<String> pojoVelden = new ArrayList<>();
        pojoVelden.addAll(Arrays.asList("bestelling_id", "klant_id", "artikel_aantal" ) );
        setUpTabel(pojoVelden, weerTeGevenPOJOs);
    }
    
    
    private void setUpTabel(List<String> pojoVelden, ObservableList pojos ) {
        
        pojoTabel.setItems(pojos);
        pojoTabel.setMaxSize(300, 1000);
        pojoTabel.setMinSize(250, 300);
        
        int aantalKolommen = pojoVelden.size();
        String pojoNaam = "KlantBestellingen";
        
        if (!pojos.isEmpty()) {
            pojoNaam = pojos.get(0).getClass().getSimpleName();
        }
        TableColumn hoofdKolom = new TableColumn(pojoNaam);
        
        for (int i =0; i < aantalKolommen; i++){
            TableColumn<Integer,String> colNaam = new TableColumn<>(pojoVelden.get(i));
            colNaam.setCellValueFactory(new PropertyValueFactory<>(pojoVelden.get(i)));
            colNaam.setMinWidth(pojoTabel.getMaxWidth() / aantalKolommen);
            
            hoofdKolom.getColumns().add(colNaam);
        }
        pojoTabel.getColumns().clear();
        pojoTabel.getColumns().addAll(hoofdKolom);
                 
    }
    
    private void setUpInhoudBestellingen() {
   
        List<String> pojoVelden = new ArrayList<>();
        pojoVelden.addAll(Arrays.asList("artikelNaam", "artikelID", "artikelPrijs", "aantal_artikelen" ) );
        
        setUpBestellingTabel(pojoVelden, weerTeGevenInhoud);
    }
    
    private void setUpBestellingTabel(List<String> pojoVelden, ObservableList inhoud ) {
        
        inhoudBestellingTabel.setItems(inhoud);
        inhoudBestellingTabel.setMaxSize(300, 1000);
        inhoudBestellingTabel.setMinSize(250, 300);
        
        int aantalKolommen = pojoVelden.size();
        String pojoNaam = "Inhoud Bestelling";
        
        if (!inhoud.isEmpty()) {
            pojoNaam = inhoud.get(0).getClass().getSimpleName();
        }
        TableColumn hoofdKolom = new TableColumn(pojoNaam);
        
        for (int i =0; i < aantalKolommen; i++){
            TableColumn<ArtikelBestelling,String> colNaam = new TableColumn<>(pojoVelden.get(i));
            colNaam.setCellValueFactory(new PropertyValueFactory<>(pojoVelden.get(i)));
            colNaam.setMinWidth(inhoudBestellingTabel.getMaxWidth() / aantalKolommen);
            
            hoofdKolom.getColumns().add(colNaam);
        }
        inhoudBestellingTabel.getColumns().clear();
        inhoudBestellingTabel.getColumns().addAll(hoofdKolom);
          
    }
    
    private void zoekBestellingen() {
        weerTeGevenPOJOs.clear();   
        weerTeGevenPOJOs.addAll( (List)nepAppArray[2]);
    }
    
    private void zoekInDezeBestelling() {
        
        if (pojoTabel.getSelectionModel().getSelectedItem() instanceof Bestelling ) {
            
            dezeBestelling =  (Bestelling)pojoTabel.getSelectionModel().getSelectedItem();
            List indezak = facade.findArtikelen(dezeBestelling);
            weerTeGevenInhoud.clear();
            weerTeGevenInhoud.addAll(indezak);
            setUpInhoudBestellingen();
        }
        else weerTeGevenInhoud.clear();
    }
    
    
    private void voegArtikelToe(){
        ArtikelBestelling artikelBestelling = new ArtikelBestelling();
        ArtikelPOJO artikel = new ArtikelPOJO();
        artikel.setArtikelID(Integer.parseInt(artikelTextField.getText()));
        artikelBestelling.setArtikelPojo(artikel);
        artikelBestelling.setArtikelenAantal(Integer.parseInt(artikelAantalTextField.getText()));
        try{
            facade.updateBestelling(artikelBestelling, dezeBestelling.getBestelling_id());
            System.out.println("aanpassing wel gelukt");
        }
        catch (SQLException e) {
           e.printStackTrace();
           System.out.println("aanpassing niet gelukt");
        }
    }
    
    private void verwijderArtikel() {
        if ( inhoudBestellingTabel.getSelectionModel().getSelectedItem() instanceof ArtikelBestelling    ) {
            ArtikelBestelling artikelBestelling = (ArtikelBestelling)inhoudBestellingTabel.getSelectionModel().getSelectedItem();
            facade.removeFromBestelling(dezeBestelling, artikelBestelling);
        }
    }
    
    private void verwijderBestelling(){
        try {           
            facade.deleteBestelling((Bestelling)pojoTabel.getSelectionModel().getSelectedItem()); // dit komt uit TabelScherm.java
            facade.zoek(nepAppArray);
            nepAppArray = facade.getToDisplay();
            setUpForBestellingen();
            zoekBestellingen();
            System.out.println("verwijdering gelukt1111");
        }
        
           catch (SQLException e) {
           e.printStackTrace();
           System.out.println("verwijdering niet gelukt");
       }
    }
    
    private void verwijderAlleBestellingen(){
        try{
            facade.verwijderAlleBestellingen(klant);
            
            facade.zoek(nepAppArray);
            nepAppArray = facade.getToDisplay();
            setUpForBestellingen();
            zoekBestellingen(); 
        }
        catch(SQLException e){
        
        }
    }
    
    private void plaatsBestelling() {
       try {
            facade.zoek(nepAppArray);
            facade.createBestelling();
            nepAppArray = facade.getToDisplay();
            facade.zoek(nepAppArray);
            nepAppArray = facade.getToDisplay();
           
            setUpForBestellingen();
            zoekBestellingen(); 
         
       }
       catch (SQLException e) {
           e.printStackTrace();
           System.out.println("wordt nu wel ver gegooid deze SQL exc in Bestellingscherm.plaatsbestelling");
       }
    }
           

    
}

