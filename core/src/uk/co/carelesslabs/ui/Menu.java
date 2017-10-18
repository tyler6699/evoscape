package uk.co.carelesslabs.ui;

import java.util.ArrayList;
import uk.co.carelesslabs.Enums;
import uk.co.carelesslabs.Enums.MenuState;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Menu {
    public String name;
    public Vector2 pos;
    public Texture texture;
    public float width;
    public float height;
    public float scale;
    public MenuState state;
    public float time;
    public float coolDown;
    public Rectangle hitbox;
    public ArrayList<Button> buttons;

    public Menu(float x, float y, float scale, Texture texture){
        pos = new Vector2(x,y);
        hitbox = new Rectangle(x,y,width,height);
        this.texture = texture;
        width = texture.getWidth() * scale;
        height = texture.getHeight() * scale;
        buttons = new ArrayList<Button>();
        setActive();
    }
    
    public void draw(SpriteBatch batch){
        if(isActive()){
            if(texture != null) batch.draw(texture, pos.x, pos.y, width, height);
            for(Button b : buttons){
                b.draw(batch);
            }
        }
        
    }
    
    public boolean checkClick(Vector2 pos, boolean processedClick){
        boolean processed = false;
        if(!processedClick){
            if(hitbox.contains(pos)){
                System.out.println("Hit: " + name);
            }
            
            for(Button b : buttons){
                if(b.hitbox.contains(pos)){
                    if (b.listener != null) b.listener.onClick(b);
                    processed = true;
                    break;
                }
            }
        } else {
            return processedClick;
        }
        
        return processed;
    }

    public void addButtons(float offset, int columns, int rows, Texture texture, int scale) {
        for(int i = 0; i < columns; i++){
            for(int j = 0; j < rows; j++){
                float bx = pos.x + (offset + ((i+1)*offset) + (i * texture.getWidth())) * 2;
                float by = pos.y + (offset + ((j+1)*offset) + (j * texture.getHeight())) * 2;
                float width = texture.getWidth() * 2;
                float height = texture.getHeight() * 2;
                buttons.add(new Button(bx, by, width, height, texture));
            }
        }    
    }
    
    public boolean isActive(){
        return state == Enums.MenuState.ACTIVE;
    }
    
    public void setActive(){
        state = Enums.MenuState.ACTIVE;
    }
    
    public void setInactive(){
        state = Enums.MenuState.DISABLED;
    }

    public void toggleActive() {
        if(isActive()){
            setInactive();
        } else {
            setActive();
        }
    }

}
