package com.chess.engine.board;
import com.chess.engine.pieces.Piece;
import org.carrot2.shaded.guava.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

//setting coordinate and piece as protected and private made the class almost immutable and this helps retrieve
//all the created tile just looking up in the cache
public abstract class Tile {
   //accessed only by subclasses but CANNOT be set again
    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllEmptyTiles();

    //
    private static Map<Integer, EmptyTile> createAllEmptyTiles() {

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for(int i = 0; i < 64; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }
        //just class of google taùhat helps to keep map immutable
        return ImmutableMap.copyOf(emptyTileMap);
    }
    //only way to create a new Tile
    public static Tile createTile(final int tileCoordinate, final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    private Tile(int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public static final class  EmptyTile extends Tile{
        private EmptyTile(final int coordinate){
            super(coordinate);
        }

        //Returns status of tile, that being empty, returns false
        @Override
        public boolean isTileOccupied(){
            return false;
        }

        //get the piece on that tile, but being the tile empty returns null
        @Override
        public Piece getPiece(){
            return null;
        }
    }

    public static final class OccupiedTile extends Tile{

        private final Piece pieceOnTile;

        private OccupiedTile(int coordinate, Piece piece){
            super(coordinate);
            this.pieceOnTile = piece;
        }

        //now the spot is occupied
        @Override
        public boolean isTileOccupied(){
            return true;
        }

        //if a tile is occupied, of course it is in this case, returns the piece that is on it
        @Override
        public Piece getPiece(){
            return this.pieceOnTile;
        }
    }
}
