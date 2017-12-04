package uk.co.carelesslabs.entity;

import uk.co.carelesslabs.Enums.EnityState;
import uk.co.carelesslabs.Enums.EntityType;
import uk.co.carelesslabs.box2d.Box2DWorld;
import uk.co.carelesslabs.map.Chunk;
import uk.co.carelesslabs.map.Tile;
import uk.co.carelesslabs.ui.SquareMenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

public class Entity implements Comparable<Entity> {
    public int hashcode;
    public Vector3 pos;
    public Vector3 destVec;
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
    transient public Tile currentTile;
    public boolean showMenu;
    public SquareMenu popupMenu;
    
    public int water = 100;
    public int feed = 100;
    public int trim = 100;
    
    float dirX = 0;
    float dirY = 0;
    
    public int waterLevel;
    public int foodLevel;
    
    public Hero hero;
    
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
    
    public void interact(Entity entity, int water, int food){}

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

    public void hover(Entity entity) {
        
    }

    public void drawMenu(SpriteBatch batch) { 
    }

}
