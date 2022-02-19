package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import org.carrot2.shaded.guava.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    //refer to useful images to understand, are all possible movements in a board from 0 to 63
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition,final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public List<Move> calculateLegalMoves(Board board) {

        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        //check legal tiles for all possible offset in board
        for(int currentCandidate : CANDIDATE_MOVE_COORDINATES){
            //apply possible move offset to current position will determine the possible new position
            candidateDestinationCoordinate = this.piecePosition + currentCandidate;

            //check if the coordinate is valid: out of bound, occupied, eatable
            if(true){
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                if(!candidateDestinationTile.isTileOccupied()){
                    //movement of the piece
                    legalMoves.add(new Move());
                } else{
                    //check the piece that is occupying the tile to check if can be eaten or not
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    //check if the piece alliance in the new tile is in the same alliance of ours 
                    if(this.pieceAlliance != pieceAlliance){
                        //enemy piece
                        legalMoves.add(new Move());
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
