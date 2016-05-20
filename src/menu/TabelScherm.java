package menu;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import opdracht2.*;
import facade.*;
import java.sql.*;
import java.util.*;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class TabelScherm {
    
    private final FacadeDatabaseMenu facade;
    private final Stage window;
    private final BorderPane root ;
    private final GridPane pane;
    private final VBox vBox ;
    private final Scene scene ;
    private final TableView pojoTabel;
    private ObservableList weerTeGevenPOJOs;
    private MenuButton btnTerug;
    private MenuButton btnStop;
    private MenuButton btnClear;
    private MenuButton btnKlanten;
    private MenuButton btnAdressen;
    private MenuButton btnArtikelen;
    private MenuButton btnBestellingen;
    private MenuButton btnCrud;
 
    
 public TabelScherm() {
     
    facade = new FacadeDatabaseMenu();
    window = new Stage();
    root = new BorderPane();
    pane = new GridPane();
    vBox = new VBox(15);
    scene = new Scene(root, 800, 500);
    pojoTabel = new TableView<>();
    weerTeGevenPOJOs = FXCollections.observableArrayList();
    
    initializeButtons();
     
     
 }    
    
    public void display() {  
        
        initializeButtons();
        showDisplay();
    }
    
    private void showDisplay() {
                       
        setBackground();
        refreshPanes("zoek me dan als je kan");
        
        root.setCenter(pane);
        root.setRight(vBox);
    
        window.setScene(scene);
        window.showAndWait();
    }
    
    private void initializeButtons() {
        
        btnTerug = new MenuButton("Terug");
        btnTerug.setOnMouseClicked(event -> { clearTables();  refreshPanes("lege tabel"); 
        	window.close();        	
        });       
        btnStop = new MenuButton("Afsluiten");
        btnStop.setOnMouseClicked(event -> {  
            System.exit(0);
        }); 
        btnClear = new MenuButton("Clear");
        btnClear.setOnMouseClicked(event -> { clearTables();  refreshPanes("lege tabel");        
        }); 
        btnKlanten = new MenuButton("Klanten");
        btnKlanten.setOnMouseClicked(event -> {  clearTables(); getKlanten(); setUpForKlanten(); 
                refreshPanes("Alle Klanten in de database.");        
        }); 
        btnAdressen = new MenuButton("Adressen");
        btnAdressen.setOnMouseClicked(event -> {  clearTables(); getAdressen();  setUpForAdressen(); 
                refreshPanes("Alle Adressen in de database.");        
        }); 
        btnBestellingen = new MenuButton("Bestellingen");
        btnBestellingen.setOnMouseClicked(event -> {  clearTables();  getBestellingen(); setUpForBestellingen();
                refreshPanes("Alle bestellingen in de database.");        
        });
        btnArtikelen = new MenuButton("Artikelen");
        btnArtikelen.setOnMouseClicked(event -> {   clearTables();  refreshPanes("Alle artikelen in de database.");        
        });
        btnCrud = new MenuButton("CRUD");
        btnCrud.setOnMouseClicked(event -> {   openDezeKlantCrud();          
        });
       // pojoTabel.setOnMouseClicked(event -> {   openDezeKlantCrud(); } );
        
    }
    
    private void refreshPanes(String header) {
        
        Text txtTitel = new Text(header);
        txtTitel.setFont(Font.font(20));
        txtTitel.setFill(Color.BLACK);   
        pane.getChildren().clear();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(50, 50, 15, 50));
        pane.add(txtTitel, 0, 0, 5, 1);
        pane.setHalignment(txtTitel, HPos.CENTER);
        pane.add(pojoTabel, 0, 1, 5, 5);
        
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 100, 5, 5));
        vBox.getChildren().clear();
        vBox.getChildren().addAll(btnKlanten, btnAdressen, btnBestellingen, btnArtikelen, btnCrud, 
                        btnClear, btnTerug, btnStop);
        
    }
      
    private void setBackground() {
         Image image;
         try (InputStream input = Files.newInputStream(Paths.get("res/images/Groene-achtergrond.jpg"))) {
            image = new Image(input);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            root.setBackground(background);
            
            FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();


            
            
        } catch (IOException e) {
		System.out.println("Kan plaatje niet vinden");
	}
        
    }
    
    private void getKlanten() {
        try {
         weerTeGevenPOJOs.addAll(facade.findKlanten() );
        }
        catch (SQLException e) {
            System.out.println("oplossen nog ");
        }
    }
    
    private void getAdressen() {
        try {
            weerTeGevenPOJOs.addAll(facade.findAlleAdressen() );
        }
        catch (SQLException e) {
            System.out.println("oplossen nog "); 
        }
    }
    
    private void getBestellingen() {
        try {
            weerTeGevenPOJOs.addAll(facade.findAlleBestellingen() );
        }
        catch (SQLException e) {
            System.out.println("oplossen nog "); 
        }
    }
    
    private void setUpForKlanten() {
        
        List<String> pojoVelden = new ArrayList<>();
        pojoVelden.addAll(Arrays.asList("klantID", "voornaam", "achternaam", "tussenvoegsel", "email"));
        setUpTabel(pojoVelden, weerTeGevenPOJOs);
    }
        
    private void setUpForAdressen() {
   
        List<String> pojoVelden = new ArrayList<>();
        pojoVelden.addAll(Arrays.asList("straatnaam", "postcode", "toevoeging", "huisnummer", "woonplaats"));
        setUpTabel(pojoVelden, weerTeGevenPOJOs);
    }
    
    private void setUpForBestellingen() {
   
        List<String> pojoVelden = new ArrayList<>();
        pojoVelden.addAll(Arrays.asList("bestelling_id", "klant_id", "artikel_aantal" ) );
        setUpTabel(pojoVelden, weerTeGevenPOJOs);
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
    

    private void clearTables() {
        
        weerTeGevenPOJOs.clear(); 
        setUpTabel(Collections.EMPTY_LIST, weerTeGevenPOJOs );
    }
    private void openDezeKlantCrud() {
        // aleen gebruiken bij klant
        if (pojoTabel.getSelectionModel().getSelectedItem() instanceof Klant ) {
        
            Klant person = (Klant)pojoTabel.getSelectionModel().getSelectedItem();
            CrudInvoerMenu klantCrud = new CrudInvoerMenu();
            klantCrud.prepareMenu();
            klantCrud.setKlant(person);
            klantCrud.zoekKlant();
            klantCrud.zoekAdresVanKlant(); 
            klantCrud.showMenu();
        }
        else if (pojoTabel.getSelectionModel().getSelectedItem() instanceof Adres ) {
        
            Adres adres = (Adres)pojoTabel.getSelectionModel().getSelectedItem();
            CrudInvoerMenu klantCrud = new CrudInvoerMenu();
            klantCrud.prepareMenu();
            klantCrud.setAdres(adres);
            klantCrud.zoekKlant();
            klantCrud.zoekAdresVanKlant(); 
            klantCrud.showMenu();
        }
    }
    
}
