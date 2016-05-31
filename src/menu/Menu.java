package menu;
//jjj
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.FadeTransition;
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
import opdracht2.ConnectionFactory;

public class Menu extends Parent {
	static boolean terugNaarInlog;
        static CrudInvoerMenu crudInvoerMenu = new CrudInvoerMenu();
        static TabelScherm tabelScherm = new TabelScherm();
        
	
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
        btnCRUD.setOnMouseClicked(event -> {crudInvoerMenu.startMenu(); });   
        

        MenuButton btnKlasseSelect = new MenuButton("klasse selectie");
        btnKlasseSelect.setOnMouseClicked(event -> {tabelScherm.display(); });
              
        
        MenuButton btnUitloggen = new MenuButton("Uitloggen");
        btnUitloggen.setOnMouseClicked(event -> {
            terugNaarInlog = true;
            window.close();
        });

        MenuButton btnStop = new MenuButton("Afsluiten");
        btnStop.setOnMouseClicked(event -> {
            System.exit(0);
        });
        
        MenuButton btnChangeConnector = new MenuButton("change connection pool");
        btnChangeConnector.setOnMouseClicked(event -> { System.out.println("knap"); ConnectionFactory.changeConnectionPool(); System.out.println("knop");
        });
        
        menu0.getChildren().addAll(btnCRUD, btnKlasseSelect, btnChangeConnector, btnUitloggen, btnStop);

        Rectangle bg = new Rectangle(300, 250);
        bg.setTranslateX(75);
        bg.setTranslateY(175);
        bg.setFill(Color.GREENYELLOW);
        bg.setOpacity(0.1);
        
        FadeTransition ft = new FadeTransition(Duration.millis(1500), layout);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();

        layout.getChildren().addAll(bg, menu0);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return terugNaarInlog;
    }   
}
