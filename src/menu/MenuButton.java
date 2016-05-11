package menu;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MenuButton extends StackPane {
    private Text text;
    
    public MenuButton(String name) {
        text = new Text(name);
        text.setFont(text.getFont().font(20));
        text.setFill(Color.BLACK);
        
        //achtergrond van de knoppen
        Rectangle bg = new Rectangle(250, 30);
        bg.setOpacity(0.6);
        bg.setFill(Color.GREEN); //default kleur
        GaussianBlur blur = new GaussianBlur(3.5);
        bg.setEffect(blur);
        
        setAlignment(Pos.CENTER_LEFT);
        setRotate(-0.5);
        getChildren().addAll(bg, text);
        
        setOnMouseEntered(event -> {
            bg.setTranslateX(10);//verplaats 10 pixsels naar rechts
            text.setTranslateX(10);
            bg.setFill(Color.WHITE);
            text.setFill(Color.DARKGREEN); //kleur verandering met mouseover
        });
        
        setOnMouseExited(event -> { //zet alles weer terug als de muis er af gaat
            bg.setTranslateX(0);
            text.setTranslateX(0);
            bg.setFill(Color.GREEN);
            text.setFill(Color.BLACK);
        });
       
        //geef shaduw met mouseclick
        DropShadow drop = new DropShadow(50, Color.WHITE);
        drop.setInput(new Glow());           
        setOnMousePressed(event -> setEffect(drop));
        setOnMouseReleased(event -> setEffect(null));
    }
}
