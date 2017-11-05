package uk.co.carelesslabs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Media {
    // TILES
    public static Texture grass01, grass02, grass03, grass04;
    public static Texture grassLeft, grassRight;
    public static Texture grassLeftUpperEdge, grassRightUpperEdge;
    public static Texture grassTop, grassTopRight, grassTopLeft;
    public static Texture water01, water02, water03, water04;
    public static Texture cliff, water;
    
    // HERO
    public static Texture hero;
    
    // Entity
    public static Texture tree;
    public static Texture birdWalk, birdFly, birdPeck, birdShadow;
    
    // Texture Regions
    public static TextureRegion[] birdWalkFrames, birdFlyFrames, birdPeckFrames;
    
    // Animations
    public static Animation<TextureRegion> birdWalkAnim, birdPeckAnim, birdFlyAnim;
    
    //GUI
    public static Texture squareMenu, mainBack, pinkButton;
    public static Texture iconBuild, iconSettings, iconResources;
    public static Texture selector;
    public static Texture close_menu;
    
    public static void load_assets(){
        // HERO
        hero  = new Texture("entities/hero/hero.png");
        
        // Source https://opengameart.org/content/micro-tileset-overworld-and-dungeon
        // Example Map: http://opengameart.org/sites/default/files/styles/watermarked/public/Render_0.png
        grass01 = new Texture("8x8/grass/grass_01.png");
        grass02 = new Texture("8x8/grass/grass_02.png");
        grass03 = new Texture("8x8/grass/grass_03.png");
        grass04 = new Texture("8x8/grass/grass_04.png");
        
        grassLeft = new Texture("8x8/grass/right_grass_edge.png");
        grassRight = new Texture("8x8/grass/left_grass_edge.png");
        
        grassLeftUpperEdge = new Texture("8x8/grass/left_upper_edge.png");
        grassRightUpperEdge = new Texture("8x8/grass/right_upper_edge.png");
        
        grassTop = new Texture("8x8/grass/top.png");
        grassTopRight = new Texture("8x8/grass/top_right.png");
        grassTopLeft = new Texture("8x8/grass/top_left.png");
        
        water01 = new Texture("8x8/water/water_01.png");
        water02 = new Texture("8x8/water/water_02.png");
        water03 = new Texture("8x8/water/water_03.png");
        water04 = new Texture("8x8/water/water_04.png");
        cliff   = new Texture(Gdx.files.internal("8x8/cliff.png"));
        
        tree    = new Texture("entities/tree.png");
        birdPeck = new Texture("entities/bird/bird_peck.png");
        birdWalk = new Texture("entities/bird/bird_walk.png"); 
        birdFly  = new Texture("entities/bird/bird_fly.png");
        birdShadow = new Texture("entities/bird/bird_shadow.png");
        
        // Texture Regions
        birdWalkFrames = TextureRegion.split(birdWalk, 10, 9)[0];
        birdPeckFrames = TextureRegion.split(birdPeck, 10, 9)[0];
        birdFlyFrames = TextureRegion.split(birdFly, 10, 9)[0];

        birdWalkAnim = new Animation<TextureRegion>(.1f, birdWalkFrames);
        birdPeckAnim = new Animation<TextureRegion>(.1f, birdPeckFrames);
        birdFlyAnim = new Animation<TextureRegion>(.1f, birdFlyFrames);
        
        // GUI
        squareMenu = new Texture(Gdx.files.internal("gui/square_menu.png"));
        mainBack = new Texture(Gdx.files.internal("gui/main_background.png"));
        pinkButton = new Texture(Gdx.files.internal("gui/pink_button.png"));
        selector = new Texture(Gdx.files.internal("gui/selector.png"));
        
        // ICONS
        iconBuild = new Texture(Gdx.files.internal("gui/icons/build.png"));
        iconSettings = new Texture(Gdx.files.internal("gui/icons/settings.png"));
        iconResources = new Texture(Gdx.files.internal("gui/icons/resources.png"));
        close_menu = new Texture(Gdx.files.internal("gui/icons/close_menu.png"));
    }
    
    public void dispose(){
        grass01.dispose();
        grass02.dispose();
        grass03.dispose();  
        grass04.dispose();
        grassLeft.dispose();  
        grassRight.dispose();  
        grassLeftUpperEdge.dispose();  
        grassRightUpperEdge.dispose();  
        grassTop.dispose();  
        grassTopRight.dispose();  
        grassTopLeft.dispose();  
        water01.dispose();  
        water02.dispose();  
        water03.dispose();  
        water04.dispose();  
        cliff.dispose();  
        tree.dispose();
    }
}
