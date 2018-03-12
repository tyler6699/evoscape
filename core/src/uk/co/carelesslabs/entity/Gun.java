package uk.co.carelesslabs.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import uk.co.carelesslabs.Media;

public class Gun extends Entity {
	float xOffset;
	float xRight;
	float xMinRight;
	float xMaxRight;
	
	public Gun(float xOffset, float xMinRight, float xMaxRight){
		texture = Media.gun;
		width = texture.getWidth();
		height = texture.getHeight();
		active = true;
		this.xOffset = xOffset;
		this.xMinRight = xMinRight;
		this.xMaxRight = xMaxRight;
	}
	
	public void drawRotated(SpriteBatch batch){
		// Update angle to match draw angle
		angle -= 90;
		
    	if(angle > 90 && angle < 270){
        	xRight = xMinRight;
    		flipY = true;
    	} else {
    		xRight = xMaxRight;
    		flipY = false;
    	}
    	
    	if(shadow != null) batch.draw(shadow, pos.x, pos.y, width, height);
    	batch.draw(texture, pos.x + xRight, pos.y, width/2 - xOffset, height/2, width, height, 1, 1, angle, 0, 0, (int)width, (int)height, flipX, flipY);
    }

}