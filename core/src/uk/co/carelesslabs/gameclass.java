package uk.co.carelesslabs;

import java.util.ArrayList;

import uk.co.carelesslabs.box2d.Box2DWorld;
import uk.co.carelesslabs.map.Tile;
import uk.co.carelesslabs.map.Island;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
        camera.zoom = .4f;
        
        // Used to capture Keyboard Input
        control = new Control(displayW, displayH, camera);
        Gdx.input.setInputProcessor(control);
        
        // Box2D
        box2D = new Box2DWorld();
        
        // Island
        island = new Island(box2D);
        
        // Hero
        hero = new Hero(island.centreTile.pos, box2D);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // GAME LOGIC
        hero.update(control);
        
        camera.position.lerp(hero.pos, .1f);
        camera.update();
        
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
        hero.draw(batch);
        batch.end();
        
        box2D.tick(camera, control);
    }
	
    @Override
    public void dispose () {
        batch.dispose();
    }
}
