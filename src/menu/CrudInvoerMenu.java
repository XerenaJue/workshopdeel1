package menu;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import facade.*;
import java.util.*;



import POJO.Adres;
import POJO.ArtikelPOJO;
import POJO.Bestelling;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.apache.commons.lang3.math.NumberUtils;

public class CrudInvoerMenu {

	FacadeDatabaseMenu facade;
	Stage window;
	BorderPane root;
	GridPane pane;
	VBox vBox;
        HBox hBox;
	Scene scene;

	MenuButton btnTerug;
	MenuButton btnStop = new MenuButton("Afsluiten");
	MenuButton btnClear;
	MenuButton btnZoek =  new MenuButton("Zoek Klant");
	MenuButton btnAdressen;
	MenuButton btnArtikelen;
	MenuButton btnBestellingen  = new MenuButton("Bestellingen");;
	MenuButton btnMaak = new MenuButton("Maak Klant");
	MenuButton btnUpdate = new MenuButton("Pas Klant aan");
	MenuButton btnVerwijder = new MenuButton("Verwijder Bestelling");;
	MenuButton btnDelete = new MenuButton("Verwijder Klant");;;
        MenuButton btnVerwijderAlles = new MenuButton("Verwijder alle bestellingen");
        MenuButton btnNextAdres = new MenuButton("volgend adres");
        
        MenuButton btnNextKlant = new MenuButton("volgende bewoner");;
       
        TextField klantIDTF = new TextField();
	Label klantIdLabel;
	TextField klantAchternaamTF;
	Label klantAchternaamLabel;
	TextField klantVoornaamTF;
	Label klantVoornaamLabel;
	TextField tussenvoegselTF;
	Label tussenvoegselLabel;
	TextField emailTF;
	Label emailLabel;

	TextField straatnaamTF;
	Label straatnaamLabel;
	TextField huisnrTF;
	Label huisnrLabel;
	TextField toevoegingTF;
	Label toevoegingLabel;
	TextField postcodeTF;
	Label postcodeLabel;
	TextField woonplaatsTF;
	Label plaatsnaamLabel;

//	Object[] nepAppArray;
	//Klant klant ;
	Adres adres ;
	Bestelling bestelling;
	List<Bestelling> bestellingen; 
	List<ArtikelPOJO> artikelen;
	Label lblStatus = new Label();
        private int adresIndex = 0;
        
        public CrudInvoerMenu() {
            
        }
    
	public void startMenu() {

		prepareMenu();
		showMenu();
	}

	public void prepareMenu() {

		this.setStage();
		this.initializeButtons();
		this.setLabels();
		this.setBackground();
	}

	public void showMenu() {

		this.refreshPanes("Klantgegevens");
		root.setCenter(pane);
		root.setRight(vBox);
                root.setBottom(hBox);
		//root.setBottom(lblStatus);
		window.setScene(scene);
		window.showAndWait();

	}

	protected void setStage() {

		window = new Stage();
		root = new BorderPane();
		pane = new GridPane();
		vBox = new VBox(15);
                hBox = new HBox(5);
		scene = new Scene(root, 800, 600);

	}

	protected void initializeButtons() {

		btnTerug = new MenuButton("Terug");
		btnTerug.setOnMouseClicked(event -> {  klantIDTF.clear(); postcodeTF.clear();  huisnrTF.clear(); // zodat als je terugkomt nieuwe klant wordt gezocht
			window.close();
		});
	
		btnStop.setOnMouseClicked(event -> {
			System.exit(0);
		});
                btnClear = new MenuButton("Clear");
		btnClear.setOnMouseClicked(event -> {
                        klantIDTF.clear();
                        klantAchternaamTF.clear();
                        klantVoornaamTF.clear();
                        tussenvoegselTF.clear();
                        emailTF.clear();
                        straatnaamTF.clear();
                        huisnrTF.clear();
                        toevoegingTF.clear();
                        postcodeTF.clear();
                        woonplaatsTF.clear();
                   //     getIDfromInputField();
                        //zoekKlant();
                });

	}

