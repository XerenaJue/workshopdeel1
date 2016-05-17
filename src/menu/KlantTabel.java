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


public class KlantTabel {
    
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
    static MenuButton btnKlanten;
    static MenuButton btnAdressen;
    static MenuButton btnArtikelen;
    static MenuButton btnBestellingen;
    
    /*
	static void display()  {  
		Stage window = new Stage();
	              
        FacadeDatabaseMenu facade = new FacadeDatabaseMenu();  
        
        //Titel text
        Text txtTitel = new Text("Alle Klanten in de database.");
        txtTitel.setFont(Font.font(20));
        txtTitel.setFill(Color.BLACK);      
        
        //Tabel
        TableView<Klant> klantTabel = new TableView<>();
        ObservableList<Klant> klanten = FXCollections.observableArrayList();
        
        try {
            klanten.addAll(facade.findKlanten() );
        }
        catch (SQLException e) {
            
        }
        klantTabel.setItems(klanten);
        klantTabel.setMaxSize(500, 600);
        klantTabel.setMinSize(250, 300);
        
        //kolomen
        TableColumn colKlantInfo = new TableColumn("Klanten informatie");
        
        TableColumn<Klant, String> colNaam = new TableColumn<>("Klant naam"); //naam vd kolom
        colNaam.setCellValueFactory(new PropertyValueFactory<>("voornaam")); // naam van class field
        colNaam.setMinWidth(klantTabel.getMaxWidth() / 4);
        
        TableColumn<Klant, String> colAchternaam = new TableColumn<>("Achternaam");
        colAchternaam.setCellValueFactory(new PropertyValueFactory<>("achternaam"));
        colAchternaam.setMinWidth(klantTabel.getMaxWidth() / 4);
        
        TableColumn<Klant, String> colTussenvoegsel = new TableColumn<>("Tussenvoegsel");
        colTussenvoegsel.setCellValueFactory(new PropertyValueFactory<>("tussenvoegsel"));
        colTussenvoegsel.setMinWidth(klantTabel.getMaxWidth() / 4);
        
        TableColumn<Klant, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setMinWidth(klantTabel.getMaxWidth() / 4);
        
        //zet kolumen in tableview
        colKlantInfo.getColumns().addAll(colNaam, colAchternaam, colTussenvoegsel, colEmail);
        klantTabel.getColumns().addAll(colKlantInfo);
        
        //knoppen
        Button btnTerug = new Button("Terug");
        
        btnTerug.setOnMouseClicked(event -> {
        	window.close();        	
        });       
        
        Button btnStop = new Button("Afsluiten");
        btnStop.setOnMouseClicked(event -> {
            System.exit(0);
        }); 
        
        Button btnClear = new Button("Clear");
        btnStop.setOnMouseClicked(event -> {
            klanten.clear();
        }); 
        
        //layout
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(50, 50, 15, 50));
        pane.add(txtTitel, 0, 0, 5, 1);
        pane.setHalignment(txtTitel, HPos.CENTER);
        pane.add(klantTabel, 0, 1, 5, 5);

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 100, 5, 5));
        vBox.getChildren().addAll(btnTerug, btnStop, btnClear);        

        BorderPane root = new BorderPane();
        root.setCenter(pane);
        root.setRight(vBox);
        
        //Achtergrond
        Image image;
        try (InputStream input = Files.newInputStream(Paths.get("res/images/Groene-achtergrond.jpg"))) {
            image = new Image(input);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            root.setBackground(background);
        } catch (IOException e) {
			System.out.println("Kan plaatje niet vinden");
		}
        
        Scene scene = new Scene(root, 800, 500);
        
        window.setScene(scene);
        window.showAndWait();


    }
*/
    
    
    
    static void display() {  
        
        getKlanten();
        setUpForKlanten();
            
        //knoppen
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
        btnKlanten = new MenuButton("Klanten");
        btnKlanten.setOnMouseClicked(event -> {  clearTables(); getKlanten(); setUpForKlanten(); 
                refreshPanes("Alle Klanten in de database.");        
        }); 
        btnAdressen = new MenuButton("Adressen");
        btnAdressen.setOnMouseClicked(event -> {  clearTables(); getAdressen();  setUpForAdressen(); 
                refreshPanes("Alle Adressen in de database.");        
        }); 
        btnBestellingen = new MenuButton("Bestellingen");
        btnBestellingen.setOnMouseClicked(event -> {  clearTables();  refreshPanes("Alle bestellingen in de database.");        
        });
        btnArtikelen = new MenuButton("Artikelen");
        btnArtikelen.setOnMouseClicked(event -> {  clearTables();  refreshPanes("Alle artikelen in de database.");        
        });
        
        showDisplay();
 
    }
    
    static void showDisplay() {
        
                
        setBackGround();
        refreshPanes("Alle Klanten in de database.");
        
        root.setCenter(pane);
        root.setRight(vBox);
    
        window.setScene(scene);
        window.showAndWait();
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
        pane.add(pojoTabel, 0, 1, 5, 5);
        
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 100, 5, 5));
        vBox.getChildren().clear();
        vBox.getChildren().addAll(btnKlanten, btnAdressen, btnBestellingen, btnArtikelen, btnClear, btnTerug, btnStop);
        
    }
    
    
    static void setBackGround() {
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
            
        }
    }
    
    static void getAdressen() {
        try {
            System.out.println(" hier adressen: "+ weerTeGevenPOJOs);
         weerTeGevenPOJOs.addAll(facade.findAlleAdressen() );
          System.out.println(" hier mere adressen: "+ weerTeGevenPOJOs);
        }
        catch (SQLException e) {
            
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
