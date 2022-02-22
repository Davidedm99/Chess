package com.chess.engine.board;

public class BoardUtils {

    //array of bool of size 64 that will be "turned on" based on which column I'm at the moment
    public static final boolean[] FIRST_COLUMN = initiColumn(0) ;
    public static final boolean[] SECOND_COLUMN = initiColumn(1) ;
    public static final boolean[] EIGHTH_COLUMN = initiColumn(6) ;
    public static final boolean[] SEVENT_COLUMN = initiColumn(7) ;

    public static final boolean[] SECOND_ROW = null;
    public static final boolean[] SEVENTH_ROW = null;

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private BoardUtils(){
        throw new RuntimeException("Can't instantiate it");

    }

    private static boolean[] initiColumn(int columnNumber) {
        final boolean[] column = new boolean[NUM_TILES];

        //turns on the relative column of the columnNumber called, in this case will be 0,1,7,8 incrementing all by 8 each time
        do{
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        }while(columnNumber < NUM_TILES);

        return column;
    }

    //check if the coordinate is in bound after added bound (valid for each piece)
    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }
}
