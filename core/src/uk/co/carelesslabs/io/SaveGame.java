package uk.co.carelesslabs.io;

import java.io.IOException;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import uk.co.carelesslabs.Enums.EntityType;
import uk.co.carelesslabs.GameClass;
import uk.co.carelesslabs.entity.Bird;
import uk.co.carelesslabs.entity.Hero;
import uk.co.carelesslabs.entity.Tree;
import uk.co.carelesslabs.managers.ObjectManager;
import uk.co.carelesslabs.map.Chunk;
import uk.co.carelesslabs.map.Tile;

public class SaveGame {
    Gson gson = new GsonBuilder()
    .serializeSpecialFloatingPointValues()
    .serializeNulls().setLenient()
    .create();
    String dir;
    Thread t;
    private boolean loading;
    
    public SaveGame(){
       gson = new Gson();
       dir = "/data/save/";
    }
    
    public void save(final ObjectManager objectManager){
        if(threadAlive()){
            System.out.println("Save already in progress.");
        } else {
            Runnable r = new Runnable() {
                public void run() {
                    
                    String gameEntities = gson.toJson(objectManager);
                    String compress = "";
                    
                    try {
                        compress = Zipper.compressString(gameEntities);   
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                           
                    FileHandle file = Gdx.files.local(dir + "entities.json");
                    file.writeString(compress, false);
                    System.out.println("Save Complete.");
                }
            };
            t = new Thread(r);
            t.start();
        }
    }
    
    public boolean threadAlive(){
        if(t == null) return false;
        if(t.isAlive()) return true;
        
        t = null;
        return false;
    }
    
    public boolean load(GameClass game){
        loading = true;
        
        FileHandle file = Gdx.files.local(dir + "entities.json");
        String json = file.readString();
        try {
            json = Zipper.uncompressString(json);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        game.getObjectManager().clearAll(game.box2D);
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject(); 
        
        // Generate all chunks and tiles 
        JsonObject jsonChunkObj = jsonObject.getAsJsonObject("chunks");
        
        //CHUNKS
        for(int i = 0; i < jsonChunkObj.size(); i++){
            // GET CHUNK 
            JsonObject chunk = jsonChunkObj.getAsJsonObject(String.valueOf(i));
            
            Chunk chunkObj = new Chunk(chunk);
            
            JsonArray chunkRows = chunk.getAsJsonArray("tiles");
            for(int j = 0; j < chunkRows.size(); j++){
                //Chunk row column
                JsonArray chunkRow = chunkRows.get(j).getAsJsonArray();
                
                ArrayList<Tile> tileArray = new ArrayList<Tile>();
                
                // Chunk row column tile
                for(JsonElement e : chunkRow){
                    JsonObject row = e.getAsJsonObject();
                    tileArray.add(new Tile(row, chunkObj, game.box2D));
                } 
                
                chunkObj.tiles.add(tileArray);
            }  
            
            // ADD THE CHUNK
            game.getObjectManager().chunks.put(chunkObj.chunkNumber, chunkObj);
        }
        game.getObjectManager().currentChunk = game.getObjectManager().chunks.get(4);
        
        // ENTITIES
        JsonArray entities = jsonObject.getAsJsonArray("entities");
        
        for (JsonElement entity : entities) {
            JsonObject e = entity.getAsJsonObject();
            String type = e.get("type").getAsString();
            EntityType eType = EntityType.valueOf(type);
            switch (eType) {
                case BIRD:
                    game.getEntities().add(new Bird(e, game.box2D, game.getObjectManager()));
                    break;
                case HERO:
                    Hero hero = new Hero(e, game.box2D);
                    game.getEntities().add(hero);
                    game.hero = hero;
                    break;
                case TREE:
                    game.getEntities().add(new Tree(e, game.box2D));
                    break;
                default:
                    System.out.println(eType);
            }
        }
        
        // Re-populate the entity map used for collisions
        game.box2D.populateEntityMap(game.getEntities());
        
        loading = false;
        
        return true;
    }
    
    public boolean isLoading(){
        return loading;
    }
    
}