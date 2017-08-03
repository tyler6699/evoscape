package uk.co.carelesslabs;

import uk.co.carelesslabs.Enums.EntityType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Entity {
    public Vector3 pos;
    public Texture texture;
    public float width;
    public float height;
    public EntityType type;
    public float speed;
    
    float dirX = 0;
    float dirY = 0;
    
    public Entity(){
        pos = new Vector3();
    }
    
    public void draw(SpriteBatch batch){
        batch.draw(texture, pos.x, pos.y, width, height);
    }

}
