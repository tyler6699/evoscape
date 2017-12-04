package uk.co.carelesslabs.map;

import uk.co.carelesslabs.Enums.TileType;
import uk.co.carelesslabs.entity.Entity;
import com.badlogic.gdx.graphics.Texture;

public class Tile extends Entity {
    public int chunk;
    public int size;
    public int row;
    public int col;
    public String code;
    transient public Texture secondaryTexture;
    public TileType tileType;
    public boolean hasEntity;
    
    public Tile(float x, float y, int size, TileType type, Texture texture, Chunk chunk){
        super();
        this.chunk = chunk.chunkNumber;
        pos.x = (x*size) + (chunk.col * (chunk.numberCols * chunk.tileSize));
        pos.y = (y*size) + (chunk.row * (chunk.numberRows * chunk.tileSize));
        
        this.size = size;
        this.texture = texture;
        this.col = (int) x;
        this.row = (int) y;
        this.tileType = type;
        this.code = "";
        this.chunk = chunk.chunkNumber;
    }

    public String details(){
        return "x: " + pos.x + " y: " + pos.y + " row: " + row + " col: " + col + " code: " + code + " type: " + type.toString();
    }

    public boolean isGrass() {
        return tileType == TileType.GRASS;
    }
    
    public boolean isWater() {
        return tileType == TileType.WATER;
    }
    
    public boolean isCliff() {
        return tileType == TileType.CLIFF;
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
