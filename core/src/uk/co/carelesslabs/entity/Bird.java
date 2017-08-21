package uk.co.carelesslabs.entity;

import uk.co.carelesslabs.Enums;
import uk.co.carelesslabs.Enums.EntityType;
import uk.co.carelesslabs.Media;
import uk.co.carelesslabs.Rumble;
import uk.co.carelesslabs.box2d.Box2DHelper;
import uk.co.carelesslabs.box2d.Box2DWorld;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class Bird extends Entity{
    
    public Bird(Vector3 pos, Box2DWorld box2d){
        super();
        type = EntityType.TREE;
        width = 8;
        height = 8;
        this.pos = pos;
        texture = Media.tree;
        shadow = Media.birdShadow;
        body = Box2DHelper.createBody(box2d.world, width/2, height/2, width/4, 0, pos, BodyDef.BodyType.StaticBody);
        sensor = Box2DHelper.createSensor(box2d.world, width, height*.85f, width/2, height/3, pos, BodyDef.BodyType.DynamicBody);     
        hashcode = sensor.getFixtureList().get(0).hashCode();
        state = Enums.EnityState.WALKING;
        ticks = true;
    }
    
    @Override
    public void draw(SpriteBatch batch){
        if(state == Enums.EnityState.FLYING){
            batch.draw(Media.birdShadow, pos.x, pos.y);
            batch.draw(Media.birdFlyAnim.getKeyFrame(time, true), pos.x, pos.y + 10);
        } else if(state == Enums.EnityState.WALKING){
            batch.draw(Media.birdShadow, pos.x, pos.y);
            batch.draw(Media.birdWalkAnim.getKeyFrame(time, true), pos.x, pos.y);
        } else if(state == Enums.EnityState.FEEDING){
            batch.draw(Media.birdShadow, pos.x, pos.y);
            batch.draw(Media.birdPeckAnim.getKeyFrame(time, true), pos.x, pos.y);
        }
        
    }
    
    @Override
    public void tick(float delta){
        time += delta;
    }
    
    @Override
    public void interact(Entity entity){
        if(entity.inventory != null){
            entity.inventory.addEntity(this);
            remove = true;
            Rumble.rumble(1, .2f);
        }
    }
    

}