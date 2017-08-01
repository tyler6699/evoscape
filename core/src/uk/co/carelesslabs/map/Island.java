package uk.co.carelesslabs.map;

import java.util.ArrayList;
import java.util.Arrays;
import uk.co.carelesslabs.Enums.TILETYPE;
import uk.co.carelesslabs.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Island {
    // TILES
    Texture grass_01, grass_02, grass_03, grass_04;
    Texture grass_left, grass_right;
    Texture grass_left_upper_edge, grass_right_upper_edge;
    Texture grass_top, grass_top_right, grass_top_left;
    Texture water_01, water_02, water_03, water_04;
    Texture cliff, water;
    
    int tile_size = 8;
    int tiles_x, tiles_y;
    Tile centre_tile;
    Tile clicked_tile;
    
    // CHUNKS TODO: Add multiple chunks
    // int number_chunks = 0;
    // public Map<Integer, ArrayList<ArrayList<Tile>>> chunks = new HashMap<Integer, ArrayList<ArrayList<Tile>>>();
    
    // ONE CHUNK
    public ArrayList<ArrayList<Tile>> chunk = new ArrayList<ArrayList<Tile>>();
    ArrayList<Entity> entities = new ArrayList<Entity>();
    
    // TRACK CLICK
    int current_tile_no;
    int current_col;
    int current_row;
    
    // Arrays for mapping code to texture
    String[] a_grass_left = {"001001001","001001001", "001001000", "000001001"};
    String[] a_grass_right = {"100100100","100100000","000100100"};
    String[] a_grass_r_end = {"100000000"};
    String[] a_grass_l_end = {"001000000"};
    String[] a_grass_top = {"000000111", "000000011","000000110"};
    String[] a_grass_top_right = {"000000100"};
    String[] a_grass_top_left = {"000000001"};
    
    public Island(){
        setup_images();
        setup_tiles();
        code_tiles();
    }
    
    private void setup_tiles(){
        // Hard code island size
        tiles_x = 33;
        tiles_y = 33;
        
        int current_row = 0;
        int rng_w = MathUtils.random(5,8);
        int rng_h = MathUtils.random(5,8);
        
        int centre_tile_row = tiles_y / 2;
        int centre_tile_col = tiles_x /2;
        int first_tile_row = centre_tile_row - (rng_h);
        
        int max_row = centre_tile_row + rng_h;
        int min_row = centre_tile_row - rng_h;
        int max_col = centre_tile_col + rng_w;
        int min_col = centre_tile_col - rng_w;
        
        // CHUNK ROW
        ArrayList<Tile> chunk_row = new ArrayList<Tile>();
        
        // If number of tiles is needed.
        // int num_tiles = ((max_col - min_col)-1) * ((max_row - min_row)-1);

        for(int row = 0; row < tiles_y; row ++){
            for(int col = 0; col < tiles_x; col ++){
                // Create TILE
                Tile tile = new Tile(col, row, tile_size, TILETYPE.WATER, random_water());
                
                // Make a small island
                if(row > min_row && row < max_row && col > min_col && col < max_col){
                    tile.texture = random_grass();
                    tile.type = TILETYPE.GRASS;
                    
                    if(row == first_tile_row + 1){
                        tile.texture = cliff;
                        tile.type = TILETYPE.CLIFF;
                    } else {
                        // Chance to add trees etc
                    }
                } 
                
                // ADD TILE TO CHUNK
                if(current_row == row){
                    // Add tile to current row
                    chunk_row.add(tile);
                    
                    // Last row and column?
                    if (row == tiles_y - 1 && col == tiles_x - 1){
                        chunk.add(chunk_row);
                    }
                } else { 
                    // New row
                    current_row = row;
                    
                    // Add row to chunk
                    chunk.add(chunk_row);
                    
                    // Clear chunk row
                    chunk_row = new ArrayList<Tile>();
                    
                    // Add first tile to the new row
                    chunk_row.add(tile);
                }
            }
        }  
        
        // Set centre tile for camera positioning
        centre_tile = Chunk.get_tile(chunk, centre_tile_row, centre_tile_col);
    }
    
    private void update_image(Tile tile) {
        // Secondary Texture is to add edges to tiles
        // TODO: Add array of textures per tile
        if(Arrays.asList(a_grass_left).contains(tile.code)){
            tile.secondary_texture = grass_left;
        } else if(Arrays.asList(a_grass_right).contains(tile.code)){
            tile.secondary_texture = grass_right;
        } else if(Arrays.asList(a_grass_r_end).contains(tile.code)){
            tile.secondary_texture = grass_left_upper_edge;
        } else if(Arrays.asList(a_grass_l_end).contains(tile.code)){
            tile.secondary_texture = grass_right_upper_edge;
        } else if(Arrays.asList(a_grass_top).contains(tile.code)){
            tile.secondary_texture = grass_top;
        } else if(Arrays.asList(a_grass_top_right).contains(tile.code)){
            tile.secondary_texture = grass_top_right;
        } else if(Arrays.asList(a_grass_top_left).contains(tile.code)){
            tile.secondary_texture = grass_top_left;
        }        
    }
    
    private Texture random_grass(){
        Texture grass;

        int tile = MathUtils.random(20);
        switch (tile) {
            case 1:  grass = grass_01;
                     break;
            case 2:  grass = grass_02;
                     break;
            case 3:  grass = grass_03;
                     break;
            case 4:  grass = grass_04;
                     break;
            default: grass = grass_01;
                     break;        
        }
        
        return grass;
    }

    private Texture random_water(){
        Texture water;

        int tile = MathUtils.random(20);
        switch (tile) {
            case 1:  water = water_01;
                     break;
            case 2:  water = water_02;
                     break;
            case 3:  water = water_03;
                     break;
            case 4:  water = water_04;
                     break;
            default: water = water_01;
                     break;        
        }
        
        return water;
    }
    
    private void code_tiles() {
        // Loop all tiles and set the initial code
     
        // 1 CHUNK ONLY ATM
        for(ArrayList<Tile> row : chunk){
            for(Tile tile : row){ 
                // Check all surrounding tiles and set 1 for pass 0 for non pass
                // 0 0 0
                // 0 X 0
                // 0 0 0
                
                int[] rows = {1,0,-1};
                int[] cols = {-1,0,1};
                
                for(int r: rows){
                    for(int c: cols){
                        tile.code += Chunk.get_tile_code(chunk, tile.row + r, tile.col + c);
                        update_image(tile);
                    }
                }    
            }
        }
    }

    private void setup_images(){
        // Source https://opengameart.org/content/micro-tileset-overworld-and-dungeon
        // Example
        // http://opengameart.org/sites/default/files/styles/watermarked/public/Render_0.png
        grass_01 = new Texture("8x8/grass/grass_01.png");
        grass_02 = new Texture("8x8/grass/grass_02.png");
        grass_03 = new Texture("8x8/grass/grass_03.png");
        grass_04 = new Texture("8x8/grass/grass_04.png");
        
        grass_left = new Texture("8x8/grass/right_grass_edge.png");
        grass_right = new Texture("8x8/grass/left_grass_edge.png");
        
        grass_left_upper_edge = new Texture("8x8/grass/left_upper_edge.png");
        grass_right_upper_edge = new Texture("8x8/grass/right_upper_edge.png");
        
        grass_top = new Texture("8x8/grass/top.png");
        grass_top_right = new Texture("8x8/grass/top_right.png");
        grass_top_left = new Texture("8x8/grass/top_left.png");
        
        water_01 = new Texture("8x8/water/water_01.png");
        water_02 = new Texture("8x8/water/water_02.png");
        water_03 = new Texture("8x8/water/water_03.png");
        water_04 = new Texture("8x8/water/water_04.png");
        cliff    = new Texture(Gdx.files.internal("8x8/cliff.png"));
    }

    public void dispose() {
        grass_01.dispose();
        grass_02.dispose();
        grass_03.dispose();  
        grass_04.dispose();
        grass_left.dispose();  
        grass_right.dispose();  
        grass_left_upper_edge.dispose();  
        grass_right_upper_edge.dispose();  
        grass_top.dispose();  
        grass_top_right.dispose();  
        grass_top_left.dispose();  
        water_01.dispose();  
        water_02.dispose();  
        water_03.dispose();  
        water_04.dispose();  
        cliff.dispose();  
    }
}
