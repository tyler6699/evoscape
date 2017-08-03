package uk.co.carelesslabs;

import com.badlogic.gdx.math.Vector3;
import uk.co.carelesslabs.Enums.EntityType;

public class Hero extends Entity{
    
    public Hero(Vector3 pos){
        type = EntityType.HERO;
        width = 8;
        height = 8;
        this.pos.x = pos.x;
        this.pos.y = pos.y;
        texture = Media.hero;
        speed = 1;
    }

    public void update(Control control) {
        dirX = 0;
        dirY = 0;
        
        if (control.down)  dirY = -1;
        if (control.up)    dirY = 1;
        if (control.left)  dirX = -1;
        if (control.right) dirX = 1;    
        
        pos.x += dirX * speed;
        pos.y += dirY * speed;
    }

    public float getCameraX() {
        return pos.x + width/2;
    }
    
    public float getCameraY() {
        return pos.y + height/2;
    }

}