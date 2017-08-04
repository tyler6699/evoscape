package uk.co.carelesslabs.box2d;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DHelper {
    
    public static Body createBody(World world, float width, float height, Vector3 pos, BodyDef.BodyType type) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x + width/2, pos.y + height/2);
        bodyDef.angle = 0;
        bodyDef.fixedRotation = true;
        bodyDef.type = type;
        body = world.createBody(bodyDef);
        
        FixtureDef fixtureDef = new FixtureDef();        
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(width / 2, height / 2);
        
        fixtureDef.shape = boxShape;
        fixtureDef.restitution = 0.4f;
        
        body.createFixture(fixtureDef);
        boxShape.dispose();
        
        return body;
    }
    
}
