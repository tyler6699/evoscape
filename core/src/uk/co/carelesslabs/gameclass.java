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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class gameclass extends ApplicationAdapter {
    OrthographicCamera camera;
    Control control;
    SpriteBatch batch, hudBatch;
    Matrix4 screenMatrix;
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
        
        // Setup Matrix4 for HUD
        screenMatrix = new Matrix4(batch.getProjectionMatrix().setToOrtho2D(0, 0, control.screenWidth, control.screenHeight));
        
        // Box2D
        box2D = new Box2DWorld();
        
        // Island
        island = new Island(box2D);
        
        // Hero
        hero = new Hero(island.centreTile.pos, box2D);
        island.entities.add(hero);
       
        // HashMap of Entities for collisions
        box2D.populateEntityMap(island.entities);  
        
        control.reset = true;
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // GAME LOGIC
        if(control.reset){
            resetGameState();
        }
        
        if(control.inventory){
            hero.inventory.print();
            control.inventory = false;
        }
        
        if(!control.processedClick){
            System.out.println("Clicked: " + control.mapClickPos + " " + hero.inventory.hasWood());
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
        
        // TEST DRAW GUI
        // TODO Move into entities / classes
        // Reset batch projection for screen size
        batch.setProjectionMatrix(screenMatrix);
        batch.begin();
        
        float scale = control.screenWidth / (Media.squareMenu.getWidth() + Media.mainBack.getWidth());
        batch.draw(Media.squareMenu, 0,0, Media.squareMenu.getHeight()*scale, Media.squareMenu.getWidth()*scale);
        batch.draw(Media.mainBack, Media.squareMenu.getWidth()*scale,0, Media.mainBack.getWidth()*scale, Media.mainBack.getHeight()*scale);
        
        // Main menu
        // Create new entities for each
        float offset = 3;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                batch.draw(Media.pinkButton, (offset + ((i+1)*offset) + (i * Media.pinkButton.getWidth())) * scale, (offset + ((j+1)*offset) + (j * Media.pinkButton.getHeight())) * scale, Media.pinkButton.getWidth()*scale, Media.pinkButton.getHeight()*scale);
            }
        }
        
        // Main menu
        // Create new entities for each
        for(int i = 0; i < 14; i++){
            for(int j = 0; j < 2; j++){
                batch.draw(Media.pinkButton, (Media.squareMenu.getWidth() + offset + ((i+1)*offset) + (i * Media.pinkButton.getWidth())) * scale, (offset + ((j+1)*offset) + (j * Media.pinkButton.getHeight())) * scale, Media.pinkButton.getWidth()*scale, Media.pinkButton.getHeight()*scale);
            }
        }
        
        batch.end();
        
        // END MENU TEST
        
        box2D.tick(camera, control);
        island.clearRemovedEntities(box2D);
        
        time += Gdx.graphics.getDeltaTime();
        if(time > 3){
            System.out.println(Gdx.graphics.getFramesPerSecond());    
            time = 0;
        }
        control.processedClick = true;
    }
	
    private void resetGameState() {     
        island.reset(box2D);
        hero.reset(box2D,island.getCentrePosition());
        island.entities.add(hero);
        
        for(int i = 0; i < MathUtils.random(20); i++){
            island.entities.add(new Bird(new Vector3(MathUtils.random(100),MathUtils.random(100),0), box2D, Enums.EnityState.FLYING));
        }
       
        box2D.populateEntityMap(island.entities);
        control.reset = false;   
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
