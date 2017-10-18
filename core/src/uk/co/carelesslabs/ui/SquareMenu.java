package uk.co.carelesslabs.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import uk.co.carelesslabs.Media;

public class SquareMenu {
    public Menu menu;
    BuildMenu build;
    
    public SquareMenu(){
        // MAIN MENU
        menu = new Menu(0, 0, 2, Media.squareMenu);
        menu.addButtons(3, 2, 2, Media.pinkButton, 2);
        menu.buttons.get(0).setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        build.menu.toggleActive();
                    }
                });
        menu.buttons.get(1).setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        System.out.println("Boop 1!");
                    }
                });
        
        menu.buttons.get(2).setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        System.out.println("Boop 2!");
                    }
                });
        
        menu.buttons.get(3).setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        System.out.println("Boop 3!");
                    }
                });
        
        // BUILDING
        build = new BuildMenu();
        build.menu = new Menu(menu.pos.x + menu.width, 0, 2, Media.mainBack);
        build.menu.addButtons(3, 14, 2, Media.pinkButton, 2);
        build.menu.setInactive();
        
    }
    
    public void draw(SpriteBatch batch){
        menu.draw(batch);
        build.menu.draw(batch);
    }

    public boolean checkClick(Vector2 pos, boolean processedClick) {
         return menu.checkClick(pos, processedClick);
    }
}
