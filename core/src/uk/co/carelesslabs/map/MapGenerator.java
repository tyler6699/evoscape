package uk.co.carelesslabs.map;

import java.util.Arrays;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import uk.co.carelesslabs.Media;

public class MapGenerator {

    // Arrays for mapping code to texture
    public static String[] aGrassLeft = {"001001001","001001001", "001001000", "000001001"};
    public static String[] aGrassRight = {"100100100","100100000","000100100"};
    public static String[] aGrassREnd = {"100000000"};
    public static String[] aGrassLEnd = {"001000000"};
    public static String[] aGrassTop = {"000000111", "000000011","000000110"};
    public static String[] aGrassTopRight = {"000000100"};
    public static String[] aGrassTopLeft = {"000000001"};
    
    public static void setTileSecondaryTexture(Tile tile){
        if(Arrays.asList(aGrassLeft).contains(tile.code)){
            tile.secondaryTexture = Media.grassLeft;
        } else if(Arrays.asList(aGrassRight).contains(tile.code)){
            tile.secondaryTexture = Media.grassRight;
        } else if(Arrays.asList(aGrassREnd).contains(tile.code)){
            tile.secondaryTexture = Media.grassLeftUpperEdge;
        } else if(Arrays.asList(aGrassLEnd).contains(tile.code)){
            tile.secondaryTexture = Media.grassRightUpperEdge;
        } else if(Arrays.asList(aGrassTop).contains(tile.code)){
            tile.secondaryTexture = Media.grassTop;
        } else if(Arrays.asList(aGrassTopRight).contains(tile.code)){
            tile.secondaryTexture = Media.grassTopRight;
        } else if(Arrays.asList(aGrassTopLeft).contains(tile.code)){
            tile.secondaryTexture = Media.grassTopLeft;
        }
    }
    
    public static Texture randomGrass(){
        Texture grass;

        int tile = MathUtils.random(20);
        switch (tile) {
            case 1:  grass = Media.grass01;
                     break;
            case 2:  grass = Media.grass02;
                     break;
            case 3:  grass = Media.grass03;
                     break;
            case 4:  grass = Media.grass04;
                     break;
            default: grass = Media.grass01;
                     break;        
        }
        
        return grass;
    }
    
    public static Texture randomWater(){
        Texture water;

        int tile = MathUtils.random(20);
        switch (tile) {
            case 1:  water = Media.water01;
                     break;
            case 2:  water = Media.water02;
                     break;
            case 3:  water = Media.water03;
                     break;
            case 4:  water = Media.water04;
                     break;
            default: water = Media.water01;
                     break;        
        }
        
        return water;
    }
}
