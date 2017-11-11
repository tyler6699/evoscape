package uk.co.carelesslabs.ui;

import uk.co.carelesslabs.Media;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BuildMenu extends Menu {
    
    public BuildMenu(float x, int y, int scale, Texture mainBack){
        super(x, y, 2, Media.mainBack);
        addButtons(3, 14, 2, Media.pinkButton, Media.selector, 2);
        setInactive();
        
        Button close = new Button(0, 0, Media.close_menu.getWidth() * scale, Media.close_menu.getHeight() * scale, Media.close_menu, null);
        close.pos.x = x + width - (Media.close_menu.getWidth() * scale) - (6 * scale);
        close.pos.y = height - (Media.close_menu.getHeight() * scale) - (6 * scale);
        close.updateHitbox();
        close.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        toggleActive();
                    }
                });
        buttons.add(close);
    }
    
    public void draw(SpriteBatch batch){
        if(isActive()){
            super.draw(batch);
        } 
    }

}