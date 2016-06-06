/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import facade.FacadeDatabaseMenu;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
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
import javafx.util.Duration;

/**
 *
 * @author jeroen
 */
public class DatasourceScherm {
    
    private final FacadeDatabaseMenu facade;
    private final Stage window;
    private final BorderPane root ;
    private final GridPane pane;
    private final VBox vBox ;
    private final Scene scene ;
    
    private MenuButton btnTerug;
    private MenuButton btnMySQL;
    private MenuButton btnFirebird;
    private MenuButton btnChangeConnectionPool;
    
    
    public DatasourceScherm(FacadeDatabaseMenu activeFacade) {
     
        facade = new FacadeDatabaseMenu();
        window = new Stage();
        root = new BorderPane();
        pane = new GridPane();
        vBox = new VBox(15);
        scene = new Scene(root, 800, 500);
      
    }    
    
    public void display() {  
        
        initializeButtons();
        showDisplay();
    }
    
    private void showDisplay() {
                       
        setBackground();
        refreshPanes("Datasources");
        
        root.setCenter(pane);
        root.setRight(vBox);
    
        window.setScene(scene);
        window.showAndWait();
    }
    
    private void initializeButtons() {
        
        btnTerug = new MenuButton("Terug");
        btnTerug.setOnMouseClicked(event -> {  
        	window.close();        	
        });       
        btnMySQL = new MenuButton("use MySQL");
        btnMySQL.setOnMouseClicked(event -> {  facade.changeDatabase("MySQL");        
        }); 
        btnFirebird = new MenuButton("use Firebird");
        btnFirebird.setOnMouseClicked(event -> {  facade.changeDatabase("Firebird");        
        }); 
        btnChangeConnectionPool = new MenuButton("change connection pool");
        btnChangeConnectionPool.setOnMouseClicked(event -> {  facade.changeConnectionPool();        
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
               
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 100, 5, 5));
        vBox.getChildren().clear();
        vBox.getChildren().addAll(btnMySQL, btnFirebird, btnChangeConnectionPool, btnTerug);
        
    }
      
    private void setBackground() {
         Image image;
         try (InputStream input = Files.newInputStream(Paths.get("res/images/Groene-achtergrond.jpg"))) {
            image = new Image(input);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            root.setBackground(background);
            
            FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
           
        } catch (IOException e) {
		System.out.println("Kan plaatje niet vinden");
	}
        
    }
    
    
}
