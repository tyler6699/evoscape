package uk.co.carelesslabs.ui;

import uk.co.carelesslabs.Media;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class BuildMenu {
    public Menu menu;
    
    public BuildMenu(){
        menu = new Menu(0, 0, 2, Media.squareMenu);
        menu.addButtons(3, 2, 2, Media.pinkButton, 2);
    }
    
    public void draw(SpriteBatch batch){
        menu.draw(batch);
    }

    public boolean checkClick(Vector2 pos, boolean processedClick) {
         return menu.checkClick(pos, processedClick);
    }
}
