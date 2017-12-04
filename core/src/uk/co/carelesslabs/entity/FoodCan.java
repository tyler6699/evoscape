package uk.co.carelesslabs.entity;

import uk.co.carelesslabs.Enums.EntityType;
import uk.co.carelesslabs.Media;
import uk.co.carelesslabs.box2d.Box2DHelper;
import uk.co.carelesslabs.box2d.Box2DWorld;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class FoodCan extends Entity{
    
    public FoodCan(Vector3 pos, Box2DWorld box2d){
        super();
        width = 8;
        height = 8;
        this.pos = pos;
        setupTree(box2d);
    }
    
    private void setupTree(Box2DWorld box2d){
        type = EntityType.TREE;
        texture = Media.foodcan;
        body = Box2DHelper.createBody(box2d.world, width/2, height/2, width/4, 0, pos, BodyDef.BodyType.StaticBody);
        sensor = Box2DHelper.createSensor(box2d.world, width, height*.85f, width/2, height/3, pos, BodyDef.BodyType.DynamicBody);     
        hashcode = sensor.getFixtureList().get(0).hashCode();
    }

    @Override
    public void interact(Entity entity){
        if(entity.inventory != null){
            entity.foodLevel = 100;
        }
    }
    
    @Override
    public void hover(Entity entity){
        showMenu = true;
    }
    
    @Override
    public void draw(SpriteBatch batch){
        if(shadow != null) batch.draw(shadow, pos.x, pos.y, width, height);
        if(texture != null) batch.draw(texture, pos.x, pos.y, width, height);
    }
}