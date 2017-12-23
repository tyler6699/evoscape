package uk.co.carelesslabs.map;

import uk.co.carelesslabs.Enums.TileType;
import uk.co.carelesslabs.Media;
import uk.co.carelesslabs.box2d.Box2DHelper;
import uk.co.carelesslabs.box2d.Box2DWorld;
import uk.co.carelesslabs.entity.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Tile extends Entity {
    public int chunk;
    public int size;
    public int row;
    public int col;
    public String code;
    transient public Texture secondaryTexture;
    public TileType tileType;
    
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
    
    public Tile(JsonObject json, Chunk chunk, Box2DWorld box2D) {
        super();
        
        this.chunk = chunk.chunkNumber;
        
        Gson gson = new Gson(); 
        pos = gson.fromJson(json.get("pos"), Vector3.class);
        size = json.get("size").getAsInt();
        tileType = TileType.valueOf(json.get("tileType").getAsString()); 
        col = json.get("col").getAsInt();
        row = json.get("row").getAsInt();
        code = json.get("code").getAsString();
        
        switch (tileType) {
        case  GRASS: texture = MapGenerator.randomGrass();
                 break;
        case WATER: texture = MapGenerator.randomWater();
                 break;
        case CLIFF:  texture = Media.cliff;
                 break;  
        }
        
        // Generate hitboxes
        if(isNotPassable() && notIsAllWater()){
            Box2DHelper.createBody(box2D.world, chunk.tileSize, chunk.tileSize, 0, 0, pos, BodyType.StaticBody);
        }
        
        // Set secondary texture
        MapGenerator.setTileSecondaryTexture(this);
    }

    @Override
    public void draw(SpriteBatch batch){
        if(shadow != null) batch.draw(shadow, pos.x, pos.y, width, height);
        if(texture != null) batch.draw(texture, pos.x, pos.y, size, size);
        if(secondaryTexture != null) batch.draw(secondaryTexture, pos.x, pos.y, size, size);
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
