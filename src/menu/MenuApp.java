package menu;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MenuApp extends Application {    
                private final String user = "hallo";
                private final String password = "doei";
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new StackPane();
        root.setPrefSize(800, 600);
        Menu menu = new Menu();
        
        Image img;
        try (InputStream input = Files.newInputStream(Paths.get("res/images/Groene-achtergrond.jpg"))) {
            img = new Image(input);            
        }
        
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(600);
        imgView.setFitWidth(800);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6); 
        
        root.getChildren().addAll(imgView, grid);
        
        Scene scene = new Scene(root);
        
        btn.setOnAction(event -> {
                if ((userTextField.getText().equals(user)) && (pwBox.getText().equals(password))){
                    primaryStage.close();
                    menu.display();//Menu.display();
                    primaryStage.show();
                }
                else if ((userTextField.getText().equals(user)) && (!pwBox.getText().equals(password))){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Verkeerd wachtwoord");
                    alert.setContentText("U hebt een verkeerd wachtwoord ingevoerd");
                    alert.showAndWait();
                    pwBox.clear();
                }
                else if (!userTextField.getText().equals(user)){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("User onbekend");
                    alert.setContentText("User bestaat niet");
                    alert.showAndWait();
                    userTextField.clear();
                }
                
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
