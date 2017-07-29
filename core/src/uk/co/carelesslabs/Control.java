package uk.co.carelesslabs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Control extends InputAdapter implements InputProcessor {
    // CAMERA
    OrthographicCamera camera;
    
    // DIRECTIONS
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    
    // MOUSE
    public boolean  LMB;
    public boolean  RMB;
    public boolean  processed_click;
    public Vector2  mouse_click_pos = new Vector2();
    public Vector2  map_click_pos = new Vector2();
    
    // DEBUG
    public boolean debug;
    
    // SCREEN
    int screen_width;
    int screen_height;
    
    public Control(int screen_width, int screen_height, OrthographicCamera camera){
        this.camera = camera;
        this.screen_width = screen_width;
        this.screen_height = screen_height;
    }
    
    private void setMouseClickedPos(int screenX, int screenY){
     // Set mouse position (flip screen Y)
        mouse_click_pos.set(screenX, screen_height - screenY);
        map_click_pos.set(get_map_coords(mouse_click_pos));
    }
    
    public Vector2 get_map_coords(Vector2 mouse_coords){
        Vector3 v3 = new Vector3(mouse_coords.x, screen_height - mouse_coords.y, 0);
        this.camera.unproject(v3);
        return new Vector2(v3.x,v3.y);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
        case Keys.DOWN:
            down = true;
            break;
        case Keys.UP:
            up = true;
            break;
        case Keys.LEFT:
            left = true;
            break;
        case Keys.RIGHT:
            right = true;
            break;
        case Keys.W:
            up = true;
            break;
        case Keys.A:
            left = true;
            break;
        case Keys.S:
            down = true;
            break;
        case Keys.D:
            right = true;
            break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
        case Keys.DOWN:
            down = false;
            break;
        case Keys.UP:
            up = false;
            break;
        case Keys.LEFT:
            left = false;
            break;
        case Keys.RIGHT:
            right = false;
            break;
        case Keys.W:
            up = false;
            break;
        case Keys.A:
            left = false;
            break;
        case Keys.S:
            down = false;
            break;
        case Keys.D:
            right = false;
            break;
        case Keys.ESCAPE:
            Gdx.app.exit();
            break;
        case Keys.BACKSPACE:
            debug = !debug;
            break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0 && button == 0){
            LMB = true; 
        } else if (pointer == 0 && button == 0){
            RMB = true; 
        }
        
        setMouseClickedPos(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0 && button == 0){
            LMB = false; 
            processed_click = false;
        } else if (pointer == 0 && button == 0){
            RMB = false; 
        }
        
        setMouseClickedPos(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        setMouseClickedPos(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
