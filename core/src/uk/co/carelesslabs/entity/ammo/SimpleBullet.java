package uk.co.carelesslabs.entity.ammo;

import uk.co.carelesslabs.Media;
import uk.co.carelesslabs.Enums.EntityType;
import uk.co.carelesslabs.box2d.Box2DHelper;
import uk.co.carelesslabs.box2d.Box2DWorld;
import uk.co.carelesslabs.weapons.Gun;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class SimpleBullet extends Ammo {
	Gun gun;
	
	public SimpleBullet(Gun gun, Box2DWorld box2d){
		super();
		this.gun = gun;
		type = EntityType.BULLET;
        texture = Media.close_menu;
		range = 50;
		damage = 1;
        width = 8;
        height = 8;
        speed = 60;
        active = true;
        setupBullet(box2d);
	}
	
	public void tick(float delta){
		if(active){
			float dx = (delta * vector.x) * speed;
			float dy = (delta * vector.y) * speed;
			float dx2 = pos.x + dx;
			float dy2 = pos.y + dy;
			
			distMoved += Vector2.dst(pos.x, pos.y, dx2, dy2);
			pos.set(dx2, dy2, 0);
			sensor.setTransform(pos.x + width/2, pos.y + height/2, 0);
			
			if(distMoved > range){
				// Remove Bullet
				remove = true;
				active = false;
			}		
		}

	}
	
	public void setupBullet(Box2DWorld box2d){
		// Position
		float angleRadians = MathUtils.degreesToRadians * gun.angle;
		vector.x = MathUtils.cos(angleRadians);
		vector.y = MathUtils.sin(angleRadians);
		
		// Move bullet toward end of gun
		pos.x = gun.pos.x + (vector.x * 10);
        pos.y = gun.pos.y + (vector.y * 10);
        
		// Physics
        sensor = Box2DHelper.createSensor(box2d.world, width, height*.85f, width/2, height/3, pos, BodyDef.BodyType.DynamicBody);     
        hashcode = sensor.getFixtureList().get(0).hashCode(); 
	}
}
