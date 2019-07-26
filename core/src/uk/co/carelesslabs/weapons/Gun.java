package uk.co.carelesslabs.weapons;

import java.util.ArrayList;
import java.util.Iterator;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import uk.co.carelesslabs.Media;
import uk.co.carelesslabs.box2d.Box2DWorld;
import uk.co.carelesslabs.entity.Entity;
import uk.co.carelesslabs.entity.ammo.Ammo;

public class Gun extends Entity {
	float originXOffset; // OriginX Offset
	float originYOffset; // OriginY Offset
	float xPos;    		 // X offset for gun position
	float xMinPos; 		 // X Position offset facing left 
	float xMaxPos; 		 // X Position offset facing right
	float ammoCount;
	public ArrayList<Ammo> activeAmmo;
	
	public Gun(float originXOffset, float xMinRight, float xMaxRight){
		texture = Media.gun;
		width = texture.getWidth();
		height = texture.getHeight();
		active = true;
		originYOffset = height/2;
		this.originXOffset = originXOffset;
		this.xMinPos = xMinRight;
		this.xMaxPos = xMaxRight;
		activeAmmo = new ArrayList<Ammo>();
	}
	
	public void tick(float delta){
		for(Ammo a : activeAmmo){
			a.tick(delta);
		}
	}
	
	public void addActiveAmmo(Ammo a){
		activeAmmo.add(a);
		ammoCount --;
	}
	
	public void drawRotated(SpriteBatch batch){
    	if(angle > 90 && angle < 270){ // 6 to 12 Clockwise or LEFT
    		xPos = xMinPos;
    		flipY = true;
    	} else { // 12 to 6 clockwise or RIGHT
    		xPos = xMaxPos;
    		flipY = false;
    	}
    	
    	if(texture != null) batch.draw(texture, pos.x + xPos, pos.y, originXOffset, originYOffset, width, height, 1, 1, angle, 0, 0, (int)width, (int)height, flipX, flipY);
    	
    	for(Ammo a : activeAmmo){
    		
    		a.draw(batch);
		}
	}
	
	public void addAmmo(int count) {
		ammoCount += count;
	}

	public void clearTravelledAmmo(Box2DWorld box2D) {
		Iterator<Ammo> it = activeAmmo.iterator();
        while(it.hasNext()) {
            Ammo a = it.next();
            if(a.remove){
                a.removeBodies(box2D);
                box2D.removeEntityToMap(a);
       	
                it.remove();
            }
        }
	}
	
	public boolean hasAmmo(){
		return ammoCount > 0;
	}

}