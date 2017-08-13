package uk.co.carelesslabs.map;

import uk.co.carelesslabs.Enums.TileType;
import uk.co.carelesslabs.entity.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile extends Entity {
    public int size;
    public int row;
    public int col;
    public String code;
    public Texture secondaryTexture;
    public Texture texture;
    public TextureRegion textureR;
    public TileType type;
    
    public Tile(float x, float y, int size, TileType type, Texture texture){
        super();
        
        pos.x = x * (size/2) - y * (size/2);
        pos.y = x * (size/2) + y * (size/2);
        
        this.size = size;
        this.texture = texture;
        textureR = new TextureRegion(this.texture);
        this.col = (int) x;
        this.row = (int) y;
        this.type = type;
        this.code = "";
    }

    public String details(){
        return "x: " + pos.x + " y: " + pos.y + " row: " + row + " col: " + col + " code: " + code + " type: " + type.toString();
    }

    public boolean isGrass() {
        return type == TileType.GRASS;
    }
    
    public boolean isWater() {
        return type == TileType.WATER;
    }
    
    public boolean isCliff() {
        return type == TileType.CLIFF;
    }
    
    public boolean isPassable() {
        return !isWater() && !isCliff();
    }
    
    public boolean isNotPassable() {
        return !isPassable();
    }
    
    public boolean isAllWater() {
        return code.equals("000000000");
    }
    
    public boolean notIsAllWater() {
        return !isAllWater();
    }
}
