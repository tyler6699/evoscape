package uk.co.carelesslabs.ui;

import uk.co.carelesslabs.Enums;
import uk.co.carelesslabs.entity.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button extends Entity {
    public OnClickListener listener;
    public Rectangle hitbox;
    public Texture icon;
    public Entity selector;
    
    public Button(float x, float y, float width, float height, Texture texture, Entity selector) {
        super();
        this.texture = texture;
        this.selector = selector;
        this.pos.x = x;
        this.pos.y = y;
        this.width = width;
        this.height = height;
        hitbox = new Rectangle(pos.x, pos.y, width, height);
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener; 
    }
    
    @Override
    public void draw(SpriteBatch batch){
        if(texture != null) batch.draw(texture, pos.x, pos.y, width, height);
        if(icon != null) batch.draw(icon, pos.x, pos.y, width, height);
        if(isHovered() && selector != null){
            selector.draw(batch);
        }
    }
    
    private boolean isHovered(){
        return state == Enums.EnityState.HOVERING;
    }

    public void updateHitbox() {
        hitbox.set(pos.x, pos.y, width, height);  
    }
}
