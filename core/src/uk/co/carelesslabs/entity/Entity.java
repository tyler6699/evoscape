package uk.co.carelesslabs.entity;

import uk.co.carelesslabs.Enums.EnityState;
import uk.co.carelesslabs.Enums.EntityType;
import uk.co.carelesslabs.box2d.Box2DWorld;
import uk.co.carelesslabs.map.Chunk;
import uk.co.carelesslabs.map.Tile;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

public class Entity implements Comparable<Entity> {
    public int hashcode;
    public Vector3 pos;
    transient public Vector3 destVec;
    transient public Texture texture;
    transient public Texture shadow;
    public float width;
    public float height;
    public EntityType type;
    public EnityState state;
    public float speed;
    transient public Body body;
    transient public Body sensor;
    public boolean remove;
    public Inventory inventory;
    public Boolean ticks;
    public float time;
    public float coolDown;
    public Tile currentTile;
    public float angle;
    Boolean flipX = false;
	Boolean flipY = false;
	public boolean active;
    
    float dirX = 0;
    float dirY = 0;
    
    public Entity(){
        pos = new Vector3();
    }
    
    public void draw(SpriteBatch batch){
        if(shadow != null) batch.draw(shadow, pos.x, pos.y, width, height);
        if(texture != null) batch.draw(texture, pos.x, pos.y, width, height);
    }
    
    public void drawRotated(SpriteBatch batch){
    	float xOffset = 3;
    	float xRight = -1;
    	
    	if(angle > 90 && angle < 270){
    		flipY = true;
    	} else {
    		flipY = false;
    		xRight = 7;
    	}
    	
    	if(shadow != null) batch.draw(shadow, pos.x, pos.y, width, height);
    	batch.draw(texture, pos.x + xRight, pos.y, width/2 - xOffset, height/2, width, height, 1, 1, angle, 0, 0, (int)width, (int)height, flipX, flipY);

		//    	x the x-coordinate in screen space
		//    	y the y-coordinate in screen space
		//    	originX the x-coordinate of the scaling and rotation origin relative to the screen space coordinates
		//    	originY the y-coordinate of the scaling and rotation origin relative to the screen space coordinates
		//    	width the width in pixels
		//    	height the height in pixels
		//    	scaleX the scale of the rectangle around originX/originY in x
		//    	scaleY the scale of the rectangle around originX/originY in y
		//    	rotation the angle of counter clockwise rotation of the rectangle around originX/originY
		//    	srcX the x-coordinate in texel space
		//    	srcY the y-coordinate in texel space
		//    	srcWidth the source with in texels
		//    	srcHeight the source height in texels
		//    	flipX whether to flip the sprite horizontally
		//    	flipY whether to flip the sprite vertically
    }
    
    public void tick(float delta){
        time += delta;
    }
    
    public void tick(float delta, Chunk chunk){
    
    }
    
    public int compareTo(Entity e) {
        try{
            float tempY =  e.pos.y;
            float compareY = pos.y;
            return (tempY < compareY ) ? -1: (tempY > compareY) ? 1:0 ;
        } catch (Exception exc){
            return 0;
        }
    }
    
    public void collision(Entity entity, boolean begin){}

    public void interact(Entity entity){}

    public void removeBodies(Box2DWorld box2D) {
        if(sensor != null) box2D.world.destroyBody(sensor);
        if(body != null) box2D.world.destroyBody(body);
    }
    
    public void getVector(Vector3 dest){
        float dx = dest.x - pos.x;
        float dy = dest.y - pos.y;
        double h = Math.sqrt(dx * dx + dy * dy);
        float dn = (float)(h / 1.4142135623730951);
              
        destVec = new Vector3(dx / dn, dy / dn, 0);
    }

}
