package uk.co.carelesslabs.map;

import java.util.ArrayList;

public class Chunk {
     
    public static Tile get_tile(ArrayList<ArrayList<Tile>> chunk, int row, int col){
        System.out.println("Row: " + row + " Col: " + col);
        ArrayList<Tile> chunk_row;
        if(chunk.size() > row && row >= 0){
            chunk_row = chunk.get(row);
        
            if(chunk_row != null && chunk_row.size() > col && col >= 0){
                return chunk_row.get(col);
            }
        }
        return null;
    }

    public static String get_tile_code(ArrayList<ArrayList<Tile>> chunk, int row, int col){
        Tile tile;

        ArrayList<Tile> chunk_row;
        if(chunk.size() > row && row >= 0){
            chunk_row = chunk.get(row);
        
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