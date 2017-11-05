package uk.co.carelesslabs.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import uk.co.carelesslabs.Media;
import uk.co.carelesslabs.gameclass;

public class SquareMenu {
    public Menu menu;
    public BuildMenu build;
    
    public SquareMenu(final gameclass game){ 
        // MAIN MENU
        int scale = 2;
        menu = new Menu(0, 0, 2, Media.squareMenu);
        menu.addButtons(3, 2, 2, Media.pinkButton, Media.selector, scale);
        
        Button btn = menu.buttons.get(0);
        btn.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        
                    }
                });
        
        btn = menu.buttons.get(1);
        btn.icon = Media.iconSettings;
        btn.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        System.out.println("Settings.");
                    }
                });
        
        btn = menu.buttons.get(2);
        btn.icon = Media.iconResources;
        btn.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        game.control.inventory = true;
                    }
                });
        
        btn = menu.buttons.get(3);
        btn.icon = Media.iconBuild;
        menu.buttons.get(3).setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        build.menu.toggleActive();
                    }
                });
        
        // BUILDING
        build = new BuildMenu(menu.pos.x + menu.width, 0, 2, Media.mainBack);
        
    }
    
    public void draw(SpriteBatch batch){
        menu.draw(batch);
        build.draw(batch);
    }

    public boolean checkClick(Vector2 pos, boolean processedClick) {
        return menu.checkClick(pos, processedClick);
    }
    
    public void checkHover(Vector2 pos) {
        menu.checkHover(pos);
        build.menu.checkHover(pos);
    }
    
}