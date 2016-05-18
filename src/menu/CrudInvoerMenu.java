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
import javafx.animation.FadeTransition;
import static javafx.application.Application.launch;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import static javafx.application.Application.launch;
import javafx.util.Duration;


public class CrudInvoerMenu {
    
    FacadeDatabaseMenu facade;
    Stage window;
    BorderPane root; 
    GridPane pane;
    VBox vBox;
    Scene scene;
  
    MenuButton btnTerug;
    MenuButton btnStop;
    MenuButton btnClear;
    MenuButton btnZoekKlant;
    MenuButton btnAdressen;
    MenuButton btnArtikelen;
    MenuButton btnBestellingen;
    
    TextField klantIDTF = new TextField();
    Label klantIdLabel = new Label("Klant ID:");
    TextField klantAchternaamTF = new TextField();
    Label klantAchternaamLabel = new Label("Achternaam:");
    TextField klantVoornaamTF = new TextField();
    Label klantVoornaamLabel = new Label("Voornaam:");
        
    Object[] nepAppArray;
    Klant klant = new Klant();
    Adres adres = new Adres();
    Bestelling bestelling = new Bestelling();
    List<Bestelling> bestellingen = new ArrayList<>();
    List<ArtikelPOJO> artikelen =  new ArrayList<>();
    
    
    public CrudInvoerMenu() {  
        
        facade = new FacadeDatabaseMenu();
        nepAppArray = new Object[5];
        nepAppArray[0] = klant;
        nepAppArray[1] = adres;
        nepAppArray[2] = new ArrayList();
        nepAppArray[3] = new ArrayList();
        nepAppArray[4] = new ArrayList();
        
        this.setStage();
        this.initializeButtons();
     
    }
       
    public void startMenu() {
              
        this.setBackground();
        this.refreshPanes("Klantgegevens");
        root.setCenter(pane);
        root.setRight(vBox);
    
        window.setScene(scene);
        window.showAndWait();
    }
    
    private void setStage() {
        
        window = new Stage();
        root = new BorderPane();
        pane = new GridPane();
        vBox = new VBox(15);
        scene = new Scene(root, 800, 500);
           
    }
    
    private void initializeButtons() {
       
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
        btnZoekKlant = new MenuButton("Zoek Klant");
        btnZoekKlant.setOnMouseClicked(event -> { zoekKlant(); 
                refreshPanes("Klantgegevens");        
        }); 
          
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
      
    private void setBackground() {
         Image image;
         try (InputStream input = Files.newInputStream(Paths.get("res/images/Groene-achtergrond.jpg"))) {
            image = new Image(input);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            root.setBackground(background);
        } catch (IOException e) {
		System.out.println("Kan plaatje niet vinden");
	}
        FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play(); 
        
    }
        
    public void zoekKlant()  {
        try {
            int input = Integer.parseInt(klantIDTF.getText());
            klant.setKlantID(input);
            facade.zoek(nepAppArray);
            nepAppArray = facade.getToDisplay();
            klant =  (Klant)nepAppArray[0];
            
            klantVoornaamTF.setText(klant.getVoornaam());
        }
        catch (SQLException e) {
            System.out.println("oplossen nog ");
        }
        
    }
    
    
 
}

 
