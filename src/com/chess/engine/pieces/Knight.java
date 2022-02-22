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

public class Knight extends Piece{
    //refer to useful images to understand, are all possible movements in a board from 0 to 63
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition,final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final  Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        //loop through all legal tiles for all possible offset in board
        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){
            //apply possible move offset to current position will determine the possible new position
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            //check if the coordinate is valid: out of bound, occupied, eatable
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                //exclusions for invalid moves, like 1st 2nd 7th and 8th column
                if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isEighthColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isSecondColumnExclusion(this.piecePosition, currentCandidateOffset)){
                    continue;
                }

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
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    //edge cases of knight in right or left side of the board
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        //array of bool as the valid or not in a boolean column AND values that break the knight rule movement in 1st col
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 17 || candidateOffset == 10 ||
                candidateOffset == -6 || candidateOffset == -15);
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.SEVENT_COLUMN[currentPosition] && ((candidateOffset == 10) || (candidateOffset == -6));
    }
}
