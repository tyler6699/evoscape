package uk.co.carelesslabs.entity;

import uk.co.carelesslabs.Enums.EntityType;
import uk.co.carelesslabs.box2d.Box2DWorld;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

public class Entity implements Comparable<Entity> {
	public int hashcode;
    public Vector3 pos;
    public Texture texture;
    public float width;
    public float height;
    public EntityType type;
    public float speed;
    public Body body;
    public Body sensor;
    public boolean remove;
    
    float dirX = 0;
    float dirY = 0;
    
    public Entity(){
        pos = new Vector3();
    }
    
    public void draw(SpriteBatch batch){
        batch.draw(texture, pos.x, pos.y, width, height);
    }
    
    public int compareTo(Entity e) {
        float tempY =  e.pos.y;
        float compareY = pos.y;
        
        return (tempY < compareY ) ? -1: (tempY > compareY) ? 1:0 ;
    }
    
    public void collision(Entity entity, boolean begin){}

	public void interact(){}

	public void removeBodies(Box2DWorld box2D) {
		 if(sensor != null) box2D.world.destroyBody(sensor);
		 if(body != null) box2D.world.destroyBody(body);
	}

}
