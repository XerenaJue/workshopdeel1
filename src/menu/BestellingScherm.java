/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import POJO.Bestelling;
import POJO.ArtikelBestelling;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author jeroen
 */
public class BestellingScherm extends CrudInvoerMenu {
       
    private MenuButton btnVerwijderArtikel = new MenuButton("Verwijder artikel");
    private MenuButton  btnVoegToe = new MenuButton("Artikelen Toevoegen");
    private final TableView pojoTabel;
    private final TableView inhoudBestellingTabel;
    private final ObservableList weerTeGevenPOJOs;
    private final ObservableList weerTeGevenInhoud;
    
    MenuButton btnMaak = new MenuButton("Nieuwe bestelling ");
   
    private Label lbl1a, lbl1b, lbl2a , lbl2b;
   
    private Label artikelLabel;
    private    TextField artikelTextField;
    private    Label artikelAantalLabel;
    private   TextField artikelAantalTextField;
    
    
    
    public BestellingScherm() {
        
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
    public void setLabels() {
        
        lbl1a= new Label("Klant ID:");
        lbl2a = new Label("Achternaam klant:");
                
    }
    public void setLabels(String id, String naam) {
           
        lbl1b = new Label(id);
        lbl2b = new Label(naam);
            
    }
    
    
     private void setUpForBestellingen() {
   
        List<String> pojoVelden = new ArrayList<>();
        pojoVelden.addAll(Arrays.asList("bestelling_id", "klant_id", "artikel_aantal" ) );
        setUpTabel(pojoVelden);
    }
    
    
    public void setUpTabel(List<String> pojoVelden) {
        
        pojoTabel.setItems(weerTeGevenPOJOs);
        pojoTabel.setMaxSize(300, 1000);
        pojoTabel.setMinSize(250, 300);
        
        int aantalKolommen = pojoVelden.size();
        String pojoNaam = "KlantBestellingen";
        
        if (!weerTeGevenPOJOs.isEmpty()) {
            pojoNaam = weerTeGevenPOJOs.get(0).getClass().getSimpleName();
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
        
        setUpBestellingTabel(pojoVelden);
    }
    
    public void setUpBestellingTabel(List<String> pojoVelden ) {
        
        inhoudBestellingTabel.setItems(weerTeGevenInhoud);
        inhoudBestellingTabel.setMaxSize(300, 1000);
        inhoudBestellingTabel.setMinSize(250, 300);
        
        int aantalKolommen = pojoVelden.size();
        String pojoNaam = "Inhoud Bestelling";
        
        if (!weerTeGevenInhoud.isEmpty()) {
            pojoNaam = weerTeGevenInhoud.get(0).getClass().getSimpleName();
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
       
    public void showBestellingen(List pojos) {
        weerTeGevenPOJOs.clear();   
        weerTeGevenPOJOs.addAll( pojos);
    }
    
    public void clearArtikelLijst() {
        weerTeGevenInhoud.clear();
    }
    public int getArtikelID() {
        
        return Integer.parseInt(artikelTextField.getText());
    }
    
     public int getArtikelAantal() {
        
        return Integer.parseInt(artikelAantalTextField.getText());
    }

    public ArtikelBestelling getSelectedArtikel() {
        ArtikelBestelling artikelBestelling = (ArtikelBestelling)inhoudBestellingTabel.getSelectionModel().getSelectedItem();
        return artikelBestelling;
    }
    
    public void verversWinkelwagen(List indezak) {
        
        weerTeGevenInhoud.clear();
        weerTeGevenInhoud.addAll(indezak);
        
    }
    
    public Bestelling getSelectedBestelling() {
        
        return (Bestelling)pojoTabel.getSelectionModel().getSelectedItem();
        
    }
    public void addListenerToMaak(EventHandler event) {
        
        System.out.println("adding listenmer maak");
        btnMaak.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        
    }
    
    public void addListenerToVerwijder(EventHandler event) {
        
        btnVerwijder.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        
    }
           
    public void addListenerToTabel(EventHandler event) {
        
        pojoTabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        
    }
    public void addListenerToVerwijderAlle(EventHandler event) {
        
        btnVerwijderAlles.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        
    }
    public void addListenerToVerwijderArtikel(EventHandler event) {
        
        btnVerwijderArtikel.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        
    }
    public void addListenerToVoegToe(EventHandler event) {
        
        btnVoegToe.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        
    }
}

