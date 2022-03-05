package com.chess.engine.pieces;
import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePosition;
    protected final Alliance pieceAlliance;             //Alliance as enum
    protected final boolean isFirstMove;                //useful for pawns and king

    Piece(final int piecePosition, final Alliance pieceAlliance){
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        //TODO: adjust
        this.isFirstMove = false;
    }

    public int getPiecePosition() { return this.piecePosition; }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    //all pieces will have a legal move and override this function depending on its behaviour
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public boolean isFirstMove(){
        return this.isFirstMove;
    }
}
