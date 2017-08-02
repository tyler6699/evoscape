package uk.co.carelesslabs;

import uk.co.carelesslabs.Enums.ENTITYTYPE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Entity {
    public Vector2 pos;
    public Vector3 pos3;
    public Texture texture;
    public float width;
    public float height;
    public ENTITYTYPE type;
    public float speed;
    
    float dir_x = 0;
    float dir_y = 0;
    
    public Entity(){
        pos = new Vector2();
        pos3 = new Vector3();
    }
    
    public void draw(SpriteBatch batch){
        batch.draw(texture, pos.x, pos.y, width, height);
    }

}
