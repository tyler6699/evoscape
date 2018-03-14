package uk.co.carelesslabs.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import uk.co.carelesslabs.Media;
import uk.co.carelesslabs.entity.Entity;

public class Gun extends Entity {
	float originXOffset; // OriginX Offset
	float originYOffset; // OriginY Offset
	float xPos;    		 // X offset for gun position
	float xMinPos; 		 // X Position offset facing left 
	float xMaxPos; 		 // X Position offset facing right
	
	public Gun(float originXOffset, float xMinRight, float xMaxRight){
		texture = Media.gun;
		width = texture.getWidth();
		height = texture.getHeight();
		active = true;
		originYOffset = height/2;
		this.originXOffset = originXOffset;
		this.xMinPos = xMinRight;
		this.xMaxPos = xMaxRight;
	}
	
	public void drawRotated(SpriteBatch batch){
    	if(angle > 90 && angle < 270){
    		xPos = xMinPos;
    		flipY = true;
    	} else {
    		xPos = xMaxPos;
    		flipY = false;
    	}
    	
    	if(texture != null) batch.draw(texture, pos.x + xPos, pos.y, originXOffset, originYOffset, width, height, 1, 1, angle, 0, 0, (int)width, (int)height, flipX, flipY);
    }

}