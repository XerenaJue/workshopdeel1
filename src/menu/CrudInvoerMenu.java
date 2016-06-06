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
import opdracht2.*;
import facade.*;
import java.sql.*;
import java.util.*;

import org.apache.commons.validator.routines.EmailValidator;

import POJO.Adres;
import POJO.ArtikelPOJO;
import POJO.Bestelling;
import POJO.Klant;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	MenuButton btnZoek;
	MenuButton btnAdressen;
	MenuButton btnArtikelen;
	MenuButton btnBestellingen;
	MenuButton btnMaak;
	MenuButton btnUpdate;
	MenuButton btnVerwijder;
	MenuButton btnDelete;

	TextField klantIDTF;
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

	Object[] nepAppArray;
	Klant klant ;
	Adres adres ;
	Bestelling bestelling;
	List<Bestelling> bestellingen; 
	List<ArtikelPOJO> artikelen;
	Label lblStatus = new Label();

	public CrudInvoerMenu(FacadeDatabaseMenu deFacade) {

		facade = deFacade;
                nepAppArray = facade.getToDisplay();
                klant = (Klant)nepAppArray[0];
                adres = (Adres)nepAppArray[1];
                bestellingen = (List<Bestelling>)nepAppArray[2];
                artikelen = (List<ArtikelPOJO> )nepAppArray[3];
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

	protected void showMenu() {

		this.refreshPanes("Klantgegevens");
		root.setCenter(pane);
		root.setRight(vBox);
		root.setBottom(lblStatus);
		window.setScene(scene);
		window.showAndWait();

	}

	protected void setStage() {

		window = new Stage();
		root = new BorderPane();
		pane = new GridPane();
		vBox = new VBox(15);
		scene = new Scene(root, 800, 600);

	}

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
		btnZoek = new MenuButton("Zoek Klant");
		btnZoek.setOnMouseClicked(event -> {
			getIDfromInputField();
			zoekKlant();
			zoekAdresVanKlant();
			refreshPanes("Klantgegevens");
		});
		btnMaak = new MenuButton("Maak Klant");
		btnMaak.setOnMouseClicked(event -> {
			maakKlant();
			zoekKlant();
			refreshPanes("Klantgegevens");
		});
		btnBestellingen = new MenuButton("Bestellingen");
		btnBestellingen.setOnMouseClicked(event -> {
			BestellingScherm besteld = new BestellingScherm(facade);
			besteld.startMenu();
			besteld.refreshPanes("Bestellingsgegevens");
		});
		btnUpdate = new MenuButton("Pas Klant Aan");
		btnUpdate.setOnMouseClicked(event -> {
			getIDfromInputField();
			updateKlant();
			updateAdres();
			refreshPanes("Klantgegevens");
		});
		btnDelete = new MenuButton("Verwijder Klant");
		btnDelete.setOnMouseClicked(event -> {
			getIDfromInputField();
			deleteKlant();
			refreshPanes("Klantgegevens");
		});

	}

	protected void setLabels() {

		klantIDTF = new TextField();
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

	public void zoekKlant() {

		try {

			facade.zoek(nepAppArray);
			nepAppArray = facade.getToDisplay();
			klant = (Klant) nepAppArray[0];
			klantIDTF.setText(Integer.toString(klant.getKlantID()));
			klantVoornaamTF.setText(klant.getVoornaam());
			klantAchternaamTF.setText(klant.getAchternaam());
			tussenvoegselTF.setText(klant.getTussenvoegsel());
			emailTF.setText(klant.getEmail());
		} catch (SQLException e) {
			System.out.println("oplossen nog zoekklantcrudinvoermenu ");
                        e.printStackTrace();
		}
	}

	public void maakKlant() {
		try {
			if (klantIDTF.getText().isEmpty()) {
				klant.setKlantID(0); // zodat er geen bestaande klant opnieuw
										// aangemaakt wordt, bij maak op
										// bestaanID komt SQLexception
			}
			klant.setAchternaam(klantAchternaamTF.getText());

			if (!emailCheck(emailTF.getText())) {
				return;
			}
			klant.setEmail(emailTF.getText());
			klant.setVoornaam(klantVoornaamTF.getText());
			klant.setTussenvoegsel(tussenvoegselTF.getText());

			klant = facade.createKlant(klant);
			nepAppArray = facade.getToDisplay(); // nepapparray[0] is nu kopie
													// van klant niet klant
													// zelf!

		} catch (SQLException e) {
			System.out.println("oplossen nog ");
		}
	}

	public void zoekAdresVanKlant() {

		nepAppArray = facade.getToDisplay();
		adres = (Adres) nepAppArray[1];

		straatnaamTF.setText(adres.getStraatnaam());
		huisnrTF.setText(Integer.toString(adres.getHuisnummer()));
		toevoegingTF.setText(adres.getToevoeging());
		postcodeTF.setText(adres.getPostcode());
		woonplaatsTF.setText(adres.getWoonplaats());
	}

	public void setKlant(Klant bestaandeKlant) {

		nepAppArray[0] = bestaandeKlant;

	}

	public void setAdres(Adres bestaandAdres) {

		nepAppArray[1] = bestaandAdres;

	}


                


	public boolean emailCheck(String email) {
		EmailValidator emailVal = EmailValidator.getInstance();
		if (!emailVal.isValid(email)) {
			lblStatus.setTextFill(Color.ORANGERED);
			lblStatus.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
			lblStatus.setText("Ongeldig email adres");
			return false;
		}
		return true;
	}

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

	public void updateKlant() {
		try {
			klant.setAchternaam(klantAchternaamTF.getText());

			if (!emailCheck(emailTF.getText())) {
				return;
			}
			klant.setEmail(emailTF.getText());
			klant.setVoornaam(klantVoornaamTF.getText());
			klant.setTussenvoegsel(tussenvoegselTF.getText());
			facade.updateKlant(klant);
			nepAppArray = facade.getToDisplay();

		} catch (SQLException ex) {
			System.out.println("Nog op te lossen");
		}
	}

	public void updateAdres() {

		try {
			adres.setStraatnaam(straatnaamTF.getText());
			adres.setHuisnummer(Integer.parseInt(huisnrTF.getText()));
			adres.setToevoeging(toevoegingTF.getText());
			adres.setPostcode(postcodeTF.getText());
			adres.setWoonplaats(woonplaatsTF.getText());
			facade.update(klant.getKlantID(), adres);
			nepAppArray = facade.getToDisplay();

		} catch (SQLException e) {
			System.out.println("Nog op te lossen");
		}

	}

	public void deleteKlant() {
		try {
			klant.setKlantID(Integer.parseInt(klantIDTF.getText()));
			facade.deleteKlant();
			nepAppArray = facade.getToDisplay();
		} catch (SQLException ex) {
			System.out.println("Nog op te lossen");
		}
	}

}
