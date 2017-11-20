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
    String dir;
    Thread t;
    
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
    
    public boolean load(final ObjectManager objectManager){
        Runnable r = new Runnable() {
            public void run() {
                FileHandle file = Gdx.files.local(dir + "entities.json");
                String json = file.readString();
                try {
                    System.out.println(Zipper.uncompressString(json));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
        
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