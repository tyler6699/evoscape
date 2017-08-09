package uk.co.carelesslabs.box2d;

import java.util.ArrayList;
import java.util.HashMap;
import uk.co.carelesslabs.Control;
import uk.co.carelesslabs.entity.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Box2DWorld {
    public World world;
    private Box2DDebugRenderer debugRenderer;
    private HashMap<Integer, Entity> entityMap;
    
    public Box2DWorld(){
        world = new World(new Vector2(.0f, .0f), true);
        debugRenderer = new Box2DDebugRenderer();
        entityMap = new HashMap<Integer, Entity>();
        
        world.setContactListener(new ContactListener() {
        	 @Override
             public void beginContact(Contact contact) {
        		 
                 Fixture fixtureA = contact.getFixtureA();
                 Fixture fixtureB = contact.getFixtureB();
                 
                 process_collisions(fixtureA, fixtureB, true);
             }

             @Override
             public void endContact(Contact contact) {
                 Fixture fixtureA = contact.getFixtureA();
                 Fixture fixtureB = contact.getFixtureB();
                 
                 process_collisions(fixtureA, fixtureB, false);
             }
             
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {}

        });
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
        
        entityMap.clear();
    }
    
    private void process_collisions(Fixture aFixture, Fixture bFixture, boolean begin) {	
    	Entity entityA = entityMap.get(aFixture.hashCode());
		Entity entityB = entityMap.get(bFixture.hashCode());
		
		if(entityA != null && entityB != null){			
			if(aFixture.isSensor() && !bFixture.isSensor()){
				entityB.collision(entityA, begin);
			} else if(bFixture.isSensor() && !aFixture.isSensor()){
				entityA.collision(entityB, begin);
			}			
		}  	
    }
    
    public void populateEntityMap(ArrayList<Entity> entities){
    	entityMap.clear();
    	for(Entity e: entities){
    		entityMap.put(e.hashcode, e);
        }
    }
    
    public void addEntityToMap(Entity entity){
    	entityMap.put(entity.hashcode, entity);
    }
    
    public void removeEntityToMap(Entity entity){
    	entityMap.remove(entity.hashcode);
    }
    
}