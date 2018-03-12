package uk.co.carelesslabs.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import uk.co.carelesslabs.Media;

public class Gun extends Entity {
	float xOffset;
	float xSetOffset;
	float xRight;
	float xMinRight;
	float xMaxRight;
	
	public Gun(float xSetOffset, float xMinRight, float xMaxRight){
		texture = Media.gun;
        width = texture.getWidth();
        height = texture.getHeight();
        active = true;
        this.xSetOffset = xSetOffset;
        this.xMinRight = xMinRight;
        this.xMaxRight = xMaxRight;
	}
	
	public void drawRotated(SpriteBatch batch){
    	if(angle > 90 && angle < 270){
    		xOffset = xSetOffset;
        	xRight = xMinRight;
    		flipY = true;
    	} else {
    		flipY = false;
    		xRight = xMaxRight;
    	}
    	
    	if(shadow != null) batch.draw(shadow, pos.x, pos.y, width, height);
    	batch.draw(texture, pos.x + xRight, pos.y, width/2 - xOffset, height/2, width, height, 1, 1, angle, 0, 0, (int)width, (int)height, flipX, flipY);
    }

}