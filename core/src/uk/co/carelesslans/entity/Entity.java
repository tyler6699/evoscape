package uk.co.carelesslans.entity;

import uk.co.carelesslabs.Enums.EntityType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

public class Entity implements Comparable<Entity> {
    public Vector3 pos;
    public Texture texture;
    public float width;
    public float height;
    public EntityType type;
    public float speed;
    public Body body;
    
    float dirX = 0;
    float dirY = 0;
    
    public Entity(){
        pos = new Vector3();
    }
    
    public void draw(SpriteBatch batch){
        batch.draw(texture, pos.x, pos.y, width, height);
    }
    
    @Override
    public int compareTo(Entity e) {
        float temp_y =  e.pos.y;
        float compare_y = pos.y;
        
        return (temp_y < compare_y ) ? -1: (temp_y > compare_y) ? 1:0 ;
    }

}
