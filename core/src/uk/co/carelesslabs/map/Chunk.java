package uk.co.carelesslabs.map;

import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.JsonObject;

public class Chunk {
    public int chunkNumber;
    public int row;
    public int col;
    int numberRows;
    int numberCols;
    int tileSize;
    
    // Tiles are split into arrays of rows
    public ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();
    
    public Chunk(int number_rows, int number_cols, int tile_size){
        tiles = new ArrayList<ArrayList<Tile>>();
        this.numberRows = number_rows;
        this.numberCols = number_cols;
        this.tileSize = tile_size;
    }
    
    public Chunk(JsonObject chunk) {
        tiles = new ArrayList<ArrayList<Tile>>();
        numberRows = chunk.get("numberRows").getAsInt();
        numberCols = chunk.get("numberCols").getAsInt();;
        tileSize = chunk.get("tileSize").getAsInt();
        chunkNumber = chunk.get("chunkNumber").getAsInt();
        row = chunk.get("row").getAsInt();
        col = chunk.get("col").getAsInt();
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

    public Tile getTile(Vector2 vector2) {
        ArrayList<Tile> chunk_row;
        Tile firstTile = tiles.get(0).get(0);
        float row = ((vector2.y - firstTile.pos.y) / tileSize) ;
        float col = ((vector2.x - firstTile.pos.x) / tileSize);

        if(tiles.size() > row && row >= 0){
            chunk_row = tiles.get((int) row);
        
            if(chunk_row != null && chunk_row.size() > col && col >= 0){
                return chunk_row.get((int) col);
            }
        }

        return null;
    }

}