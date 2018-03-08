package uk.co.carelesslabs.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import uk.co.carelesslabs.Media;

public class Gun extends Entity {

	public Gun(){
		texture = Media.gun;
        width = texture.getWidth();
        height = texture.getHeight();
        active = true;
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
    }
}