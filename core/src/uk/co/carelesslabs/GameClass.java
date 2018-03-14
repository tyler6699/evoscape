package uk.co.carelesslabs;

import java.util.ArrayList;
import java.util.Collections;
import uk.co.carelesslabs.box2d.Box2DWorld;
import uk.co.carelesslabs.entity.Bird;
import uk.co.carelesslabs.entity.Entity;
import uk.co.carelesslabs.entity.Hero;
import uk.co.carelesslabs.io.SaveGame;
import uk.co.carelesslabs.managers.ObjectManager;
import uk.co.carelesslabs.map.Chunk;
import uk.co.carelesslabs.map.Tile;
import uk.co.carelesslabs.map.Island;
import uk.co.carelesslabs.ui.SquareMenu;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class GameClass extends ApplicationAdapter {
    OrthographicCamera camera;
    public Control control;
    SpriteBatch batch;
    Matrix4 screenMatrix;
    public Box2DWorld box2D;
    public SaveGame saveGame;

    // Display Size
    private int displayW;
    private int displayH;

    // Hero
    public Hero hero;
    
    // Island
    Island island;
    
    // TIME
    float time;
    
    // Menu test
    SquareMenu squareMenu;
        
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
        // TODO: Set hero position after island has been reset
        // Currently it is reset twice.
        hero = new Hero(new Vector3(200,200,0), box2D);
        island.objectManager.entities.add(hero);
       
        // HashMap of Entities for collisions
        box2D.populateEntityMap(island.objectManager.entities);  
        
        control.reset = true;
        
        //Menu
        squareMenu = new SquareMenu(this);
        
        // Game Saving
        saveGame = new SaveGame();
    }

    @Override
    public void render() { 
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
        
        // Menu Logic
        control.processedClick = squareMenu.checkClick(control.mouseClickPos, control.processedClick);
        control.processedClick = squareMenu.build.checkClick(control.mouseClickPos, control.processedClick);
        squareMenu.checkHover(control.mousePos);
        
        hero.update(control);
        
        // Heo Position
        if (Rumble.getRumbleTimeLeft() > 0){
            Rumble.tick(Gdx.graphics.getDeltaTime());
            camera.translate(Rumble.getPos());
        } else {
            camera.position.lerp(hero.cameraPos, .2f);
        }
        
        // Tick all entities
        for(Entity e: island.objectManager.entities){
            e.tick(Gdx.graphics.getDeltaTime());
            Chunk chunk = island.chunkAt(e.body.getPosition());
            if(chunk != null) e.currentTile = chunk.getTile(e.body.getPosition());
            
            e.tick(Gdx.graphics.getDeltaTime(), island.objectManager.currentChunk);
        }
        
        camera.update();
        
        // While load / saving do not sort entities
        if(island.hasEntities() && !saveGame.threadAlive() && !saveGame.isLoading()){
            Collections.sort(island.objectManager.entities);
        }
                
        // GAME DRAW
        batch.setProjectionMatrix(camera.combined);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        batch.begin();
        // TODO: Draw all tiles in the hero chunk only
        // TODO: Improve tiles rendered.
        for (Integer key : island.objectManager.chunks.descendingKeySet()) {
            Chunk chunk = island.objectManager.chunks.get(key);
            
            for(ArrayList<Tile> row : chunk.tiles){
                for(Tile tile : row){
                    tile.draw(batch);
                }
            }    
        }
        
        // Draw all entities
        // TODO: Only tick / Draw entities in the current chunk?
        for(Entity e: island.objectManager.entities){
            //e.draw(btach, currentChunk) Use current chunk to determine if render occurs
            e.draw(batch);
        }
        
        batch.end();
        
        // GUI
        batch.setProjectionMatrix(screenMatrix);
        
        batch.begin(); 
        squareMenu.draw(batch);
        batch.end();
        
        box2D.tick(camera, control);
        island.clearRemovedEntities(box2D);
        
        time += Gdx.graphics.getDeltaTime();
        if(time > 3){
            if(control.debug) System.out.println(Gdx.graphics.getFramesPerSecond());    
            time = 0;
        }
        control.processedClick = true;
    }
	
    private void resetGameState() {     
        island.reset(box2D);
        hero.reset(box2D,island.getCentrePosition());
        island.objectManager.entities.add(hero);
        
        for(int i = 0; i < MathUtils.random(10) + 1; i++){
            island.objectManager.entities.add(new Bird(new Vector3(MathUtils.random(300),MathUtils.random(300),0), box2D, Enums.EnityState.FLYING));
        }
        
        box2D.populateEntityMap(island.objectManager.entities);
        control.reset = false;   
    }

    @Override
    public void dispose () {
        batch.dispose();
    }

    public ArrayList<Entity> getEntities() {
        return island.objectManager.entities;
    }

    public ObjectManager getObjectManager() {
        return island.objectManager;
    }
}
