package uk.co.carelesslabs.map;

import java.util.ArrayList;

public class Chunk {
    int numberRows;
    int numberCols;
    int tileSize;
    // Tiles are split into arrays of rows
    public ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
    
    public Chunk(int number_rows, int number_cols, int tile_size ){
        tiles = new ArrayList<ArrayList<Tile>>();
        this.numberRows = number_rows;
        this.numberCols = number_cols;
        this.tileSize = tile_size;
    }
    
    public Tile getTile(int row, int col){
        ArrayList<Tile> chunk_row;
        if(tiles.size() > row && row >= 0){
            chunk_row = tiles.get(row);
        
            if(chunk_row != null && chunk_row.size() > col && col >= 0){
                return chunk_row.get(col);
            }
        }
        return null;
    }

    public String getTileCode(int row, int col){
        Tile tile;

        ArrayList<Tile> chunk_row;
        if(tiles.size() > row && row >= 0){
            chunk_row = tiles.get(row);
        
            if(chunk_row != null && chunk_row.size() > col && col >= 0){
                tile = chunk_row.get(col);
                return tile.isGrass() ? "1" : "0";
            }
        }
        return "0";
    }

}