/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxdatafieldform;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.awt.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


/**
 *
 * @author maurice
 */
public class JavaFXDataFieldForm extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Workshop deel1 invoerscherm");

        

        GridPane root = new GridPane();
        Button btn = new Button("voer in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        root.add(hbBtn, 1, 24);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                //POJO CODE hier
            }
        });
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(root, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
        Text sceneTitle = new Text("KLANTGEGEVENS");
        //sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        root.add(sceneTitle, 0, 0, 2, 1);
        Label klantIdLabel = new Label("Klant ID:");
        root.add(klantIdLabel, 0, 1);
        
        TextField klant_id = new TextField();
        root.add(klant_id, 1, 1);

        Label voornaamLabel = new Label("Voornaam:");
        root.add(voornaamLabel, 0, 2);

        TextField voornaam = new TextField();
        root.add(voornaam, 1, 2);
        
        Label achternaamLabel = new Label("Achternaam:");
        root.add(achternaamLabel, 0, 2);

        TextField achternaam = new TextField();
        root.add(achternaam, 1, 2);
        
        Label tussenvoegselLabel = new Label("Tussenvoegsel:");
        root.add(tussenvoegselLabel, 0, 3);

        TextField tussenvoegsel = new TextField();
        root.add(tussenvoegsel, 1, 3);

        Label emailLabel = new Label("Email:");
        root.add(emailLabel, 0, 4);

        TextField email = new TextField();
        root.add(email, 1, 4);

        Label straatnaamLabel = new Label("straatnaam:");
        root.add(straatnaamLabel, 0, 5);

        TextField straatnaam = new TextField();
        root.add(straatnaam, 1, 5);

        Label postcodeLabel = new Label("postcode:");
        root.add(postcodeLabel, 0, 6);

        TextField postcode = new TextField();
        root.add(postcode, 1, 6);
        
        Label toevoegingLabel = new Label("toevoeging:");
        root.add(toevoegingLabel, 0, 7);

        TextField toevoeging = new TextField();
        root.add(toevoeging, 1, 7);
        
        Label huisnummerLabel = new Label("huisnummer:");
        root.add(huisnummerLabel, 0, 8);

        TextField huisnummer = new TextField();
        root.add(huisnummer, 1, 8);
        
        Label woonplaatsLabel = new Label("woonplaats:");
        root.add(woonplaatsLabel, 0, 9);

        TextField woonplaats = new TextField();
        root.add(woonplaats, 1, 9);
        
        Text bestellingSceneTitle = new Text("BESTELLINGSGEGEVENS");
        //sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        root.add(bestellingSceneTitle, 0, 0, 2, 10);

        Label bestellingIdLabel = new Label("Bestelling ID:");
        root.add(bestellingIdLabel, 0, 11);
        
        TextField bestelling_id = new TextField();
        root.add(bestelling_id, 1, 11);

        Label artikelIdLabel = new Label("Artikel ID:");
        root.add(artikelIdLabel, 0, 12);

        TextField artikel_id = new TextField();
        root.add(artikel_id, 1, 12);
        
        Label artikelNaamLabel = new Label("Artikel naam:");
        root.add(artikelNaamLabel, 0, 13);

        TextField artikel_naam = new TextField();
        root.add(artikel_naam, 1, 13);
        
        Label artikelAantalLabel = new Label("Artikel Aantal:");
        root.add(artikelAantalLabel, 0, 14);

        TextField artikel_aantal = new TextField();
        root.add(artikel_aantal, 1, 14);

        Label artikelPrijsLabel = new Label("Artikel Prijs:");
        root.add(artikelPrijsLabel, 0, 15);

        TextField artikel_prijs = new TextField();
        root.add(artikel_prijs, 1, 15);

        Label artikel2IdLabel = new Label("Artikel 2 ID:");
        root.add(artikel2IdLabel, 0, 16);

        TextField artikel2_id = new TextField();
        root.add(artikel2_id, 1, 16);
        
        Label artikel2NaamLabel = new Label("Artikel 2 naam:");
        root.add(artikel2NaamLabel, 0, 17);

        TextField artikel2_naam = new TextField();
        root.add(artikel2_naam, 1, 17);
        
        Label artikel2AantalLabel = new Label("Artikel 2 Aantal:");
        root.add(artikel2AantalLabel, 0, 18);

        TextField artikel2_aantal = new TextField();
        root.add(artikel2_aantal, 1, 18);

        Label artikel2PrijsLabel = new Label("Artikel 2 Prijs:");
        root.add(artikel2PrijsLabel, 0, 19);

        TextField artikel2_prijs = new TextField();
        root.add(artikel2_prijs, 1, 19);

        Label artikel3IdLabel = new Label("Artikel 3 ID:");
        root.add(artikel3IdLabel, 0, 20);

        TextField artikel3_id = new TextField();
        root.add(artikel3_id, 1, 20);
        
        Label artikel3NaamLabel = new Label("Artikel 3 naam:");
        root.add(artikel3NaamLabel, 0, 21);

        TextField artikel3_naam = new TextField();
        root.add(artikel3_naam, 1, 21);
        
        Label artikel3AantalLabel = new Label("Artikel 3 Aantal:");
        root.add(artikel3AantalLabel, 0, 22);

        TextField artikel3_aantal = new TextField();
        root.add(artikel3_aantal, 1, 22);

        Label artikel3PrijsLabel = new Label("Artikel 3 Prijs:");
        root.add(artikel3PrijsLabel, 0, 23);

        TextField artikel3_prijs = new TextField();
        root.add(artikel3_prijs, 1, 23);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
