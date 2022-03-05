package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Piece;
import org.carrot2.shaded.guava.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;

public class Board {

    /**
     * immutable list of tiles, useful to later map Tile-Piece
     */
    private final List<Tile> gameBoard;

    private Board(Builder builder){
        this.gameBoard = createGameboard(builder);
    }

    public Tile getTile(final int tileCoordinate) { return null; }

    /**  this method populates the list of tiles from 1 to 64 (chessboard)
        in the loop we associate a piece into a tile i since in the createTile we will
        associate the tile coordinate with that piece and this will be retrieved from boardConfig.get(pos)
    */
    private static List<Tile> createGameboard(final Builder builder){
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    /** create initial chessBoard position using the builder class */
    public static Board createStandardBoard(){
        return null;
    }

    public static class Builder{

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        public Builder(){

        }

        public Builder setPiece(final Piece piece){
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build(){
            return new Board(this);
        }
    }

}
