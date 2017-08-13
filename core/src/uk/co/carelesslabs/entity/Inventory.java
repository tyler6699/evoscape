package uk.co.carelesslabs.entity;

import java.util.HashMap;

public class Inventory {
    HashMap<Integer, Entity> entities;
    
    public Inventory(){
        reset();
    }
    
    public int getInventorySize(){
        return entities.size();
    }

    public void addEntity(Entity entity) {
        entities.put(getInventorySize(), entity);
    }
    
    public HashMap<Integer, Entity> getInventory(){
        return entities;
    }

    public void print() {
        System.out.println("*** Inventory ***");
        for(int i = 0 ; i < entities.size(); i++){
            Entity e = entities.get(i);
            System.out.println("* ["+i+"] " + e.type.toString());
        }    
        System.out.println("*****************");
    }
    
    public void reset() {
        entities = new HashMap<Integer, Entity>();
    }
}
