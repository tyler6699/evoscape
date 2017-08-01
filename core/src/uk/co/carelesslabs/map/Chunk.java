package uk.co.carelesslabs.map;

import java.util.ArrayList;

public class Chunk {
     
    int number_rows;
    int number_cols;
    int tile_size;
    // Tiles are split into arrays of rows
    public ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
    
    public Chunk(int number_rows, int number_cols, int tile_size ){
        tiles = new ArrayList<ArrayList<Tile>>();
        this.number_rows = number_rows;
        this.number_cols = number_cols;
        this.tile_size = tile_size;
    }
    
    public Tile get_tile(int row, int col){
        System.out.println("Row: " + row + " Col: " + col);
        ArrayList<Tile> chunk_row;
        if(tiles.size() > row && row >= 0){
            chunk_row = tiles.get(row);
        
            if(chunk_row != null && chunk_row.size() > col && col >= 0){
                return chunk_row.get(col);
            }
        }
        return null;
    }

    public String get_tile_code(int row, int col){
        Tile tile;

        ArrayList<Tile> chunk_row;
        if(tiles.size() > row && row >= 0){
            chunk_row = tiles.get(row);
        
            if(chunk_row != null && chunk_row.size() > col && col >= 0){
                tile = chunk_row.get(col);
                return get_tile_code(tile);   
            }
        }
        return null;
    }

    public static String get_tile_code(Tile tile){
        if(tile != null && tile.is_grass()){
            return "1";
        } else {
            return "0";
        }
    }

}