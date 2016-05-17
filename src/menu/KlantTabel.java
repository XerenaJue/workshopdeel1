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
import opdracht2.Klant;

public class KlantTabel {
	static void display() {  
		Stage window = new Stage();
		
        //Titel text
        Text txtTitel = new Text("Alle Klanten in de database.");
        txtTitel.setFont(Font.font(20));
        txtTitel.setFill(Color.BLACK);      
        
        //Tabel
        TableView<Klant> klantTabel = new TableView<>();
        ObservableList<Klant> klanten = FXCollections.observableArrayList();
        
        Klant klant1 = new Klant();
        klant1.setAchternaam("Fitte");
        klant1.setVoornaam("Kim");
        
        Klant klant2 = new Klant();
        klant2.setAchternaam("Vries");
        klant2.setVoornaam("Daan");
        klant2.setTussenvoegsel("de");
        klant2.setEmail("ddVries@gmail.com");
        
        klanten.addAll(klant1, klant2);
        
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
        vBox.getChildren().addAll(btnTerug, btnStop);        

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
}
