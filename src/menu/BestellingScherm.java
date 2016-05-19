/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import java.util.List;
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
import opdracht2.Bestelling;

/**
 *
 * @author jeroen
 */
public class BestellingScherm extends CrudInvoerMenu {
    
    private List<Bestelling> mijnBestellingen;
    MenuButton btnZoekBestellingen;
    private final TableView pojoTabel;
    private ObservableList weerTeGevenPOJOs;
    
    BestellingScherm(int klantnr) {
        super();
        pojoTabel = new TableView<>();
        weerTeGevenPOJOs = FXCollections.observableArrayList();
        
        pojoTabel.setMaxSize(300, 700); // nog verplaatsen
        pojoTabel.setMinSize(250, 700);
        
    }
    
    @Override
    public void startMenu() {
              
        this.setBackground();
        this.refreshPanes("Klantgegevens");
        root.setCenter(pane);
        root.setRight(vBox);
        root.setBottom(pojoTabel);
    
        window.setScene(scene);
        window.showAndWait();
    }
    
    @Override
    protected void initializeButtons() {
       
        btnTerug = new MenuButton("Terug");
        btnTerug.setOnMouseClicked(event -> {
        	window.close();        	
        });       
        btnStop = new MenuButton("Afsluiten");
        btnStop.setOnMouseClicked(event -> {
            System.exit(0);
        }); 
        btnClear = new MenuButton("Clear");
        btnClear.setOnMouseClicked(event -> {  
        }); 
        btnZoek = new MenuButton("Zoek Bestellingen");
        btnZoek.setOnMouseClicked(event -> { zoekKlant(); zoekAdresVanKlant();
                refreshPanes("Bestellingsgegevens");        
        }); 
        btnMaak = new MenuButton("Maak nieuwe bestelling");
        btnMaak.setOnMouseClicked(event -> { maakKlant(); zoekKlant();
                refreshPanes("Bestellingsgegevens");        
        }); 
          
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
        pane.add(klantIDTF, 5, 1, 5, 5);
        pane.add(klantIdLabel, 0, 1, 5, 5);
        pane.add(klantAchternaamTF, 5, 5, 5, 5);
        pane.add(klantAchternaamLabel, 0, 5, 5, 5);
        pane.add(klantVoornaamTF, 5, 10, 5, 5);
        pane.add(klantVoornaamLabel, 0, 10, 5, 5);
        pane.add(tussenvoegselTF, 5, 14, 5, 5);
        pane.add(tussenvoegselLabel, 0, 14, 5, 5);
        pane.add(emailTF, 5, 18, 5, 5);
        pane.add(emailLabel, 0, 18, 5, 5);
        
           
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 100, 5, 5));
        vBox.getChildren().clear();
        vBox.getChildren().addAll(btnZoek, btnMaak, btnClear, btnTerug, btnStop);
       
    }
    @Override
    protected void setLabels() {
        
        klantIDTF = new TextField();
        klantIdLabel = new Label("Klant ID:");
        klantAchternaamTF = new TextField();
        klantAchternaamLabel = new Label("Achternaam klant:");
        klantVoornaamTF = new TextField();
        klantVoornaamLabel = new Label("Bestellingsnr:");
        tussenvoegselTF = new TextField();
        tussenvoegselLabel = new Label("aantal artikelen");
        emailTF = new TextField();
        emailLabel = new Label("totaalbedrag");
    
        
        
    }
    
      private void setUpTabel(List<String> pojoVelden, ObservableList pojos ) {
        
        pojoTabel.setItems(pojos);
        pojoTabel.setMaxSize(600, 1000);
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
    
}

