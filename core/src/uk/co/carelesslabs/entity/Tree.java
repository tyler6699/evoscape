package uk.co.carelesslabs.entity;

import uk.co.carelesslabs.Enums.EntityType;
import uk.co.carelesslabs.Media;
import uk.co.carelesslabs.box2d.Box2DHelper;
import uk.co.carelesslabs.box2d.Box2DWorld;
import uk.co.carelesslabs.ui.SquareMenu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class Tree extends Entity{
    
    public Tree(Vector3 pos, Box2DWorld box2d, Hero hero){
        super();
        width = 8;
        height = 8;
        this.pos = pos;
        setupTree(box2d);
        this.hero = hero;
    }
    
    private void setupTree(Box2DWorld box2d){
        type = EntityType.TREE;
        texture = Media.tree;
        body = Box2DHelper.createBody(box2d.world, width/2, height/2, width/4, 0, pos, BodyDef.BodyType.StaticBody);
        sensor = Box2DHelper.createSensor(box2d.world, width, height*.85f, width/2, height/3, pos, BodyDef.BodyType.DynamicBody);     
        hashcode = sensor.getFixtureList().get(0).hashCode();
        popupMenu = new SquareMenu(this);
        popupMenu.pos.set(this.pos.x, this.pos.y);
    }

    @Override
    public void interact(Entity entity){
       
    }
    
    @Override
    public void hover(Entity entity){
        showMenu = true;
    }
    
    @Override
    public void draw(SpriteBatch batch){
        if(shadow != null) batch.draw(shadow, pos.x, pos.y, width, height);
        if(popupMenu.feedPercent.width < 3 || popupMenu.waterPercent.width < 3){
            batch.draw(Media.treeDie, pos.x, pos.y, width, height);    
        } else {
            if(texture != null) batch.draw(texture, pos.x, pos.y, width, height);
        }
        
        if(showMenu) popupMenu.draw(batch);
        
        // FOOD AND WAER
        popupMenu.feedPercent.width -=.005f;
        popupMenu.waterPercent.width -=.01f;
        
        if(popupMenu.feedPercent.width < 0) popupMenu.feedPercent.width = 0;
        if(popupMenu.waterPercent.width < 0) popupMenu.waterPercent.width = 0;
        
        if(popupMenu.feedPercent.width <= 0 && popupMenu.waterPercent.width <= 0){
            this.remove = true;
        }    
    }
}