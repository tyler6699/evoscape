package uk.co.carelesslabs.box2d;

import uk.co.carelesslabs.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Box2DWorld {
    public World world;
    private Box2DDebugRenderer debugRenderer;
    
    public Box2DWorld(){
        world = new World(new Vector2(.0f, .0f), true);
        debugRenderer = new Box2DDebugRenderer();
    }
        
    public void tick(OrthographicCamera camera, Control control){
        if (control.debug) debugRenderer.render(world, camera.combined);
        
        world.step(Gdx.app.getGraphics().getDeltaTime(), 6, 2);
        world.clearForces();           
    }

    public void clearAllBodies() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(Body b: bodies){
            world.destroyBody(b);
        }
    }
    
}