package uk.co.carelesslabs.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import uk.co.carelesslabs.Media;
import uk.co.carelesslabs.entity.Entity;

public class SquareMenu extends Menu {
    //public BuildMenu build;
    public Entity waterPercent = new Entity();
    public Entity feedPercent = new Entity();
    float full;
    
    public SquareMenu(final Entity entity){
        super(entity.pos.x, entity.pos.y, 2, Media.squareMenu);
        //width = width / 1.8f;
        
        int scale = 1;
        addButtons(.6f, 1, 2, Media.pinkButton, Media.selector, scale);
        
        Button btn = buttons.get(0);
        
        btn.icon = Media.watering;
        btn.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        if(entity.hero != null && entity.hero.waterLevel > 0){    
                            waterPercent.width = full;  
                            entity.hero.waterLevel -= 10;
                        }
                        
                    }
                });
        waterPercent.pos.set(btn.pos.x + btn.width + 1, btn.pos.y, 0);
        waterPercent.texture = Media.percent;
        waterPercent.height = btn.height;
        waterPercent.width = btn.width;
        full = btn.width;
        
        btn = buttons.get(1);
        btn.icon = Media.feed;
        btn.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(Button b) {
                        if(entity.hero != null && entity.hero.foodLevel > 0){    
                            feedPercent.width = full;  
                            entity.hero.foodLevel -= 10;
                        }
                    }
                });
        feedPercent.pos.set(btn.pos.x + btn.width + 1, btn.pos.y, 0);
        feedPercent.texture = Media.percent;
        feedPercent.height = btn.height;
        feedPercent.width = btn.width;
        
    }
    
    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        feedPercent.draw(batch);
        waterPercent.draw(batch);
    }
    
    @Override
    public void checkHover(Vector2 pos) {
        super.checkHover(pos);
    }
    
}