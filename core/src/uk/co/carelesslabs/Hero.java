package uk.co.carelesslabs;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import uk.co.carelesslabs.Enums.EntityType;
import uk.co.carelesslabs.box2d.Box2DHelper;
import uk.co.carelesslabs.box2d.Box2DWorld;

public class Hero extends Entity{
    
    public Hero(Vector3 pos, Box2DWorld box2d){
        type = EntityType.HERO;
        width = 8;
        height = 8;
        this.pos.x = pos.x;
        this.pos.y = pos.y;
        texture = Media.hero;
        speed = 30;
        body = Box2DHelper.createBody(box2d.world, width, height/2, pos, BodyType.DynamicBody);
    }

    public void update(Control control) {
        dirX = 0;
        dirY = 0;
        
        if (control.down)  dirY = -1;
        if (control.up)    dirY = 1;
        if (control.left)  dirX = -1;
        if (control.right) dirX = 1;    
        
        body.setLinearVelocity(dirX * speed, dirY * speed);
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - height/4;
    }

    public float getCameraX() {
        return pos.x + width/2;
    }
    
    public float getCameraY() {
        return pos.y + height/2;
    }
    
}