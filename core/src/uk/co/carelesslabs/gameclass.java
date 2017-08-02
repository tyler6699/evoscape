package uk.co.carelesslabs;

import java.util.ArrayList;
import uk.co.carelesslabs.map.Tile;
import uk.co.carelesslabs.map.Island;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class gameclass extends ApplicationAdapter {
    OrthographicCamera camera;
    Control control;
    SpriteBatch batch;
    Texture img;

    // Display Size
    private int displayW;
    private int displayH;

    // Hero
    Hero hero;
    
    // Island
    Island island;

    @Override
    public void create () {
        Media.load_assets();
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        
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
        
        island = new Island();
        
        // Hero
        hero = new Hero(island.centre_tile.pos);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // GAME LOGIC
        hero.update(control);
            
        camera.position.x = hero.get_camera_x();
        camera.position.y = hero.get_camera_y();
        camera.update();
        
        // GAME DRAW
        batch.setProjectionMatrix(camera.combined);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        batch.begin();
        // Draw all tiles in the chunk / chunk rows
        for(ArrayList<Tile> row : island.chunk.tiles){
            for(Tile tile : row){
                batch.draw(tile.texture, tile.pos.x, tile.pos.y, tile.size, tile.size);
                if (tile.secondary_texture != null) batch.draw(tile.secondary_texture, tile.pos.x, tile.pos.y, tile.size, tile.size);
            }
        }
        hero.draw(batch);
        batch.end();
    }
	
    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}
