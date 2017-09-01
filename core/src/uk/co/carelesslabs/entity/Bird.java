package uk.co.carelesslabs.entity;

import java.util.ArrayList;
import uk.co.carelesslabs.Enums;
import uk.co.carelesslabs.Enums.EntityType;
import uk.co.carelesslabs.Enums.TileType;
import uk.co.carelesslabs.Media;
import uk.co.carelesslabs.Rumble;
import uk.co.carelesslabs.box2d.Box2DHelper;
import uk.co.carelesslabs.box2d.Box2DWorld;
import uk.co.carelesslabs.map.Chunk;
import uk.co.carelesslabs.map.Tile;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class Bird extends Entity{
    public float flightY = 0;
    private float maxHeight;
    public Tile destTile;
    private TextureRegion tRegion;
    
    public Bird(Vector3 pos, Box2DWorld box2d, Enums.EnityState state){
        super();
        maxHeight = setHeight();
        type = EntityType.BIRD;
        speed = 30;
        width = 8;
        height = 8;
        texture = Media.tree;
        shadow = Media.birdShadow;
        this.pos.set(pos);
        body = Box2DHelper.createBody(box2d.world, width/2, height/2, width/4, 0, pos, BodyDef.BodyType.StaticBody);
        sensor = Box2DHelper.createSensor(box2d.world, width, height*.85f, width/2, height/3, pos, BodyDef.BodyType.DynamicBody);     
        hashcode = sensor.getFixtureList().get(0).hashCode();
        this.state = state;
        ticks = true;
    }

    @Override
    public void draw(SpriteBatch batch){
        setTextureRegion();
        setFlipped();
        
        batch.draw(Media.birdShadow, pos.x, pos.y);
        batch.draw(tRegion, pos.x, pos.y + flightY);
    }
    
    @Override
    public void tick(float delta, Chunk chunk){ 
        // Reached destination ?
        if(needsDestination()){
            if(MathUtils.randomBoolean(.85f) || currentTile.isWater()){
                setDestination(delta, chunk);
                maxHeight = setHeight();    
            } else {
                state = Enums.EnityState.HOVERING;
            }
            
        } else if (hasDestination()) {
            moveToDestination(delta);
            if(atDestination()){
                destVec = null;
                destTile = null;
            }
        } else if(isNotAirBorn()){
            if(MathUtils.randomBoolean(.02f)){
                state = Enums.EnityState.FLYING;
            } else if(MathUtils.randomBoolean(.5f)) {
                state = Enums.EnityState.FEEDING;
            }  
        }

        // If flying check height
        if(isFLying()){
            if (isNotHigh()) flightY += 0.1; 
            if (isTooHigh()) flightY -= 0.1;
            
            body.setActive(false);
        } else {
            if (isAirBorn()) flightY -= 0.5; 
            if(flightY < 0) flightY = 0;
            body.setActive(true);
        }
        

    }
    
    private boolean atDestination() {
        return currentTile.pos.epsilonEquals(destTile.pos, 20);
    }

    private boolean needsDestination() {
        return destVec == null && isFLying();
    }
    
    private boolean hasDestination() {
        return destVec != null;
    }

    private void setTextureRegion(){
        if(state == Enums.EnityState.FLYING){
            tRegion = Media.birdFlyAnim.getKeyFrame(time, true);
        } else if(state == Enums.EnityState.WALKING){
            tRegion = Media.birdWalkAnim.getKeyFrame(time, true);
        } else if(state == Enums.EnityState.FEEDING){
            if(isAirBorn()){
                tRegion = Media.birdFlyAnim.getKeyFrame(time, true);
            } else {
                tRegion = Media.birdPeckAnim.getKeyFrame(time, true);
            }
        }
    }
    
    private void setFlipped(){
        if(destVec != null){
            if(destVec.x > 0 && !tRegion.isFlipX()){
                tRegion.flip(true, false); 
            } else if(destVec.x < 0 && tRegion.isFlipX()) {
                tRegion.flip(true, false);
            }   
        }
    }
    
    private void moveToDestination(float delta) {
        // Using own lerp function
        // body.setTransform(body.getPosition().x + (destVec.x * speed * delta), body.getPosition().y + (destVec.y * speed * delta), 0);
        
        // https://github.com/libgdx/libgdx/wiki/Interpolation
        body.setTransform(body.getPosition().interpolate(new Vector2(destTile.pos.x + width, destTile.pos.y + height), delta * speed / 4, Interpolation.circle),0);
        
        // Lerp - Standard Lineer movement
        //body.setTransform(body.getPosition().lerp(new Vector2(destTile.pos.x + width, destTile.pos.y + height), delta * speed/10),0);
        
        updatePositions();
    }

    private void setDestination(float delta, Chunk chunk){    
        System.out.println("Find land"); 
        for(ArrayList<Tile> row : chunk.tiles){
            if(destTile != null) break;
            
            for(Tile tile : row){
                if (tile.type == TileType.GRASS && MathUtils.random(100) > 99 && tile != currentTile){
                    System.out.println("Found Land & set destination.");
                    destTile = tile;
                    getVector(destTile.pos);
                    break;
                }
            }
        }
        
    }
    
    private void updatePositions() {
        sensor.setTransform(body.getPosition(),0);
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - height/4;    
    }

    @Override
    public void interact(Entity entity){
        if(entity.inventory != null){
            entity.inventory.addEntity(this);
            remove = true;
            Rumble.rumble(1, .2f);
        }
    }
    
    public boolean isAirBorn(){
        return flightY > 0;
    }
    
    public boolean isNotAirBorn(){
        return flightY == 0;
    }
    
    public boolean isHigh(){
        return flightY == maxHeight;
    }
    
    public boolean isNotHigh(){
        return flightY < maxHeight;
    }
    
    public boolean isTooHigh(){
        return flightY > maxHeight;
    }
    
    private float setHeight() {
        return MathUtils.random(10) + 10;
    }
    
    private boolean isFLying() {
        return state == Enums.EnityState.FLYING;
    }
}