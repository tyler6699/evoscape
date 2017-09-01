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
    public Vector3 destVec;
    public Texture texture;
    public Texture shadow;
    public float width;
    public float height;
    public EntityType type;
    public EnityState state;
    public float speed;
    public Body body;
    public Body sensor;
    public boolean remove;
    public Inventory inventory;
    public Boolean ticks;
    public float time;
    public Tile currentTile;
    
    float dirX = 0;
    float dirY = 0;
    
    public Entity(){
        pos = new Vector3();
    }
    
    public void draw(SpriteBatch batch){
        if(shadow != null) batch.draw(shadow, pos.x, pos.y, width, height);
        if(texture != null) batch.draw(texture, pos.x, pos.y, width, height);
    }
    
    public void tick(float delta){
        time += delta;
    }
    
    public void tick(float delta, Chunk chunk){
    
    }
    
    public int compareTo(Entity e) {
        float tempY =  e.pos.y;
        float compareY = pos.y;
        
        return (tempY < compareY ) ? -1: (tempY > compareY) ? 1:0 ;
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

        System.out.println("Start x: " + pos.x + " Dest x: " + dest.x + " x vector: " + destVec.x);
    }

}
