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
import javafx.scene.control.Button;
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
import static javafx.application.Application.launch;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static javafx.application.Application.launch;


public class CrudInvoerMenu {
    
    static FacadeDatabaseMenu facade = new FacadeDatabaseMenu();
    static Stage window = new Stage();
    static BorderPane root = new BorderPane();
    static GridPane pane = new GridPane();
    static VBox vBox = new VBox(15);
    static Scene scene = new Scene(root, 800, 500);
    
    static TableView pojoTabel = new TableView<>();
    static ObservableList weerTeGevenPOJOs = FXCollections.observableArrayList();
    
    static MenuButton btnTerug;
    static MenuButton btnStop;
    static MenuButton btnClear;
    static MenuButton btnZoekKlant;
    static MenuButton btnAdressen;
    static MenuButton btnArtikelen;
    static MenuButton btnBestellingen;
    
    static TextField klantIDTF = new TextField();
    static Label klantIdLabel = new Label("Klant ID:");
    static TextField klantAchternaamTF = new TextField();
    static Label klantAchternaamLabel = new Label("Achternaam:");
    static TextField klantVoornaamTF = new TextField();
    static Label klantVoornaamLabel = new Label("Voornaam:");
    
    
    static Object[] nepAppArray = new Object[5];
   
    static Klant klant = new Klant();
    static Adres adres = new Adres();
    static Bestelling bestelling = new Bestelling();
    static List<Bestelling> bestellingen = new ArrayList<>();
    static List<ArtikelPOJO> artikelen =  new ArrayList<>();
    
    
    static void display() {  
        
        nepAppArray[0] = klant;
        nepAppArray[1] = adres;
        nepAppArray[2] = new ArrayList();
        nepAppArray[3] = new ArrayList();
        nepAppArray[4] = new ArrayList();
        
        clearTables();
        getKlanten();
        setUpForKlanten();
  
        initializeButtons();
        showDisplay();
 
    }
    
    static void showDisplay() {
                       
        setBackground();
        refreshPanes("Klantgegevens");
        
        root.setCenter(pane);
        root.setRight(vBox);
    
        window.setScene(scene);
        window.showAndWait();
    }
    
    static void initializeButtons() {
        
        btnTerug = new MenuButton("Terug");
        btnTerug.setOnMouseClicked(event -> {
        	window.close();        	
        });       
        btnStop = new MenuButton("Afsluiten");
        btnStop.setOnMouseClicked(event -> {
            System.exit(0);
        }); 
        btnClear = new MenuButton("Clear");
        btnClear.setOnMouseClicked(event -> { clearTables();  refreshPanes("lege tabel");        
        }); 
        btnZoekKlant = new MenuButton("Zoek Klant");
        btnZoekKlant.setOnMouseClicked(event -> { zoekKlant(); setUpForKlanten(); 
                refreshPanes("Alle Klanten in de database.");        
        }); 
        
        
    }
    
    static void refreshPanes(String header) {
        
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
        pane.add(klantVoornaamTF, 5, 9, 5, 5);
        pane.add(klantVoornaamLabel, 0, 9, 5, 5);
        
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 100, 5, 5));
        vBox.getChildren().clear();
        vBox.getChildren().addAll(btnZoekKlant, btnClear, btnTerug, btnStop);
        
    }
      
    static void setBackground() {
         Image image;
         try (InputStream input = Files.newInputStream(Paths.get("res/images/Groene-achtergrond.jpg"))) {
            image = new Image(input);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            root.setBackground(background);
        } catch (IOException e) {
		System.out.println("Kan plaatje niet vinden");
	}
        
    }
    
    static void getKlanten() {
        try {
         weerTeGevenPOJOs.addAll(facade.findKlanten() );
        }
        catch (SQLException e) {
            System.out.println("oplossen nog ");
        }
    }
    
    static void zoekKlant()  {
        try {
             
            int input = Integer.parseInt(klantIDTF.getText());
            
            klant.setKlantID(input);
            facade.zoek(nepAppArray);
            nepAppArray = facade.getToDisplay();
            
            klant =  (Klant)nepAppArray[0];
            System.out.println(klant);
            klantVoornaamTF.setText(klant.getVoornaam());
        }
        catch (SQLException e) {
            System.out.println("oplossen nog ");
        }
        
    }
    
    
    static void getAdressen() {
        try {
            weerTeGevenPOJOs.addAll(facade.findAlleAdressen() );
        }
        catch (SQLException e) {
            System.out.println("oplossen nog "); 
        }
    }
    
    static void setUpForKlanten() {
        
        List<String> pojoVelden = new ArrayList<>();
        pojoVelden.addAll(Arrays.asList("klantID", "voornaam", "achternaam", "tussenvoegsel", "email"));
        setUpTabel(pojoVelden, weerTeGevenPOJOs);
    }
        
    static void setUpForAdressen() {
   
        List<String> pojoVelden = new ArrayList<>();
        pojoVelden.addAll(Arrays.asList("straatnaam", "postcode", "toevoeging", "huisnummer", "woonplaats"));
        setUpTabel(pojoVelden, weerTeGevenPOJOs);
    }
    
    static void setUpTabel(List<String> pojoVelden, ObservableList pojos ) {
        
        pojoTabel.setItems(pojos);
        pojoTabel.setMaxSize(600, 1000);
        pojoTabel.setMinSize(250, 300);
        
        int aantalKolommen = pojoVelden.size();
        System.out.println(aantalKolommen);
        String pojoNaam = "empty";
        if (!pojos.isEmpty()) {
            pojoNaam = pojos.get(0).getClass().getName();
        }
        TableColumn hoofdKolom = new TableColumn(pojoNaam);
        
        for (int i =0; i < aantalKolommen; i++){
            TableColumn<Klant, String> colNaam = new TableColumn<>(pojoVelden.get(i));
            colNaam.setCellValueFactory(new PropertyValueFactory<>(pojoVelden.get(i)));
            colNaam.setMinWidth(pojoTabel.getMaxWidth() / aantalKolommen);
            
            hoofdKolom.getColumns().add(colNaam);
        }
        pojoTabel.getColumns().clear();
        pojoTabel.getColumns().addAll(hoofdKolom);
    }
    

    static void clearTables() {
        
        weerTeGevenPOJOs.clear(); 
        setUpTabel(Collections.EMPTY_LIST, weerTeGevenPOJOs );
    }
    
}

 
