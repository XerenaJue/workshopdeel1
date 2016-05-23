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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import opdracht2.Klant;
import opdracht2.Bestelling;

/**
 *
 * @author jeroen
 */
public class BestellingScherm extends CrudInvoerMenu {
    
    
    MenuButton btnZoekBestellingen;
    private final TableView pojoTabel;
    private final ObservableList weerTeGevenPOJOs;
    private final Klant dezeKlant;
    
    Label lbl1a, lbl1b, lbl2a , lbl2b;
    
    
    
    BestellingScherm(Object[] nepAppArray) {
         
        super();
        dezeKlant = (Klant)nepAppArray[0];
        pojoTabel = new TableView<>();
        weerTeGevenPOJOs = FXCollections.observableArrayList();
        super.nepAppArray = nepAppArray;
          
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
             
        setUpForBestellingen();
        zoekBestellingen();
        schrijfLabels();
       
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
        btnZoek = new MenuButton("Voeg artikel toe");
        btnZoek.setOnMouseClicked(event -> {   System.out.println(klant); 
                refreshPanes("Bestellingsgegevens");        
        });
        btnMaak = new MenuButton("Nieuwe bestelling ");
        btnMaak.setOnMouseClicked(event -> { System.out.println("click Maakbutton met arrayklant "+nepAppArray[0]); plaatsBestelling();
                refreshPanes("Bestellingsgegevens");        
        });
        btnVerwijder = new MenuButton("Verwijder bestelling");
        btnVerwijder.setOnMouseClicked(event -> { System.out.println("click Deletebutton "+nepAppArray[0]); verwijderBestelling();
                refreshPanes("Bestellingsgegevens");        
        }); 
        //blll  
    }
                
    @Override
    public void refreshPanes(String header) {
       
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
                        
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 100, 5, 5));
        vBox.getChildren().clear();
        vBox.getChildren().addAll( btnTerug, btnZoek, btnMaak, btnVerwijder);
       
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
        String pojoNaam = "empty";
        
        if (!pojos.isEmpty()) {
            pojoNaam = pojos.get(0).getClass().getName();
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
    
    private void zoekBestellingen() {
        weerTeGevenPOJOs.clear();   
        weerTeGevenPOJOs.addAll( (List)nepAppArray[2]);
    }
    
    
    private void verwijderBestelling(){
        try {           
            facade.deleteBestelling((Bestelling)pojoTabel.getSelectionModel().getSelectedItem()); // dit komt uit TabelScherm.java
        }
           catch (SQLException e) {
           e.printStackTrace();
           System.out.println("verwijdering niet gelukt");
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
      
    private void schrijfLabels() {
        
      lbl2b.setText(dezeKlant.getAchternaam());
      lbl1b.setText(Integer.toString(dezeKlant.getKlantID()));  
    }
}

