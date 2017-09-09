package uk.co.carelesslabs;

import java.util.ArrayList;
import java.util.Collections;
import uk.co.carelesslabs.box2d.Box2DWorld;
import uk.co.carelesslabs.entity.Bird;
import uk.co.carelesslabs.entity.Entity;
import uk.co.carelesslabs.entity.Hero;
import uk.co.carelesslabs.map.Tile;
import uk.co.carelesslabs.map.Island;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class gameclass extends ApplicationAdapter {
    OrthographicCamera camera;
    Control control;
    SpriteBatch batch;
    Box2DWorld box2D;

    // Display Size
    private int displayW;
    private int displayH;

    // Hero
    Hero hero;
    
    // Island
    Island island;
    
    // TIME
    float time;
        
    @Override
    public void create() {
        Media.load_assets();
        batch = new SpriteBatch();
        
        // CAMERA
        displayW = Gdx.graphics.getWidth();
        displayH = Gdx.graphics.getHeight();
        
        // For 800x600 we will get 266*200
        int h = (int) (displayH/Math.floor(displayH/160));
        int w = (int) (displayW/(displayH/ (displayH/Math.floor(displayH/160))));
        
        camera = new OrthographicCamera(w,h);
        camera.zoom = .6f;
        
        // Used to capture Keyboard Input
        control = new Control(displayW, displayH, camera);
        Gdx.input.setInputProcessor(control);
        
        // Box2D
        box2D = new Box2DWorld();
        
        // Island
        island = new Island(box2D);
        
        // Hero
        hero = new Hero(island.centreTile.pos, box2D);
        island.entities.add(hero);
        
        // Bird
        island.entities.add(new Bird(new Vector3(10,10,0), box2D, Enums.EnityState.FLYING));
        
        // HashMap of Entities for collisions
        box2D.populateEntityMap(island.entities);  
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // GAME LOGIC
        if(control.reset){
            island.reset(box2D);
            hero.reset(box2D,island.getCentrePosition());
            island.entities.add(hero);
            island.entities.add(new Bird(new Vector3(10,10,0), box2D, Enums.EnityState.FLYING));
            
            box2D.populateEntityMap(island.entities);
            control.reset = false;
        }
        
        if(control.inventory){
            hero.inventory.print();
            control.inventory = false;
        }
        
        hero.update(control);
        
        // Hero Position
        if (Rumble.getRumbleTimeLeft() > 0){
            Rumble.tick(Gdx.graphics.getDeltaTime());
            camera.translate(Rumble.getPos());
        } else {
            camera.position.lerp(hero.pos, .2f);
        }
        
        // Tick all entities
        for(Entity e: island.entities){
            e.tick(Gdx.graphics.getDeltaTime());
            e.currentTile = island.chunk.getTile(e.body.getPosition());
            e.tick(Gdx.graphics.getDeltaTime(), island.chunk);
        }
        
        camera.update();
        
        Collections.sort(island.entities);
                
        // GAME DRAW
        batch.setProjectionMatrix(camera.combined);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        batch.begin();
        // Draw all tiles in the chunk / chunk rows
        for(ArrayList<Tile> row : island.chunk.tiles){
            for(Tile tile : row){
                batch.draw(tile.texture, tile.pos.x, tile.pos.y, tile.size, tile.size);                
                if (tile.secondaryTexture != null) batch.draw(tile.secondaryTexture, tile.pos.x, tile.pos.y, tile.size, tile.size);
            }
        }
        
        // Draw all entities
        for(Entity e: island.entities){
            e.draw(batch);
        }
        
        batch.end();
        
        box2D.tick(camera, control);
        island.clearRemovedEntities(box2D);
        
        time += Gdx.graphics.getDeltaTime();
    }
	
    @Override
    public void dispose () {
        batch.dispose();
    }
}
