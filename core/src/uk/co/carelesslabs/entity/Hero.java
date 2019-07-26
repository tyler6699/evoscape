package uk.co.carelesslabs.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.google.gson.JsonObject;

import uk.co.carelesslabs.Control;
import uk.co.carelesslabs.Media;
import uk.co.carelesslabs.Enums.EntityType;
import uk.co.carelesslabs.box2d.Box2DHelper;
import uk.co.carelesslabs.box2d.Box2DWorld;
import uk.co.carelesslabs.entity.ammo.SimpleBullet;
import uk.co.carelesslabs.weapons.Gun;

public class Hero extends Entity{
    ArrayList<Entity> interactEntities;
	public Vector3 cameraPos;
    
    public Hero(Vector3 pos, Box2DWorld box2d){
    	super();
    	cameraPos = new Vector3();
    	type = EntityType.HERO;
    	width = 8;
    	height = 8;
    	texture = Media.hero;
        speed = 30;
        inventory = new Inventory();
        reset(box2d, pos);
        
        // Weapon
        Gun gun = new Gun(1, -1, 7);
        gun.addAmmo(10);
        weapons = new ArrayList<Gun>();
        weapons.add(gun);
        
    }
    
    public Hero(JsonObject e, Box2DWorld box2d) {
        super();
        type = EntityType.HERO;
        width = e.get("width").getAsInt();
        height = e.get("height").getAsInt();
        texture = Media.hero;
        speed = e.get("speed").getAsFloat();
        inventory = new Inventory();
        float jX = e.get("pos").getAsJsonObject().get("x").getAsFloat();
        float jY = e.get("pos").getAsJsonObject().get("y").getAsFloat();
        float jZ = e.get("pos").getAsJsonObject().get("z").getAsFloat();
        this.pos.set(jX, jY, jZ);
        reset(box2d, pos);
    }

    @Override
    public void draw(SpriteBatch batch){
        if(shadow != null) batch.draw(shadow, pos.x, pos.y, width, height);
        if(texture != null) batch.draw(texture, pos.x, pos.y, width, height);
        for(Gun g : weapons){
			if(g.active){
				g.tick(Gdx.graphics.getDeltaTime());
				g.drawRotated(batch);
			}	
		}
    }
    
    public void reset(Box2DWorld box2d, Vector3 pos) {
        this.pos.set(pos);
        body = Box2DHelper.createBody(box2d.world, width/2, height/2, width/4, 0, pos, BodyType.DynamicBody);  
        hashcode = body.getFixtureList().get(0).hashCode();
        interactEntities = new ArrayList<Entity>();
        inventory.reset();
    }

    public void update(Control control, Box2DWorld box2D) {
        dirX = 0;
        dirY = 0;
        
        if (control.down)  dirY = -1;
        if (control.up)    dirY = 1;
        if (control.left)  dirX = -1;
        if (control.right) dirX = 1;    
        
        body.setLinearVelocity(dirX * speed, dirY * speed);
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - height/4;

        // If interact key pressed and interactEntities present interact with first in list.
        if(control.interact && interactEntities.size() > 0){
        	interactEntities.get(0).interact(this);
        }
        
        // Update weapons
        for(Gun g : weapons){
        	if(g.active){
        		g.updatePos(pos.x, pos.y);
            	g.angle = control.angle - 90;
            	
            	if(control.spacePressed){
            		if(g.hasAmmo()){
            			SimpleBullet bullet = new SimpleBullet(g, box2D);
                		g.addActiveAmmo(bullet);
            		} else {
            			System.out.println("Clink");
            		}
            		
            		// Set to false
            		control.spacePressed = false;
            	}
        	}
        }
       
        // Reset interact
        control.interact = false;
        
        // Update Camera Position
        cameraPos.set(pos);
        cameraPos.x += width / 2;
    }
    
    @Override
    public void collision(Entity entity, boolean begin){
    	if(begin){
    		// Hero entered hitbox
    		interactEntities.add(entity);   		
    	} else {
    		// Hero Left hitbox
    		interactEntities.remove(entity);
    	}    	
    }

	public boolean weaponActive() {
		for(Gun g : weapons){
			if(g.active){
				return true;
			}	
		}
		return false;
	}

	public void clearAmmo(Box2DWorld box2D) {
		for(Gun g : weapons){
			g.clearTravelledAmmo(box2D);
		}
	}
 
}