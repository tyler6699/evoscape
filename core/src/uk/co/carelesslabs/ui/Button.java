package uk.co.carelesslabs.ui;

import uk.co.carelesslabs.entity.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Button extends Entity {
    public OnClickListener listener;
    public Rectangle hitbox;
    
    public Button(float x, float y, float width, float height, Texture texture) {
        super();
        this.texture = texture;
        this.pos.x = x;
        this.pos.y = y;
        this.width = width;
        this.height = height;
        hitbox = new Rectangle(pos.x, pos.y, width, height);
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener; 
    }
}
