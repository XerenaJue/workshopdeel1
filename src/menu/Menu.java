package menu;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Menu extends Parent {
    public Menu() {
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
            getChildren().add(menu1);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), menu0);
            tt.setToX(menu0.getTranslateX() - OFFSET);
            //zet submenu op plek van hoofdmenu
            TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.5), menu1);
            tt2.setToX(menu0.getTranslateX());

            tt.play();
            tt2.play();
            //als hooofdmenu naar links is verschoven laat het niet meer zien
            tt.setOnFinished(event2 -> {
                getChildren().remove(menu0);
            });
        });

        MenuButton btnKlasseSelect = new MenuButton("klasse selectie");
        btnKlasseSelect.setOnMouseClicked(event -> {
            getChildren().add(menu2);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), menu0);
            tt.setToX(menu0.getTranslateX() - OFFSET);
            TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.5), menu2);
            tt2.setToX(menu0.getTranslateX());
            
            tt.play();
            tt2.play();
            
            tt.setOnFinished(event2 -> {
                getChildren().remove(menu0);
            });
        });

        MenuButton btnUitloggen = new MenuButton("Uitloggen");
        btnUitloggen.setOnMouseClicked(event -> {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
            ft.setFromValue(1); //vanuit hier
            ft.setToValue(0); // naar hier verschuiven
            ft.setOnFinished(event2 -> this.setVisible(false));//niet meer weergeven            
            ft.play();
        });

        MenuButton btnStop = new MenuButton("Afsluiten");
        btnStop.setOnMouseClicked(event -> {
            System.exit(0);
        });
        
        MenuButton btnTerug = new MenuButton("Terug");
        btnTerug.setOnMouseClicked(event -> {
            getChildren().add(menu0);
            if (menu1.getTranslateX() == OFFSET) {//als submenu 2 actief is
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
                tt.setToX(menu2.getTranslateX() + OFFSET);

                TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.5), menu0);
                tt2.setToX(menu2.getTranslateX());
                
                tt.play();
                tt2.play();

                tt.setOnFinished(event2 -> {
                    getChildren().remove(menu2);
                });
            } else {//als submenu 1 actief is
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
                tt.setToX(menu1.getTranslateX() + OFFSET);

                TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.5), menu0);
                tt2.setToX(menu1.getTranslateX());
                
                tt.play();
                tt2.play();

                tt.setOnFinished(event2 -> {
                    getChildren().remove(menu1);
                });
            }            
        });
        MenuButton btnCrieer = new MenuButton("Crieer");
        MenuButton btnUpdate = new MenuButton("Update");
        MenuButton btnZoek = new MenuButton("Zoek");
        MenuButton btnVerwijder = new MenuButton("Verwijder");
        
        MenuButton btnKlant = new MenuButton("Klant");
        MenuButton btnAdres = new MenuButton("Adres");
        MenuButton btnBestelling = new MenuButton("Bestelling");
        MenuButton btnArtikel = new MenuButton("Artikel");

        menu0.getChildren().addAll(btnCRUD, btnKlasseSelect, btnUitloggen, btnStop);
        menu1.getChildren().addAll(btnCrieer, btnUpdate, btnZoek, btnVerwijder, btnTerug);
        menu2.getChildren().addAll(btnKlant, btnAdres, btnBestelling, btnArtikel, btnTerug);

        //achtergrond van het menu
        Rectangle bg = new Rectangle(300, 250);
        bg.setTranslateX(75);
        bg.setTranslateY(175);
        bg.setFill(Color.GREENYELLOW);
        bg.setOpacity(0.1);

        getChildren().addAll(bg, menu0);
    }   
}
