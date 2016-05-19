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
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;


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
    MenuButton btnMaakKlant;
    
    TextField klantIDTF = new TextField();
    Label klantIdLabel = new Label("Klant ID:");
    TextField klantAchternaamTF = new TextField();
    Label klantAchternaamLabel = new Label("Achternaam:");
    TextField klantVoornaamTF = new TextField();
    Label klantVoornaamLabel = new Label("Voornaam:");
    TextField tussenvoegselTF = new TextField();
    Label tussenvoegselLabel = new Label("tussenvoegsel:");
    TextField emailTF = new TextField();
    Label emailLabel = new Label("email:");
    
    TextField straatnaamTF = new TextField();
    Label straatnaamLabel = new Label("straatnaam:");
    TextField huisnrTF = new TextField();
    Label huisnrLabel = new Label("huisnr:");
    TextField toevoegingTF = new TextField();
    Label toevoegingLabel = new Label("toevoeging:");
    TextField postcodeTF = new TextField();
    Label postcodeLabel = new Label("postcode:");
    TextField woonplaatsTF = new TextField();
    Label plaatsnaamLabel = new Label("plaatsnaam:");
    
        
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
        nepAppArray[2] = bestellingen;
        nepAppArray[3] = artikelen;
        nepAppArray[4] = new ArrayList<Bestelling>();
        
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
        scene = new Scene(root, 800, 600);
           
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
        btnZoekKlant.setOnMouseClicked(event -> { zoekKlant(); zoekAdresVanKlant();
                refreshPanes("Klantgegevens");        
        }); 
        btnMaakKlant = new MenuButton("Maak Klant");
        btnMaakKlant.setOnMouseClicked(event -> { maakKlant(); zoekKlant();
                refreshPanes("Klantgegevens");        
        }); 
          
    }
    
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
        pane.add(klantVoornaamTF, 5, 9, 5, 5);
        pane.add(klantVoornaamLabel, 0, 9, 5, 5);
        pane.add(tussenvoegselTF, 5, 13, 5, 5);
        pane.add(tussenvoegselLabel, 0, 13, 5, 5);
        pane.add(emailTF, 5, 17, 5, 5);
        pane.add(emailLabel, 0, 17, 5, 5);
        
        pane.add(straatnaamTF, 5, 24, 5, 5);
        pane.add(straatnaamLabel, 0, 24, 5, 5);
        pane.add(huisnrTF, 5, 28, 5, 5);
        pane.add(huisnrLabel, 0, 28, 5, 5);
        pane.add(toevoegingTF, 5, 32, 5, 5);
        pane.add(toevoegingLabel, 0, 32, 5, 5);
        pane.add(postcodeTF, 5, 36, 5, 5);
        pane.add(postcodeLabel, 0, 36, 5, 5);
        pane.add(woonplaatsTF, 5, 40, 5, 5);
        pane.add(plaatsnaamLabel, 0, 40, 5, 5);
        
        
        
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 100, 5, 5));
        vBox.getChildren().clear();
        vBox.getChildren().addAll(btnZoekKlant, btnMaakKlant, btnClear, btnTerug, btnStop);
       
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
        int input;
        try {
            if (klantIDTF.getText().isEmpty()) { input = 0 ; }
            else {
                input = Integer.parseInt(klantIDTF.getText());
            }
            klant.setKlantID(input);
            facade.zoek(nepAppArray);
            nepAppArray = facade.getToDisplay();
            klant =  (Klant)nepAppArray[0];
            klantIDTF.setText(Integer.toString(klant.getKlantID()));
            klantVoornaamTF.setText(klant.getVoornaam());
            klantAchternaamTF.setText(klant.getAchternaam());
            tussenvoegselTF.setText(klant.getTussenvoegsel()) ;
            emailTF.setText(klant.getEmail());
        }
        catch (SQLException e) {
            System.out.println("oplossen nog ");
        }
    }
    
    public void maakKlant()  {
        try {
            if (klantIDTF.getText().isEmpty()) { 
                klant.setKlantID(0); // zodat er geen bestaande klant opnieuw aangemaakt wordt, bij maak op bestaanID komt SQLexception
            }
            klant.setAchternaam(klantAchternaamTF.getText());
            klant.setEmail(emailTF.getText());
            klant.setVoornaam(klantVoornaamTF.getText());
            klant.setTussenvoegsel(tussenvoegselTF.getText());
            
            klant = facade.createKlant(klant);
            nepAppArray = facade.getToDisplay();  // nepapparray[0] is nu kopie van klant niet klant zelf!
                       
        }
        catch (SQLException e) {
            System.out.println("oplossen nog ");
        }
    }
    
    public void zoekAdresVanKlant()  {
                            
            nepAppArray = facade.getToDisplay();
            adres =  (Adres)nepAppArray[1];
            
            straatnaamTF.setText(adres.getStraatnaam());
            huisnrTF.setText(Integer.toString(adres.getHuisnummer()));
            toevoegingTF.setText(adres.getToevoeging()) ;
            postcodeTF.setText(adres.getPostcode());
            woonplaatsTF.setText(adres.getWoonplaats());
            
        
    }
    public void setKlant(Klant bestaandeKlant) {
                
        klantIDTF.setText(Integer.toString(bestaandeKlant.getKlantID()));
    }
    public void setAdres(Adres bestaandAdres) {
                
         nepAppArray[1] = bestaandAdres;
        
    }
    
    
 
}

 
