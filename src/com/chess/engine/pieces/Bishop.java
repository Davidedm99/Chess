package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import org.carrot2.shaded.guava.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Bishop extends Piece {
    public static final int[] CANDIDATE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    Bishop(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        //loop through each vector
        for (final int candidateCoordinateOffset : CANDIDATE_VECTOR_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition;

            //check if after first applied offset we're not out of the board
            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){

                //check we're not in edge cases
                if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                    isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)){
                    break;
                }

                candidateDestinationCoordinate += candidateCoordinateOffset;

                //check again if the new position with applied offset is a valid one
                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){

                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                    if(!candidateDestinationTile.isTileOccupied()){
                        //movement of the piece
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    } else{
                        //check the piece that is occupying the tile to check if can be eaten or not
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                        //check if the piece alliance in the new tile is in the same alliance of ours
                        if(this.pieceAlliance != pieceAlliance){
                            //enemy piece, attacking move
                            legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate,
                                    pieceAtDestination));
                        }
                        //this break allows us to stop looping as soon as we found a piece, allied or not
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    //cover edge cases in which we're extreme right or left and an offset would send us in a wrong tile
    private static final boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
    }

    private static final boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 9 || candidateOffset == -7);
    }
}