	public void setLabels() {

		//klantIDTF = new TextField();
		klantIdLabel = new Label("Klant ID:");
		klantAchternaamTF = new TextField();
		klantAchternaamLabel = new Label("Achternaam:");
		klantVoornaamTF = new TextField();
		klantVoornaamLabel = new Label("Voornaam:");
		tussenvoegselTF = new TextField();
		tussenvoegselLabel = new Label("tussenvoegsel:");
		emailTF = new TextField();
		emailLabel = new Label("email:");

		straatnaamTF = new TextField();
		straatnaamLabel = new Label("straatnaam:");
		huisnrTF = new TextField();
		huisnrLabel = new Label("huisnr:");
		toevoegingTF = new TextField();
		toevoegingLabel = new Label("toevoeging:");
		postcodeTF = new TextField();
		postcodeLabel = new Label("postcode:");
		woonplaatsTF = new TextField();
		plaatsnaamLabel = new Label("plaatsnaam:");

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
                
                hBox.setAlignment(Pos.CENTER);
                hBox.getChildren().clear();
                hBox.setPadding(new Insets(5, 100, 5, 5));
                hBox.getChildren().addAll(lblStatus, btnNextAdres, btnNextKlant );

		vBox.setAlignment(Pos.CENTER);
		vBox.setPadding(new Insets(5, 100, 5, 5));
		vBox.getChildren().clear();
		vBox.getChildren().addAll(btnZoek, btnMaak, btnUpdate, btnDelete, btnBestellingen, btnClear, btnTerug, btnStop);
	}

	protected void setBackground() {
		Image image;
		try (InputStream input = Files.newInputStream(Paths.get("res/images/Groene-achtergrond.jpg"))) {
			image = new Image(input);
			BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
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

		public void clearStatusText() {
			lblStatus.setText("");
		}
        
        public void falseEmail() {
            lblStatus.setTextFill(Color.ORANGERED);
            lblStatus.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
            lblStatus.setText("Ongeldig email adres");
        }
/*
	public void getIDfromInputField() {

		int input;
		if (klantIDTF.getText().isEmpty()) {
			input = 0;
		} else {
			input = Integer.parseInt(klantIDTF.getText());
		}
		klant.setKlantID(input);
		
		//zet andere klant velden ook in klant.
		klant.setAchternaam(klantAchternaamTF.getText());
		klant.setVoornaam(klantVoornaamTF.getText());
		klant.setTussenvoegsel(tussenvoegselTF.getText());
		klant.setEmail(emailTF.getText());
		
	}
*/
        public int getKlantID() {
            if ( NumberUtils.isNumber(klantIDTF.getText())) {
                return Integer.parseInt(klantIDTF.getText());
            } 
            return 0;
        }
        public String getKlantVoornaam() {
            return klantVoornaamTF.getText();
        }
        public String getKlantAchternaam() {
            return klantAchternaamTF.getText();
        }
        public String getTussenvoegsel() {
            return tussenvoegselTF.getText();
        }
        public String getEmail() {
            return emailTF.getText();
        }
        public String getStraatnaam() {
            return straatnaamTF.getText();
        }
        public int getHuisnummer() {
            if (NumberUtils.isNumber(huisnrTF.getText())) {
                return Integer.parseInt(huisnrTF.getText());
            } 
            return 0;
        }
        public String getToevoeging() {
            return toevoegingTF.getText();
        }
        public String getPostcode() {
            return postcodeTF.getText();
        }
        public String getPlaatsnaam() {
            return woonplaatsTF.getText();
        }
        
        public void setKlantID(int klantID) {
            klantIDTF.setText(Integer.toString(klantID)); 
        }
        public void setKlantVoornaam(String voornaam) {
             klantVoornaamTF.setText(voornaam);
        }
        public void setKlantAchternaam(String achternaam) {
            klantAchternaamTF.setText(achternaam);
        }
        public void setTussenvoegsel(String tussen) {
            tussenvoegselTF.setText(tussen);
        }
        public void setEmail(String email) {
            emailTF.setText(email);
        }
        public void setStraatnaam(String straat) {
            straatnaamTF.setText(straat );
        }
        public void setHuisnummer(int huisnr) {
            huisnrTF.setText(Integer.toString(huisnr));
        }
        public void setToevoeging(String toe) {
            toevoegingTF.setText(toe);
        }
        public void setPostcode(String postcode) {
            postcodeTF.setText(postcode);
        }
        public void setPlaatsnaam(String plaats) {
            woonplaatsTF.setText(plaats);
        }
        
        public void addHandlerToButtonZoek(EventHandler event) {
           
             btnZoek.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        }
        public void addHandlerToButtonMaak(EventHandler event) {
          
             btnMaak.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        }
        public void addHandlerToButtonUpdate(EventHandler event) {
          
             btnUpdate.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        }
        public void addHandlerToButtonVerwijder(EventHandler event) {
            
             btnDelete.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        }
        public void addHandlerToButtonBestellingen(EventHandler event) {
           
             btnBestellingen.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        }
        public void addHandlerToButtonVolgendAdres(EventHandler event) {
           
             btnNextAdres.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        }
        public void addHandlerToButtonNextKlant(EventHandler event) {
           
             btnNextKlant.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        }
}
