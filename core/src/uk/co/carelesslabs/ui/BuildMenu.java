package uk.co.carelesslabs.ui;

import uk.co.carelesslabs.Media;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class BuildMenu {
    public Menu menu;
    Button close;
    
    public BuildMenu(float x, int y, int scale, Texture mainBack){
        menu = new Menu(x, y, 2, Media.mainBack);
        menu.addButtons(3, 14, 2, Media.pinkButton, Media.selector, 2);
        menu.setInactive();
        
        close = new Button(0, 0, Media.close_menu.getWidth() * scale, Media.close_menu.getHeight() * scale, Media.close_menu, null);
        close.pos.x = x + menu.width - (Media.close_menu.getWidth() * scale) - (6 * scale);
        close.pos.y = menu.height - (Media.close_menu.getHeight() * scale) - (6 * scale);
        close.updateHitbox();
        close.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        menu.toggleActive();
                    }
                });
        menu.buttons.add(close);
    }
    
    public void draw(SpriteBatch batch){
        if(menu.isActive()){
            menu.draw(batch);
        } 
    }

    public boolean checkClick(Vector2 pos, boolean processedClick) {
         return menu.checkClick(pos, processedClick);
    }
}
