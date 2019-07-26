package uk.co.carelesslabs.entity.ammo;

import com.badlogic.gdx.math.Vector2;
import uk.co.carelesslabs.entity.Entity;

public class Ammo extends Entity {
	public float range;
	public float damage;
	public Vector2 vector;
	public float distMoved;
	 
	public Ammo(){
		super();
		vector = new Vector2();
	}
	
	public void tick(float delta){
		
	}
	
}
