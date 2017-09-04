package uk.co.carelesslabs.entity;

import java.util.ArrayList;
import uk.co.carelesslabs.Enums;
import uk.co.carelesslabs.Enums.EntityType;
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
    // pos.z is the height off the floor
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
        batch.draw(tRegion, pos.x, pos.y + pos.z);
    }
    
    @Override
    public void tick(float delta, Chunk chunk){ 
        if(isHovering()){
            setLanding();
        } else if(isLanding()){
            land(); 
        } else if(needsDestination()){
            newDestinationOrHover(delta, chunk);
        } else if(hasDestination()) {
            moveToDestination(delta);
            clearDestination();
        } else if(isNotAirBorn()){
            setNewState(delta);
        }

        if(isFlying()){
            checkFlyHeight();
            toggleHitboxes(false);
        }
    }

    @Override
    public void interact(Entity entity){
        if(entity.inventory != null){
            entity.inventory.addEntity(this);
            remove = true;
            Rumble.rumble(1, .2f);
        }
    }
    
    private void toggleHitboxes(boolean b) {
        body.setActive(b);
        sensor.setActive(b);
    }

    private void setNewState(float delta) {
        if(coolDown > 0){
            coolDown -= delta;
            if(isWalking()){
                walk(delta);
            }
        } else {
            if(MathUtils.randomBoolean(.2f)){
                state = Enums.EnityState.FLYING;
            } else if(MathUtils.randomBoolean(.5f)) {
                state = Enums.EnityState.FEEDING;
                coolDown = .5f;
            } else if(MathUtils.randomBoolean(.3f)) {
                state = Enums.EnityState.WALKING;
                coolDown = 1f;
            }      
        }     
    }

	private void clearDestination() {
        if(isAtDestination()){
            destVec = null;
            destTile = null;
        } 
    }

    private void updatePositions() {
        sensor.setTransform(body.getPosition(),0);
        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - height/4;    
    }
    
    private void setTextureRegion(){
        if(isFlying() || isLanding()){
            tRegion = Media.birdFlyAnim.getKeyFrame(time, true);
        } else if(isWalking()){
            tRegion = Media.birdWalkAnim.getKeyFrame(time, true);
        } else if(isFeeding()){
            tRegion = Media.birdPeckAnim.getKeyFrame(time, true);
        } else if(isWalking()){
            tRegion = Media.birdWalkAnim.getKeyFrame(time, true);
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
        // https://github.com/libgdx/libgdx/wiki/Interpolation
        body.setTransform(body.getPosition().interpolate(new Vector2(destTile.pos.x + width, destTile.pos.y + height), delta * speed / 4, Interpolation.circle), 0);
        
        updatePositions();
    }
    
    private float setHeight() {
        return MathUtils.random(10) + 10;
    }
    
    private void checkFlyHeight() {
        if (isNotHigh()) pos.z += 0.1; 
        if (isTooHigh()) pos.z -= 0.1;     
    }

    private void land() {
        if (isAirBorn()) pos.z -= 0.5; 
        if(pos.z <= 0){ 
            // Landed
            pos.z = 0;
            state = Enums.EnityState.NONE;
            toggleHitboxes(true);
        }
    }
    
    private void setLanding() {
        if(MathUtils.randomBoolean(.2f)){
            state = Enums.EnityState.LANDING;  
        }
    }
    
    private void newDestinationOrHover(float delta, Chunk chunk) {
     // 15% chance a new destination is set, unless over water then always
        // get a new destination
        if(MathUtils.randomBoolean(.85f) || currentTile.isWater()){
            setDestination(delta, chunk);
            maxHeight = setHeight(); 
        } else {
            state = Enums.EnityState.HOVERING;
        }
    }

    private void setDestination(float delta, Chunk chunk){    
        for(ArrayList<Tile> row : chunk.tiles){
            if(destTile != null) break;
            
            for(Tile tile : row){
                if (tile.isGrass() && MathUtils.random(100) > 99 && tile != currentTile){
                    destTile = tile;
                    getVector(destTile.pos);
                    break;
                }
            }
        }
        
    }
   
    private void walk(float delta) {
    	if(currentTile.isPassable()){
    		if(tRegion.isFlipX()){
                body.setTransform(body.getPosition().x - speed / 4 * delta, body.getPosition().y,0);
            } else {
                body.setTransform(body.getPosition().x + speed / 4 * delta, body.getPosition().y,0);
            }
            updatePositions();		
    	}
	}
    
    private boolean hasDestination() {
        return destVec != null;
    }
    
    private boolean isAtDestination() {
        return currentTile.pos.epsilonEquals(destTile.pos, 20);
    }
    
    private boolean needsDestination() {
        return destVec == null && isFlying();
    }
    
    public boolean isAirBorn(){
        return pos.z > 0;
    }
    
    public boolean isNotAirBorn(){
        return pos.z == 0;
    }
    
    public boolean isHigh(){
        return pos.z == maxHeight;
    }
    
    public boolean isNotHigh(){
        return pos.z < maxHeight;
    }
    
    public boolean isTooHigh(){
        return pos.z > maxHeight;
    }
    
    private boolean isFlying() {
        return state == Enums.EnityState.FLYING;
    }
    
    private boolean isHovering(){
        return state == Enums.EnityState.HOVERING;
    }
    
    private boolean isLanding(){
        return state == Enums.EnityState.LANDING;
    }

    private boolean isFeeding(){
        return state == Enums.EnityState.FEEDING;
    }
    
    private boolean isWalking(){
        return state == Enums.EnityState.WALKING;
    }
    
}