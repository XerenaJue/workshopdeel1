package menu;
//jjj
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Menu extends Parent {
	static boolean terugNaarInlog;
	
    public static boolean display() {
        Stage window = new Stage();
        Pane layout = new Pane();
        layout.setPrefSize(800, 600);
        
        Image img;        
        try (InputStream input = Files.newInputStream(Paths.get("res/images/Groene-achtergrond.jpg"))) {
            img = new Image(input);
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(600);
            imgView.setFitWidth(800);
            layout.getChildren().add(imgView);
        } catch (IOException ex) {
            System.out.println("Kan achtergrond plaatje niet vindenn");
        }
    	
        VBox menu0 = new VBox(10);
        VBox menu1 = new VBox(10);
        VBox menu2 = new VBox(10);

        menu0.setTranslateX(100);
        menu0.setTranslateY(200);
        menu1.setTranslateX(100);
        menu1.setTranslateY(200);
        menu2.setTranslateX(100);
        menu2.setTranslateY(200);

        final int OFFSET = 400;            
        menu1.setTranslateX(OFFSET);
        menu2.setTranslateX(OFFSET);

        MenuButton btnCRUD = new MenuButton("CRUD-Handelingen");
        btnCRUD.setOnMouseClicked(event -> { //slide hoofdmenu naar links 
            layout.getChildren().add(menu1);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), menu0);
            tt.setToX(menu0.getTranslateX() - OFFSET);
            //zet submenu op plek van hoofdmenu
            TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.5), menu1);
            tt2.setToX(menu0.getTranslateX());

            tt.play();
            tt2.play();
            //als hooofdmenu naar links is verschoven laat het niet meer zien
            tt.setOnFinished(event2 -> {
                layout.getChildren().remove(menu0);
            });
        });

        MenuButton btnKlasseSelect = new MenuButton("klasse selectie");
        btnKlasseSelect.setOnMouseClicked(event -> {
            layout.getChildren().add(menu2);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), menu0);
            tt.setToX(menu0.getTranslateX() - OFFSET);
            TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.5), menu2);
            tt2.setToX(menu0.getTranslateX());
            
            tt.play();
            tt2.play();
            
            tt.setOnFinished(event2 -> {
                layout.getChildren().remove(menu0);
            });
        });

        MenuButton btnUitloggen = new MenuButton("Uitloggen");
        btnUitloggen.setOnMouseClicked(event -> {
            terugNaarInlog = true;
            window.close();
        });

        MenuButton btnStop = new MenuButton("Afsluiten");
        btnStop.setOnMouseClicked(event -> {
            System.exit(0);
        });
        
 //------------------------------------CRUD handelingen sub-menu-----------------------------------------//
        
        MenuButton btnCrieer = new MenuButton("Crieer");
        MenuButton btnUpdate = new MenuButton("Update");
        MenuButton btnZoek = new MenuButton("Zoek");
        MenuButton btnVerwijder = new MenuButton("Verwijder");
        
        MenuButton btnTerug = new MenuButton("Terug");
        btnTerug.setOnMouseClicked(event -> { //zet hoofdmenu weer op plek van submenu
            layout.getChildren().add(menu0);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
            tt.setToX(menu1.getTranslateX() + OFFSET);

            TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.5), menu0);
            tt2.setToX(menu1.getTranslateX());

            tt.play();
            tt2.play();

            tt.setOnFinished(event2 -> {
                layout.getChildren().remove(menu1);
            });
        });
        
//----------------------------------Klasse selectie sub-menu--------------------------------------------//        
        
        MenuButton btnKlant = new MenuButton("Klant");
        btnKlant.setOnMouseClicked(event -> {
        	KlantTabel.display();        	
        });
        MenuButton btnAdres = new MenuButton("Adres");
        MenuButton btnBestelling = new MenuButton("Bestelling");
        MenuButton btnArtikel = new MenuButton("Artikel");
        
        MenuButton btnTerug2 = new MenuButton("Terug");
        btnTerug2.setOnMouseClicked(event -> { //zet hoofdmenu weer op plek van submenu
            layout.getChildren().add(menu0);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
            tt.setToX(menu2.getTranslateX() + OFFSET);

            TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.5), menu0);
            tt2.setToX(menu2.getTranslateX());

            tt.play();
            tt2.play();

            tt.setOnFinished(event2 -> {
                layout.getChildren().remove(menu2);
            });
        });

        menu0.getChildren().addAll(btnCRUD, btnKlasseSelect, btnUitloggen, btnStop);
        menu1.getChildren().addAll(btnCrieer, btnUpdate, btnZoek, btnVerwijder, btnTerug);
        menu2.getChildren().addAll(btnKlant, btnAdres, btnBestelling, btnArtikel, btnTerug2);

        //achtergrond van het menu
        Rectangle bg = new Rectangle(300, 250);
        bg.setTranslateX(75);
        bg.setTranslateY(175);
        bg.setFill(Color.GREENYELLOW);
        bg.setOpacity(0.1);

        layout.getChildren().addAll(bg, menu0);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return terugNaarInlog;
    }   
}
