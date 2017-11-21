package uk.co.carelesslabs.io;

import java.io.IOException;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;
import com.google.gson.Gson;
import uk.co.carelesslabs.entity.Entity;
import uk.co.carelesslabs.managers.ObjectManager;

public class SaveGame {
    Gson gson;
    String dir = "/data/save/";
    
    public SaveGame(){
       gson = new Gson();       
    }
    
    public boolean save(final ObjectManager objectManager){
        Runnable r = new Runnable() {
            public void run() {
                objectManager.isSaving = true;
                String gameEntities = gson.toJson(objectManager);
                String compress = "";
                
                try {
                    compress = Zipper.compressString(gameEntities);   
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                       
                FileHandle file = Gdx.files.local(dir + "entities.json");
                file.writeString(compress, false);
                
                objectManager.isSaving = false;
            }
        };
        Thread t = new Thread(r);
        t.start();
        
        return true;
    }
    
    public boolean load(ObjectManager objectManager){
        //Runnable r = new Runnable() {
        //    public void run() {
                objectManager.isLoading = true;

                FileHandle file = Gdx.files.local(dir + "entities.json");
                String json = file.readString();
                try {
                    json = Zipper.uncompressString(json);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                Gson gson = new Gson();
                String gameObjects = gson.toJson(json);
                ObjectManager om = gson.fromJson(json, ObjectManager.class);
                objectManager.isLoading = false;
        //    }
        //};
        //Thread t = new Thread(r);
        //t.start();
        
        return true;
    }
    
    public void clearEntities(ArrayList<Entity> entities){
        World world = entities.get(0).body.getWorld();
        for(Entity e : entities){            
            if (e.body != null) world.destroyBody(e.body);
            if (e.sensor != null) world.destroyBody(e.sensor);
        }
        entities.clear();
    }
}