package com.thebyteguru.game.level;

/**
 * Created by Chessmaster on 30.05.16.
 */
public enum  TileType {
    //названия тайлов
    EMPTY(0), BRICK(1), METAL(2), WATER(3), GRASS(4), ICE(5);

    private int n;

    TileType(int n){
        this.n = n;
    }
    //с помощью тайпа получать число
    public int numeric(){
        return n;
    }

    //с числа получить тайлтап
    public static TileType fromNumeric(int n){
        switch (n){
            case 1:
                return BRICK;
            case 2:
            return METAL;
            case 3:
                return WATER;
            case 4:
                return GRASS;
            case 5:
                return ICE;
            default:
            return EMPTY;

        }
    }
}
