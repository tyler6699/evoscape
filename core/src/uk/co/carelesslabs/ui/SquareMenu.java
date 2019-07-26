package uk.co.carelesslabs.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import uk.co.carelesslabs.Media;
import uk.co.carelesslabs.GameClass;;

public class SquareMenu extends Menu {
    public BuildMenu build;
    
    public SquareMenu(final GameClass game){
        super(0, 0, 2, Media.squareMenu);
        
        int scale = 2;
        addButtons(3, 2, 2, Media.pinkButton, Media.selector, scale);
        
        Button btn = buttons.get(0);
        
        btn.icon = Media.iconSave;
        btn.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        game.saveGame.save(game.getObjectManager());
                    }
                });
        
        btn = buttons.get(1);
        btn.icon = Media.iconSettings;
        btn.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        System.out.println("LOAD.");
                        game.saveGame.load(game);

                    }
                });
        
        btn = buttons.get(2);
        btn.icon = Media.iconResources;
        btn.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        game.control.inventory = true;
                    }
                });
        
        btn = buttons.get(3);
        btn.icon = Media.iconBuild;
        buttons.get(3).setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        build.toggleActive();
                    }
                });
        
        // BUILDING
        build = new BuildMenu(pos.x + width, 0, 2, Media.mainBack);  
    }
    
    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        build.draw(batch);
    }
    
    @Override
    public void checkHover(Vector2 pos) {
        super.checkHover(pos);
        build.checkHover(pos);
    }
    
}